package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Game End Condition XML element Definition
 *
 * @author jrquinones
 */
@Slf4j
@Data
public class GameEndConditionDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private String checkerClass;
    @JacksonXmlProperty(isAttribute = true)
    private String objectNames;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean winningCondition;
    @JacksonXmlProperty(isAttribute = true)
    private String property;
    @JacksonXmlProperty(isAttribute = true)
    private String value;

    /**
    * @return Game End Condition initialized
     */
    public IGameEndCondition toModel() {

        try {
            IGameEndCondition gameEndCondition = (IGameEndCondition) Class.forName(
                    this.getCheckerClass()).getDeclaredConstructor().newInstance();
            gameEndCondition.setGameEndConditionDefinition(this);
            return gameEndCondition;
        } catch (Exception e) {
            log.error("Exception converting GameEndConditionDefinition to IGameEndCondition: " + e.getMessage(), e);
            return null;
        }
    }
}
