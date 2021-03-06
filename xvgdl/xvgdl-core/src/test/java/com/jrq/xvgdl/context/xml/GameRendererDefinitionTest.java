package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.renderer.IGameRenderer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameRendererDefinitionTest {

    private GameRendererDefinition gameRendererDefinition;

    @Before
    public void setUp() throws Exception {
        gameRendererDefinition = new GameRendererDefinition();
        gameRendererDefinition.setClassName("com.jrq.xvgdl.renderer.BasicAsciiRenderer");
    }

    @Test
    public void toModelWithCorrectDefinition() {
        IGameRenderer gameRenderer = gameRendererDefinition.toModel();

        assertNotNull(gameRenderer);
    }

    @Test
    public void toModelWithIncorrectDefinition() {
        gameRendererDefinition.setClassName("Invalid");
        IGameRenderer gameRenderer = gameRendererDefinition.toModel();

        assertNull(gameRenderer);
    }
}