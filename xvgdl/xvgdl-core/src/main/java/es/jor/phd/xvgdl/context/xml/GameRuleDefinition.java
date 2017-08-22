package es.jor.phd.xvgdl.context.xml;

import java.util.ArrayList;
import java.util.List;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.model.rules.GameRule;
import es.jor.phd.xvgdl.model.rules.GameRuleType;
import es.jor.phd.xvgdl.model.rules.IGameRule;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Rule XML element Definition
 *
 * @author jrquinones
 *
 */
public class GameRuleDefinition extends Properties {

    /** XML main tag. */
    public static final String XMLTAG = "rule";

    /** XML Attribute. Name. */
    public static final String XMLATTR_NAME = "name";

    /** XML Attribute. Type. */
    public static final String XMLATTR_TYPE = "type";

    /** List of Action Definitions. */
    private List<GameRuleActionDefinition> actionDefinitions = new ArrayList<>();

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }

    /**
     *
     * @param actionDefinition Action Definition.
     */
    public void addActionDefinition(GameRuleActionDefinition actionDefinition) {
        actionDefinitions.add(actionDefinition);
    }

    /**
     *
     * @param ruleDefinition Object definition
     * @return Game Rule initialized with Actions
     */
    public IGameRule convert(GameRuleDefinition ruleDefinition) {

        GameRule gameRule = null;

        try {
            gameRule = new GameRule();
            gameRule.setName(ruleDefinition.getProperty(XMLATTR_NAME));
            gameRule.setType(GameRuleType.fromString(ruleDefinition.getProperty(XMLATTR_TYPE)));

            for (GameRuleActionDefinition ruleActionDefinition : actionDefinitions) {
                gameRule.addRuleAction(GameRuleActionDefinition.convert(ruleActionDefinition));
            }

        } catch (Exception e) {
            ELogger.error(GameMapDefinition.class, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Exception converting GameRuleDefinition to GameRule: " + e.getMessage(), e);
            gameRule = null;
        }

        return gameRule;
    }
}
