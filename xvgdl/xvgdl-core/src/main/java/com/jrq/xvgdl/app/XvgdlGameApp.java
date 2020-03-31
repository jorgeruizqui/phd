package com.jrq.xvgdl.app;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.exception.XvgdlException;

/**
 * The XVGDL Game Application
 *
 * @author jrquinones
 */
public final class XvgdlGameApp {

    /**
     * Launches the Game Engine.
     *
     * @param configFile Configuration File
     * @return the created game engine
     */
    public static GameEngine launchGameApp(String configFile) throws XvgdlException {
        return GameEngine.createGameEngine(configFile);
    }

    /**
     * Launches the Game Engine.
     *
     * @param configFile  Configuration File
     * @param gameContext Already created game context
     * @return the created game engine
     */
    public static GameEngine launchGameApp(GameContext gameContext, String configFile) throws XvgdlException {
        return GameEngine.createGameEngine(gameContext, configFile);
    }
}
