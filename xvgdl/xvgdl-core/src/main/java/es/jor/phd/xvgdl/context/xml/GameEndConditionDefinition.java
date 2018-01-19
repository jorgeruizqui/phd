package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game End Condition XML element Definition
 *
 * @author jrquinones
 *
 */
public class GameEndConditionDefinition extends Properties {

    /** XML main tag. */
    public static final String XMLTAG = "endCondition";

    /** XML Attribute. Class Name. */
    public static final String XMLATTR_CHECKER_CLASS = "checkerClass";

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }

    /**
     *
     * @param endConditionDefinition Object definition
     * @return Game End Condition initialized
     */
    public static IGameEndCondition convert(GameEndConditionDefinition endConditionDefinition) {

    	IGameEndCondition gameEndCondition = null;

        try {
        	gameEndCondition = (IGameEndCondition) Class.forName(
                    endConditionDefinition.getProperty(XMLATTR_CHECKER_CLASS)).newInstance();
            gameEndCondition.setGameEndConditionDefinition(endConditionDefinition);

        } catch (Exception e) {
            ELogger.error(GameEndConditionDefinition.class, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Exception converting GameEndConditionDefinition to IGameEndCondition: " + e.getMessage(), e);
            gameEndCondition = null;
        }

        return gameEndCondition;
    }
}
