package es.jor.phd.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import es.jor.phd.xvgdl.model.event.AGameEvent;
import es.jor.phd.xvgdl.model.event.GameEventType;
import es.jor.phd.xvgdl.model.event.IGameEvent;
import es.jor.phd.xvgdl.model.event.KeyboardGameEvent;
import es.jor.phd.xvgdl.util.GameConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Properties;

/**
 * Game Event XML element Definition
 *
 * @author jrquinones
 *
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

    /**
     *
     * @param eventDefinition Object definition
     * @return Game Event initialized
     */
    public static IGameEvent convert(GameEventDefinition eventDefinition) {

        try {
            AGameEvent gameEvent = gameEvent = (AGameEvent) Class.forName(eventDefinition.getClassName()).getDeclaredConstructor().newInstance();
            gameEvent.setEventType(GameEventType.fromString(eventDefinition.getType()));
            gameEvent.setTimer(Optional.ofNullable(eventDefinition.getTimer()).orElse(0L));
            gameEvent.setGameEventDefinition(eventDefinition);

            if (gameEvent instanceof KeyboardGameEvent) {
                ((KeyboardGameEvent) gameEvent).setKeyCode(eventDefinition.getKeyCode());
            }
            return gameEvent;
        } catch (Exception e) {
            log.error("Exception converting GameEventDefinition to GameEvent: " + e.getMessage(), e);
            return null;
        }
    }
}
