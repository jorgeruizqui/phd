package es.jor.phd.xvgdl.engine;

import java.io.IOException;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.properties.PropertiesParseException;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
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
        } catch (PropertiesParseException e) {
            ELogger.error(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY, "Exception parsing properties");
        }
    }

    /**
     * Creates the game engine
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

        ELogger.debug(GameEngine.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                "Launching game loop....");

        // TODO Check also 'pause' option
        while (!gameFinished) {
            try {
                double start = System.currentTimeMillis();
                //System.out.println("Game Loop at " + start);
                processEvents();
                updateState();
                render();
                System.out.println("Game Loop at " + start);
                Thread.sleep((long) (getDoubleValue(MS_PER_FRAME_KEY, DEFAULT_MS_PER_FRAME)
                        + System.currentTimeMillis() - start));
            } catch (Exception e) {
                ELogger.error(GameEngine.class, "", "Exception in game loop", e);
            }
        }

    }

    /**
     * Process all pending events.
     */
    private void processEvents() {

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
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
