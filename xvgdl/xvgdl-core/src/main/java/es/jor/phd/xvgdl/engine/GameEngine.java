package es.jor.phd.xvgdl.engine;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.input.KeyboardInputListener;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.model.event.GameEventType;
import es.jor.phd.xvgdl.model.event.GameEventUtils;
import es.jor.phd.xvgdl.model.event.IGameEvent;
import es.jor.phd.xvgdl.model.event.KeyboardGameEvent;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.model.rules.GameRuleType;
import es.jor.phd.xvgdl.model.rules.GameRuleUtils;
import es.jor.phd.xvgdl.model.rules.IGameRule;


/**
 * Game engine
 *
 * @author jrquinones
 *
 */
@Slf4j
public final class GameEngine extends Properties {

    /**
     * Milliseconds per frame Configuration Key.
     */
    private static final String MS_PER_FRAME_KEY = "msPerFrame";

    /** Default value for MS_PER_FRAME */
    private static final double DEFAULT_MS_PER_FRAME = 1000;

    /**
     * Game Context configuration file key.
     */
    private static final String GAME_CONTEXT_CONFIG_KEY = "gameContextConfiguration";

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

    /** Game Finished Flag. */
    private boolean gameFinished = false;

    /** Game Winning Flag. */
    private boolean gameWinning = false;

    /** Game Running in simulation mode Flag. */
    private boolean simulationMode = false;

    private KeyboardInputListener keyboardInputListener;

    /**
     * Constructor.
     *
     * @param configFile Configuration File
     */
    private GameEngine(GameContext gc, String configFile) {
        try {
            // Load Game Engine properties
            //loadPropertiesFromXML(configFile);
            //this.simulationMode = getBooleanValue(SIMULATION_MODE_KEY, false);

            if (getProperty(GAME_CONTEXT_CONFIG_KEY) != null) {
                log.debug("Context to be created with file " + getProperty(GAME_CONTEXT_CONFIG_KEY));
                GameContext.createGameContext(gc, getProperty(GAME_CONTEXT_CONFIG_KEY));
            }

            addKeyboardListener();

        } catch (Exception e) {
            log.error("Exception parsing properties", e);
        }
    }

    private void addKeyboardListener() {
        try {
            this.keyboardInputListener = new KeyboardInputListener(getGameContext());
            
            // Get the logger for "org.jnativehook" and set the level to
            // off.
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            // Change the level for all handlers attached to the default
            // logger.
            Handler[] handlers = Logger.getLogger("").getHandlers();
            for (int i = 0; i < handlers.length; i++) {
                handlers[i].setLevel(Level.OFF);
            }
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

    /**
     * Creates the game engine using a game context
     *
     * @param configFile Configuration file
     * @param cg Game Context
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
     *
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
     *
     * @return the game Context
     */
    public GameContext getGameContext() {
        return GameContext.getInstance();
    }

    /**
     * Main game loop.
     */
    public void gameLoop() {

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
                //Thread.sleep((long) (getDoubleValue(MS_PER_FRAME_KEY, DEFAULT_MS_PER_FRAME) + System.currentTimeMillis()
                //        - start));
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
                    gameWinning = true;
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

        // Foreach rule : Rules
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
     * @param o Object
     * @param milliseconds Time to be frozen
     */
    public void freezeObject(IGameObject o, long milliseconds) {
        o.setFrozen(true);
        scheduler.schedule(() -> o.setFrozen(false), milliseconds, TimeUnit.MILLISECONDS);
    }

    /**
     * If the game is won or not.
     * 
     * @return
     */
    public boolean gameWinning() {
        return gameWinning;
    }
}
