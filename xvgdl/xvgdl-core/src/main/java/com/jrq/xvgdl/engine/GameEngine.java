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
import com.jrq.xvgdl.model.rules.GameRuleUtils;
import com.jrq.xvgdl.model.rules.IGameRule;
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
     * Default value for MS_PER_FRAME
     */
    private static final double DEFAULT_MS_PER_FRAME = 200;

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
     * Constructor.
     *
     * @param configFile Configuration File
     */
    private GameEngine(GameContext gc, String configFile) throws XvgdlException {
        try {
            loadGameContext(gc, configFile);

            addKeyboardListener();
        } catch (Exception e) {
            log.error("Exception initializing game context with file: " + configFile, e);
            throw e;
        }
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

        updateState();
        // TODO Check also 'pause' option
        while (!gameFinished) {
            try {
                double start = System.currentTimeMillis();
                processEvents();
                processAI();
                processRules();
                updateState();
                render();
                if (!simulationMode) {
                    Thread.sleep(
                            (long) (gameContext.getGameDefinition().getProperties().getDoubleValue(
                                    MS_PER_FRAME_KEY, DEFAULT_MS_PER_FRAME) + System.currentTimeMillis()
                            - start));
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
            if (simulationMode && GameEventType.KEYBOARD.equals(event.getEventType())) {
                log.debug("Keyboard Event not processed. Simulation Mode enabled");
            } else {
                GameEventUtils.processGameEvent(getGameContext(), event);
            }

            if (event.isConsumable()) {
                getGameContext().eventProcessed(event);
            }
        }
    }

    /**
     * Process all rules.
     */
    private void processRules() {

        for (IGameRule rule : getGameContext().getGameRules()) {
            boolean ruleResult = GameRuleUtils.applyGameRule(getGameContext(), rule);

            if (ruleResult && rule.getType().equals(GameRuleType.END_CONDITION)) {
                this.gameFinished = true;
            }
        }
    }

    /**
     * Processes Artificial Intelligence.
     */
    private void processAI() {
        // Apply Artificial Intelligence to all elements that have AI configured
        getGameContext().getObjectsAsList().forEach(go -> go.applyAI(getGameContext()));
    }

    /**
     * Updates context state.
     */
    private void updateState() {
        getGameContext().getObjectsAsList().forEach(IGameObject::update);
    }

    /**
     * Render current state.
     */
    private void render() {
        if (getGameContext().getGameRenderer() != null) {
            getGameContext().getGameRenderer().render();
        }
    }

    /**
     * Render current state.
     */
    private void renderGameFinished() {
        if (getGameContext().getGameRenderer() != null) {
            getGameContext().getGameRenderer().renderGameFinished();
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
