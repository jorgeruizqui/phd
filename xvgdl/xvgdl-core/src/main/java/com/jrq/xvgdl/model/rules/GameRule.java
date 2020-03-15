package com.jrq.xvgdl.model.rules;

import com.jrq.xvgdl.context.xml.GameRuleDefinition;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game Rule
 *
 * @author jrquinones
 */
@Data
public class GameRule implements IGameRule {

    private String name;
    private GameRuleType type;
    private GameRuleDefinition gameRuleDefinition;
    private String gameState;

    /**
     * Rule Actions.
     */
    private List<IGameRuleAction> ruleActions = new ArrayList<>();

    @Override
    public void addRuleAction(IGameRuleAction action) {
        this.ruleActions.add(action);
    }

    @Override
    public IGameRuleAction getRuleActionByObjectName(String objectName) {
        return ruleActions.stream().filter(s -> s.getObjectName().equalsIgnoreCase(objectName)).findFirst()
                .orElse(null);
    }

    @Override
    public void evolution() {
        ruleActions.stream().forEach(this::evolveRuleAction);
    }

    private void evolveRuleAction(IGameRuleAction gameRuleAction) {
        if (ThreadLocalRandom.current().nextDouble() >= 0.95d) {
            GameRuleUtils.evolutionOfRuleAction(gameRuleAction);
        }
    }
}
