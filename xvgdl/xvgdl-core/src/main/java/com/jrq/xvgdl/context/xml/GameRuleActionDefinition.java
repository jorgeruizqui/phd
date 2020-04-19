package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.rules.GameRuleResultType;
import com.jrq.xvgdl.model.rules.IGameRuleAction;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Game Rule XML element Definition
 *
 * @author jrquinones
 */
@Slf4j
@Data
public class GameRuleActionDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private String className;
    @JacksonXmlProperty(isAttribute = true)
    private String objectName;
    @JacksonXmlProperty(isAttribute = true)
    private String result;
    @JacksonXmlProperty(isAttribute = true)
    private String value;

    /**
     * @return Game Rule Action initialized
     */
    public IGameRuleAction toModel() {

        try {
            String clazz = Optional.ofNullable(this.getClassName()).orElse("com.jrq.xvgdl.model.rules.GameRuleAction");
            IGameRuleAction gameRuleAction = (IGameRuleAction) Class.forName(clazz).getDeclaredConstructor().newInstance();
            gameRuleAction.setObjectName(this.getObjectName());
            gameRuleAction.setResultType(GameRuleResultType.fromString(this.getResult()));
            gameRuleAction.setValue(this.getValue());
            return gameRuleAction;

        } catch (Exception e) {
            log.error("Exception converting GameRuleActionDefinition to GameRuleAction: " + e.getMessage(), e);
            return null;
        }
    }
}
