package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.model.event.AGameEvent;
import es.jor.phd.xvgdl.model.event.GameEventType;
import es.jor.phd.xvgdl.model.event.IGameEvent;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Event XML element Definition
 *
 * @author jrquinones
 *
 */
public class GameEventDefinition extends Properties {

    /** XML main tag. */
    public static final String XMLTAG = "event";

    /** XML Attribute. Class Name. */
    public static final String XMLATTR_CLASS_NAME = "className";

    /** XML Attribute. Type. */
    public static final String XMLATTR_TYPE = "type";

    /** XML Attribute. Timer. */
    public static final String XMLATTR_TIMER = "timer";

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }

    /**
     *
     * @param eventDefinition Object definition
     * @return Game Event initialized
     */
    public static IGameEvent convert(GameEventDefinition eventDefinition) {

        AGameEvent gameEvent = null;

        try {
            gameEvent = (AGameEvent) Class.forName(
                    eventDefinition.getProperty(XMLATTR_CLASS_NAME)).newInstance();
            gameEvent.setEventType(GameEventType.fromString(eventDefinition.getProperty(XMLATTR_TYPE)));
            gameEvent.setTimer(eventDefinition.getLongValue(XMLATTR_TIMER, 0));
            gameEvent.setGameEventDefinition(eventDefinition);

        } catch (Exception e) {
            ELogger.error(GameEventDefinition.class, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Exception converting GameEventDefinition to GameEvent: " + e.getMessage(), e);
            gameEvent = null;
        }

        return gameEvent;
    }
}
