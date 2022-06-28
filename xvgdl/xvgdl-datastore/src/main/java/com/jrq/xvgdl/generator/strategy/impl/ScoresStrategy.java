package com.jrq.xvgdl.generator.strategy.impl;

import com.jrq.xvgdl.generator.strategy.RuleActionStrategy;
import com.jrq.xvgdl.model.rules.GameRuleResultType;

public class ScoresStrategy implements RuleActionStrategy {

    @Override
    public boolean supports(GameRuleResultType gameRuleResultType) {
        return GameRuleResultType.SCORE_DOWN.equals(gameRuleResultType) ||
                GameRuleResultType.SCORE_UP.equals(gameRuleResultType) ||
                GameRuleResultType.SCORE_SET_TO.equals(gameRuleResultType);
    }

    @Override
    public String getRuleActionValue() {
        return "" + random.nextInt(5000);
    }
}
