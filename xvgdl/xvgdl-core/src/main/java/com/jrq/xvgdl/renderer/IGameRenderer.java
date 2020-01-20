package com.jrq.xvgdl.renderer;

import com.jrq.xvgdl.context.GameContext;

/**
 * Game Renderer Interface
 *
 * @author jrquinones
 */
public interface IGameRenderer {

    /**
     * Renderer initializing tasks
     *
     * @param gameContext Game Context
     */
    void initializeRenderer(GameContext gameContext);

    /**
     * Rendering command.
     */
    void render();
}
