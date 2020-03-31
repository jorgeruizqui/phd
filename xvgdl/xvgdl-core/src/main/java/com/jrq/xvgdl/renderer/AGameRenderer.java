package com.jrq.xvgdl.renderer;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameRendererDefinition;
import lombok.Data;

@Data
public abstract class AGameRenderer implements IGameRenderer {

    protected GameContext gameContext;
    private GameRendererDefinition gameRendererDefinition;

}
