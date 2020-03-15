package com.jrq.xvgdl.engine;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.input.KeyboardInputListener;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import com.jrq.xvgdl.model.event.GameEventType;
import com.jrq.xvgdl.model.event.GameEventUtils;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.event.KeyboardGameEvent;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.rules.GameRuleType;
import com.jrq.xvgdl.model.rules.GameRuleUtils;
import com.jrq.xvgdl.model.rules.IGameRule;
import com.jrq.xvgdl.util.GameBaseProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Game engine
 *
 * @author jrquinones
 */
@Slf4j
public final class GameEngine extends GameBaseProperties {

    /**
     * Milliseconds per frame Configuration Key.
     */
    private static final String MS_PER_FRAME_KEY = "msPerFrame";

    /**
     * Default value for MS_PER_FRAME
     */
    private static final double DEFAULT_MS_PER_FRAME = 1000;

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

    /**
     * Game Finished Flag.
     */
    private boolean gameFinished = false;

    @Getter
    private boolean winningGame = false;

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
    private GameEngine(GameContext gc, String configFile) {
        try {
            loadGameContext(gc, configFile);

            addKeyboardListener();
        } catch (Exception e) {
            log.error("Exception initializing game context with file: " + configFile, e);
        }
    }

    private void loadGameContext(GameContext gc, String configFile) {
        log.debug("Context to be created with file: " + configFile);
        long start = System.currentTimeMillis();
        GameContext.createGameContext(gc, configFile);
        long end = System.currentTimeMillis();
        log.debug("Context has been created in " + (end - start)+ " ms.");
    }

    private void addKeyboardListener() {
        try {
            this.keyboardInputListener = new KeyboardInputListener(getGameContext());

            setNativeHookLogLevelOff();

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this.keyboardInputListener);

            List<KeyboardGameEvent> definedKeyboardGameEvents =
                    getGameContext().getGameEvents().stream()
                            .filter(e -> e instanceof KeyboardGameEvent)
                            .map(o -> (KeyboardGameEvent) o)
                            .collect(Collectors.toList());

            this.keyboardInputListener.addContextDefinedKeyboardGameEvent(definedKeyboardGameEvents);

        } catch (NativeHookException ex) {
            log.error("Exception registering hooks", ex);
        }
    }

    private void setNativeHookLogLevelOff() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(Level.OFF);
        }

    }

    /**
     * Creates the game engine using a game context
     *
     * @param configFile Configuration file
     * @param cg         Game Context
     * @return The game engine instance
     */
    public static GameEngine createGameEngine(GameContext cg, String configFile) {
        instance = new GameEngine(cg, configFile);
        return instance;
    }

    /**
     * Creates the game engine
     *
     * @param configFile Configuration file
     * @return The game engine instance
     */
    public static GameEngine createGameEngine(String configFile) {
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
    public void start() {
        gameLoop();
    }

    /**
     * @return the game Context
     */
    public GameContext getGameContext() {
        return GameContext.getInstance();
    }

    /**
     * Main game loop.
     */
    private void gameLoop() {

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
                            (long) (getDoubleValue(MS_PER_FRAME_KEY, DEFAULT_MS_PER_FRAME) + System.currentTimeMillis()
                            - start));
                }
                getGameContext().nextTurn();
                checkEndConditions();
            } catch (Exception e) {
                log.error("Exception in game loop", e);
            }
        }
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            log.error("Exception Unregistering Native Hook", e);
        }

    }

    private void checkEndConditions() {

        for (IGameEndCondition endCondition : getGameContext().getEndConditions()) {
            if (endCondition.checkCondition(getGameContext())) {
                log.info("Game end condition reached: " + endCondition.toString());
                gameFinished = true;
                if (endCondition.isWinningCondition()) {
                    winningGame = true;
                }
                getGameContext().setEndTime(System.currentTimeMillis());
                break;
            }
        }
    }

    /**
     * Process all pending events.
     */
    private void processEvents() {

        for (IGameEvent event : getGameContext().getGameSortedEvents()) {
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
        if (GameContext.getInstance().getGameRenderer() != null) {
            GameContext.getInstance().getGameRenderer().render();
        }
    }

    /**
     * Freeze a game object for a concrete amount of milliseconds
     *
     * @param o            Object
     * @param milliseconds Time to be frozen
     */
    public void freezeObject(IGameObject o, long milliseconds) {
        o.setIsFrozen(true);
        scheduler.schedule(() -> o.setIsFrozen(false), milliseconds, TimeUnit.MILLISECONDS);
    }

}
