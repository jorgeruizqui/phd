package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.event.AGameEvent;
import com.jrq.xvgdl.model.event.GameEventType;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.event.KeyboardGameEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Game Event XML element Definition
 *
 * @author jrquinones
 */
@Slf4j
@Data
public class GameEventDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String className;
    @JacksonXmlProperty(isAttribute = true)
    private String objectName;
    @JacksonXmlProperty(isAttribute = true)
    private Long timer;
    @JacksonXmlProperty(isAttribute = true)
    private Integer keyCode;
    @JacksonXmlProperty(isAttribute = true)
    private Double value;
    @JacksonXmlProperty(isAttribute = true)
    private String gameState;

    /**
     * @return Game Event initialized
     */
    public IGameEvent toModel() {

        try {
            AGameEvent gameEvent = gameEvent = (AGameEvent) Class.forName(this.getClassName()).getDeclaredConstructor().newInstance();
            gameEvent.setEventType(GameEventType.fromString(this.getType()));
            gameEvent.setTimer(Optional.ofNullable(this.getTimer()).orElse(0L));
            gameEvent.setGameEventDefinition(this);
            gameEvent.addGameState(this.getGameState());
            if (gameEvent instanceof KeyboardGameEvent) {
                ((KeyboardGameEvent) gameEvent).setKeyCode(this.getKeyCode());
            }
            return gameEvent;
        } catch (Exception e) {
            log.error("Exception converting GameEventDefinition to GameEvent: " + e.getMessage(), e);
            return null;
        }
    }
}
