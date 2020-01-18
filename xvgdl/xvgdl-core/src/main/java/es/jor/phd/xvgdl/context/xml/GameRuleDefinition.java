package es.jor.phd.xvgdl.context.xml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import es.jor.phd.xvgdl.model.rules.GameRule;
import es.jor.phd.xvgdl.model.rules.GameRuleType;
import es.jor.phd.xvgdl.model.rules.IGameRule;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Game Rule XML element Definition
 *
 * @author jrquinones
 *
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
    private String gameState = "";
    /**
     *
     * @param ruleDefinition Object definition
     * @return Game Rule initialized with Actions
     */
    public IGameRule convert(GameRuleDefinition ruleDefinition) {

        try {
            GameRule gameRule = new GameRule();
            gameRule.setName(ruleDefinition.getName());
            gameRule.setType(GameRuleType.fromString(ruleDefinition.getType()));
            ruleActions.forEach(ra -> gameRule.addRuleAction(GameRuleActionDefinition.convert(ra)));

            return gameRule;
        } catch (Exception e) {
            log.error("Exception converting GameRuleDefinition to GameRule: " + e.getMessage(), e);
            return null;
        }

    }
}
