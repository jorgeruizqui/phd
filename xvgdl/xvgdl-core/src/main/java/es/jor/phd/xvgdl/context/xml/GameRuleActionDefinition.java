package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.model.rules.GameRuleAction;
import es.jor.phd.xvgdl.model.rules.GameRuleResultType;
import es.jor.phd.xvgdl.model.rules.IGameRuleAction;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Rule XML element Definition
 *
 * @author jrquinones
 *
 */
public class GameRuleActionDefinition extends Properties {

    /** XML main tag. */
    public static final String XMLTAG = "ruleAction";

    /** XML Attribute. Object Name. */
    public static final String XMLATTR_OBJECT_NAME = "objectName";

    /** XML Attribute. Result. */
    public static final String XMLATTR_RESULT = "result";

    /** XML Attribute. Value. */
    public static final String XMLATTR_VALUE = "value";

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }

    /**
     *
     * @param ruleActionDefinition Rule Action definition
     * @return Game Rule Action initialized
     */
    public static IGameRuleAction convert(GameRuleActionDefinition ruleActionDefinition) {

        GameRuleAction gameRuleAction = null;

        try {
            gameRuleAction = new GameRuleAction();
            gameRuleAction.setObjectName(ruleActionDefinition.getProperty(XMLATTR_OBJECT_NAME));
            gameRuleAction
                    .setResultType(GameRuleResultType.fromString(ruleActionDefinition.getProperty(XMLATTR_RESULT)));
            gameRuleAction.setValue(ruleActionDefinition.getProperty(XMLATTR_VALUE, ""));

        } catch (Exception e) {
            ELogger.error(GameMapDefinition.class, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Exception converting GameRuleActionDefinition to GameRuleAction: " + e.getMessage(), e);
            gameRuleAction = null;
        }

        return gameRuleAction;
    }
}
