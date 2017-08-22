package es.jor.phd.xvgdl.engine;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.properties.PropertiesParseException;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.event.GameEventUtils;
import es.jor.phd.xvgdl.event.IGameEvent;
import es.jor.phd.xvgdl.input.KeyboardInputListener;
import es.jor.phd.xvgdl.model.object.IGameObject;
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
     * Singleton instance.
     */
    private static GameEngine instance;

    /**
     * Constructor.
     *
     * @param configFile Configuration File
     */
    private GameEngine(String configFile) {
        try {
            // Load Game Engine properties
            loadPropertiesFromXML(configFile);

            if (getProperty(GAME_CONTEXT_CONFIG_KEY) != null) {
                ELogger.debug(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                        "Context to be created with file " + getProperty(GAME_CONTEXT_CONFIG_KEY));
                GameContext.createGameContext(getProperty(GAME_CONTEXT_CONFIG_KEY));
            }

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
            } catch (NativeHookException ex) {
                ELogger.error(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                        "Exception registering hooks", ex);
            }

            GlobalScreen.addNativeKeyListener(new KeyboardInputListener(getGameContext()));

        } catch (PropertiesParseException e) {
            ELogger.error(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY, "Exception parsing properties",
                    e);
        }
    }

    /**
     * Creates the game engine
     *
     * @param configFile Configuration file
     * @return The game engine instance
     */
    public static GameEngine createGameEngine(String configFile) {
        instance = new GameEngine(configFile);
        return instance;
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

        boolean gameFinished = false;

        ELogger.debug(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY, "Launching game loop....");
        getGameContext().setStartTime(System.currentTimeMillis());

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
                if (getGameContext().checkTimeoutCondition()) {
                    gameFinished = true;
                    ELogger.debug(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                            "Game timeout condition reached! Game will end.");
                }
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

    /**
     * Process all pending events.
     */
    private void processEvents() {

        for (IGameEvent event : getGameContext().getEvents()) {
            GameEventUtils.applyGameEvent(getGameContext(), event);
            getGameContext().eventProcessed(event);
        }

    }

    /**
     * Process all rules.
     */
    private void processRules() {

        // Foreach rule : Rules
        for (IGameRule rule : getGameContext().getRules().values()) {
            GameRuleUtils.applyGameRule(getGameContext(), rule);
        }

    }

    /**
     * Processes Artificial Intelligence.
     */
    private void processAI() {

        // Apply Artificial Intelligence to all elements that have AI configured
        List<IGameObject> enemies = getGameContext().getObjectsList();
        for (IGameObject enemy : enemies) {
            enemy.applyAI(getGameContext());
        }
    }

    /**
     * Updates context state.
     */
    private void updateState() {
    }

    /**
     * Render current state.
     */
    private void render() {
        GameContext.getInstance().getRenderer().render();
    }
}
