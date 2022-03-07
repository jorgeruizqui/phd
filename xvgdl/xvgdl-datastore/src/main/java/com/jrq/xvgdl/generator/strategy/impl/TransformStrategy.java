package com.jrq.xvgdl.generator.strategy.impl;

import com.jrq.xvgdl.generator.strategy.RuleActionStrategy;
import com.jrq.xvgdl.model.rules.GameRuleResultType;

public class TransformStrategy implements RuleActionStrategy {

    @Override
    public boolean supports(GameRuleResultType gameRuleResultType) {
        return GameRuleResultType.TRANSFORM.equals(gameRuleResultType);
    }

    @Override
    // Number of milliseconeds
    public String getRuleActionValue() {
        return "wall";
    }
}
