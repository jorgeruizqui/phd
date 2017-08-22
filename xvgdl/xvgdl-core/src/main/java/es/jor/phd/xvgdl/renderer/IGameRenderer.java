package es.jor.phd.xvgdl.renderer;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Game Renderer Interface
 *
 * @author jrquinones
 *
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
