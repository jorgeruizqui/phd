package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.renderer.IGameRenderer;
import com.jrq.xvgdl.util.GameBaseProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Definition for renderer
 *
 * @author jrquinones
 */
@Data
@Slf4j
public class GameRendererDefinition extends GameBaseProperties {

    @JacksonXmlProperty
    private String className;

    /**
     * @return Game Renderer initialized
     */
    public IGameRenderer toModel() {

        try {
            IGameRenderer gameRenderer = (IGameRenderer) Class.forName(this.getClassName()).getDeclaredConstructor().newInstance();
            gameRenderer.setGameRendererDefinition(this);
            return gameRenderer;
        } catch (Exception e) {
            log.error("Exception converting Game Renderer Definition to Game Renderer: " + e.getMessage(), e);
            return null;
        }
    }
}
