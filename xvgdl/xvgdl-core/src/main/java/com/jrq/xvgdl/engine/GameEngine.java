package com.jrq.xvgdl.engine;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.input.KeyboardInputListener;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import com.jrq.xvgdl.model.event.GameEventType;
import com.jrq.xvgdl.model.event.GameEventUtils;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.rules.GameRuleType;
import com.jrq.xvgdl.model.rules.IGameRule;
import com.jrq.xvgdl.renderer.IGameRenderer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Game engine
 *
 * @author jrquinones
 */
@Slf4j
public final class GameEngine {

    /**
     * Milliseconds per frame Configuration Key.
     */
    private static final String MS_PER_FRAME_KEY = "msPerFrame";

    /**
     * Default value for MS_PER_FRAME. This value means that the game is rendered at 60f/sec
     */
    private static final long DEFAULT_MS_PER_FRAME = 200;

    /**
     * Game speed factor of 1.0 means the objects with speed factor 1 will move 1 time per second.
     */
    public static final double GAME_ENGINE_SPEED_FACTOR = 1000.0d;

    /**
     * Simulation mode configuration key.
     */
    private static final String SIMULATION_MODE_KEY = "simulationMode";

    /**
     * Game engine scheduler executor.
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    /**
     * Singleton instance.
     */
    private static GameEngine instance;

    @Getter
    private GameContext gameContext = new GameContext();

    /**
     * Game Finished Flag.
     */
    private boolean gameFinished = false;

    /**
     * Game Running in simulation mode Flag.
     */
    private boolean simulationMode = false;

    private KeyboardInputListener keyboardInputListener;

    /**
     * Game Renderer.
     */
    private IGameRenderer gameRenderer;

    /**
     * Constructor.
     *
     * @param configFile Configuration File
     */
    private GameEngine(GameContext gc, String configFile) throws XvgdlException {
        try {
            loadGameContext(gc, configFile);

            initializeGameRenderer();

            addKeyboardListener();
        } catch (Exception e) {
            log.error("Exception initializing game context with file: " + configFile, e);
            throw e;
        }
    }

    private void initializeGameRenderer() {
        this.gameRenderer = gameContext.getGameDefinition().getRenderer().toModel();
        if (this.gameRenderer != null) this.gameRenderer.initializeRenderer(this.gameContext);
    }

    private void loadGameContext(GameContext gc, String configFile) throws XvgdlException {
        log.debug("Context to be created with file: " + configFile);
        long start = System.currentTimeMillis();
        gameContext.loadGameContext(gc, configFile);
        long end = System.currentTimeMillis();
        log.debug("Context has been created in " + (end - start)+ " ms.");
    }

    private void addKeyboardListener() throws XvgdlException {
        try {
            this.keyboardInputListener = new KeyboardInputListener(getGameContext());
        } catch (Exception ex) {
            throw new XvgdlException("Exception initializing the keyboard input listener", ex);
        }
    }

    /**
     * Creates the game engine using a game context
     *
     * @param configFile Configuration file
     * @param cg         Game Context
     * @return The game engine instance
     */
    public static GameEngine createGameEngine(GameContext cg, String configFile) throws XvgdlException {
        instance = new GameEngine(cg, configFile);
        return instance;
    }

    /**
     * Creates the game engine
     *
     * @param configFile Configuration file
     * @return The game engine instance
     */
    public static GameEngine createGameEngine(String configFile) throws XvgdlException {
        return createGameEngine(null, configFile);
    }

    /**
     * @return the instance
     */
    public static GameEngine getInstance() {
        if (instance == null) {
            throw new NullPointerException("Game Engine instance hasn't been created");
        }
        return instance;
    }

    /**
     * Starts the game engine
     */
    public void start() throws XvgdlException {
        gameLoop();
    }

    /**
     * Ends the game engine
     */
    public void end() throws XvgdlException {
        this.gameContext.setEndTime(System.currentTimeMillis());
    }

    /**
     * Main game loop.
     */
    private void gameLoop() throws XvgdlException {

        log.debug("Launching game loop....");
        getGameContext().setStartTime(System.currentTimeMillis());
        getGameContext().setLoopTime(gameContext.getStartTime());

        updateState();
        // TODO Check also 'pause' option
        while (!gameFinished) {
            try {
                getGameContext().setLoopTime(System.currentTimeMillis());
                processEvents();
                processRules();
                updateState();
                render();

                // Render at the Frames Per Second Ratio
                if (!simulationMode) {
                    Thread.sleep(getMillisecondsPerFrame() + timeToProcessGameLoop(getGameContext().getLoopTime()));
                }
                getGameContext().nextTurn();
                checkEndConditions();
            } catch (Exception e) {
                throw new XvgdlException("Exception in game loop.", e);
            }
        }
        renderGameFinished();
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            log.error("Exception Unregistering Native Hook:" + e.getMessage(), e);
        }

    }

    private long getMillisecondsPerFrame() {
        return gameContext.getGameDefinition().getProperties().getLongValue(
                MS_PER_FRAME_KEY, DEFAULT_MS_PER_FRAME);
    }

    private long timeToProcessGameLoop(long loopStartTime) {
        return System.currentTimeMillis() - loopStartTime;
    }

    private void checkEndConditions() {

        for (IGameEndCondition endCondition : getGameContext().getGameEndConditions()) {
            if (endCondition.checkCondition(getGameContext())) {
                log.info("Game end condition reached: " + endCondition.toString());
                gameFinished = true;
                if (endCondition.isWinningCondition()) {
                    gameContext.setWinningGame(true);
                }
                break;
            }
        }
    }

    /**
     * Process all pending events.
     */
    private void processEvents() {

        for (IGameEvent event : getGameContext().getGameSortedEventsByTime()) {
            if (event.getGameStates().isEmpty() || event.getGameStates().contains(gameContext.getCurrentGameState())) {
                boolean executed = false;
                log.debug("Event to be processed: " + event);
                if (simulationMode && GameEventType.KEYBOARD.equals(event.getEventType())) {
                    log.debug("Keyboard Event not processed. Simulation Mode enabled");
                    executed = true;
                } else {
                    executed = GameEventUtils.processGameEvent(getGameContext(), event);
                }

                if (event.isConsumable() && executed) {
                    getGameContext().eventProcessed(event);
                }
            }
        }
    }

    /**
     * Process all rules.
     */
    private void processRules() {

        for (IGameRule rule : getGameContext().getGameRules()) {
            if (rule.getGameStates().contains(gameContext.getCurrentGameState())) {
                boolean ruleResult = rule.applyGameRule(getGameContext());

                if (ruleResult && rule.getType().equals(GameRuleType.END_CONDITION)) {
                    this.gameFinished = true;
                }
            }
        }
    }

    /**
     * Updates context state for all game objects.
     */
    private void updateState() {
        getGameContext().getObjectsAsList().forEach(go -> go.updateState(this.gameContext));
    }

    private void render() {
        if (this.gameRenderer != null) {
            this.gameRenderer.render();
        }
    }

    private void renderGameFinished() {
        if (this.gameRenderer != null) {
            this.gameRenderer.renderGameFinished();
        }
    }

    /**
     * Freeze a game object for a concrete amount of milliseconds
     *
     * @param o            Object
     * @param milliseconds Time to be frozen
     */
    public void freezeObject(IGameObject o, long milliseconds) {
        o.setFrozen(true);
        scheduler.schedule(() -> o.setFrozen(false), milliseconds, TimeUnit.MILLISECONDS);
    }
}
