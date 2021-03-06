package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.rules.GameRule;
import com.jrq.xvgdl.model.rules.GameRuleType;
import com.jrq.xvgdl.model.rules.IGameRule;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Game Rule XML element Definition
 *
 * @author jrquinones
 */
@Slf4j
@Data
public class GameRuleDefinition {

    @JacksonXmlElementWrapper
    private List<GameRuleActionDefinition> ruleActions = new ArrayList<>();
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String gameState;

    /**
     * @return Game Rule initialized with Actions
     */
    public IGameRule toModel() {

        try {
            GameRule gameRule = new GameRule();
            gameRule.setName(this.getName());
            gameRule.setType(GameRuleType.fromString(this.getType()));
            if (this.getRuleActions() != null) this.getRuleActions().forEach(
                    ra -> gameRule.addRuleAction(ra.toModel()));

            gameRule.addGameState(gameState);
            return gameRule;
        } catch (Exception e) {
            log.error("Exception converting GameRuleDefinition to GameRule: " + e.getMessage(), e);
            return null;
        }
    }
}
