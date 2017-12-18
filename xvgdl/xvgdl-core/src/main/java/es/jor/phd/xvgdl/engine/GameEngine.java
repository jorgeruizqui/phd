package es.jor.phd.xvgdl.engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.properties.PropertiesParseException;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.input.KeyboardInputListener;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.model.event.GameEventType;
import es.jor.phd.xvgdl.model.event.GameEventUtils;
import es.jor.phd.xvgdl.model.event.IGameEvent;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.model.rules.GameRuleType;
import es.jor.phd.xvgdl.model.rules.GameRuleUtils;
import es.jor.phd.xvgdl.model.rules.IGameRule;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game engine
 *
 * @author jrquinones
 *
 */
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
     * Game Context generator configuration class key.
     */
    private static final String GAME_CONTEXT_GENERATOR_KEY = "gameContextGenerator";

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

    /** Game Running in simulation mode Flag. */
    private boolean simulationMode = false;

    /** Game Running current turn. */
    private int turns = 0;

    /**
     * Constructor.
     *
     * @param configFile Configuration File
     */
    private GameEngine(GameContext gc, String configFile) {
        try {
            // Load Game Engine properties
            loadPropertiesFromXML(configFile);
            this.simulationMode = getBooleanValue(SIMULATION_MODE_KEY, false);

            if (getProperty(GAME_CONTEXT_CONFIG_KEY) != null) {
                ELogger.debug(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                        "Context to be created with file " + getProperty(GAME_CONTEXT_CONFIG_KEY));
                GameContext.createGameContext(gc, getProperty(GAME_CONTEXT_CONFIG_KEY));
            }

            addKeyListener();

        } catch (PropertiesParseException e) {
            ELogger.error(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY, "Exception parsing properties",
                    e);
        }
    }

    private void addKeyListener() {
        try {
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
            GlobalScreen.addNativeKeyListener(new KeyboardInputListener(getGameContext()));
        } catch (NativeHookException ex) {
            ELogger.error(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY, "Exception registering hooks",
                    ex);
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

        ELogger.debug(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY, "Launching game loop....");
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
                Thread.sleep((long) (getDoubleValue(MS_PER_FRAME_KEY, DEFAULT_MS_PER_FRAME) + System.currentTimeMillis()
                        - start));
                this.turns++;
                checkEndConditions();
            } catch (Exception e) {
                ELogger.error(GameEngine.class, "", "Exception in game loop", e);
            }
        }
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            ELogger.error(GameEngine.class, "", "Exception Unregistering Native Hook", e);
        }

    }

    public int getTurns() {
        return turns;
    }

    private void checkEndConditions() {

        for (IGameEndCondition endCondition : getGameContext().getEndConditions()) {
            if (endCondition.checkCondition()) {
                ELogger.info(this, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                        "Game end condition reached: " + endCondition.toString());
                gameFinished = true;
                break;
            }
        }
    }

    /**
     * Process all pending events.
     */
    private void processEvents() {

        for (IGameEvent event : getGameContext().getEvents()) {
            if (simulationMode && GameEventType.KEYBOARD.equals(event.getEventType())) {
                ELogger.debug(this, "", "Keyboard Event not processed. Simulation Mode enabled");
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
        for (IGameRule rule : getGameContext().getRules().values()) {
            boolean ruleResult = GameRuleUtils.applyGameRule(getGameContext(), rule);

            if (ruleResult && rule.getGameRuleType().equals(GameRuleType.END_CONDITION)) {
                this.gameFinished = true;
            }
        }

    }

    /**
     * Processes Artificial Intelligence.
     */
    private void processAI() {

        // Apply Artificial Intelligence to all elements that have AI configured
        getGameContext().getObjectsList().forEach(go -> go.applyAI(getGameContext()));
    }

    /**
     * Updates context state.
     */
    private void updateState() {
        getGameContext().getObjectsList().forEach(IGameObject::update);
    }

    /**
     * Render current state.
     */
    private void render() {
        if (GameContext.getInstance().getRenderer() != null) {
            GameContext.getInstance().getRenderer().render();
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
}
