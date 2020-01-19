package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.rules.GameRuleAction;
import com.jrq.xvgdl.model.rules.GameRuleResultType;
import com.jrq.xvgdl.model.rules.IGameRuleAction;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Game Rule XML element Definition
 *
 * @author jrquinones
 */
@Slf4j
@Data
public class GameRuleActionDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private String objectName;
    @JacksonXmlProperty(isAttribute = true)
    private String result;
    @JacksonXmlProperty(isAttribute = true)
    private String value;

    /**
     * @param ruleActionDefinition Rule Action definition
     * @return Game Rule Action initialized
     */
    public static IGameRuleAction convert(GameRuleActionDefinition ruleActionDefinition) {

        try {
            GameRuleAction gameRuleAction = new GameRuleAction();
            gameRuleAction.setObjectName(ruleActionDefinition.getObjectName());
            gameRuleAction.setResultType(GameRuleResultType.fromString(ruleActionDefinition.getResult()));
            gameRuleAction.setValue(ruleActionDefinition.getValue());
            return gameRuleAction;

        } catch (Exception e) {
            log.error("Exception converting GameRuleActionDefinition to GameRuleAction: " + e.getMessage(), e);
            return null;
        }
    }
}
