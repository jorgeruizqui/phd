package com.jrq.xvgdl.renderer;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameRendererDefinition;

/**
 * Game Renderer Interface
 *
 * @author jrquinones
 */
public interface IGameRenderer {

    void initializeRenderer(GameContext gameContext);

    void render();

    void renderGameFinished();

    void setGameRendererDefinition(GameRendererDefinition gameRendererDefinition);
}
