package es.jor.phd.xvgdl.app;

import es.jor.phd.xvgdl.engine.GameEngine;

/**
 * The Game Application
 * @author jrquinones
 *
 */
public final class GameApp {

    /** Instance of the game engine. */
    private static GameEngine gameEngine;

    /**
     * Constructor
     */
    private GameApp() {
    }

    /**
     * Launches the Game Engine.
     * @param configFile Configuration File
     * @return the created game engine
     */
    public static GameEngine launchGameApp(String configFile) {
        gameEngine = GameEngine.createGameEngine(configFile);
        return gameEngine;
    }

}
