package com.jrq.xvgdl.app;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;

/**
 * The XVGDL Game Application
 *
 * @author jrquinones
 */
public final class XvgdlGameApp {

    /**
     * Instance of the game engine.
     */
    private static GameEngine gameEngine;

    /**
     * Constructor
     */
    private XvgdlGameApp() {
    }

    /**
     * Launches the Game Engine.
     *
     * @param configFile Configuration File
     * @return the created game engine
     */
    public static GameEngine launchGameApp(String configFile) {
        gameEngine = GameEngine.createGameEngine(configFile);
        return gameEngine;
    }

    /**
     * Launches the Game Engine.
     *
     * @param configFile  Configuration File
     * @param gameContext Already created game context
     * @return the created game engine
     */
    public static GameEngine launchGameApp(GameContext gameContext, String configFile) {
        gameEngine = GameEngine.createGameEngine(gameContext, configFile);
        return gameEngine;
    }
}
