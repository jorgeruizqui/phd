package com.jrq.xvgdl.generator.strategy.impl;

import com.jrq.xvgdl.generator.strategy.RuleActionStrategy;
import com.jrq.xvgdl.model.rules.GameRuleResultType;

public class BounceStrategy implements RuleActionStrategy {

    @Override
    public boolean supports(GameRuleResultType gameRuleResultType) {
        return GameRuleResultType.BOUNCE.equals(gameRuleResultType);
    }

    @Override
    public String getRuleActionValue() {
        return "";
    }
}
