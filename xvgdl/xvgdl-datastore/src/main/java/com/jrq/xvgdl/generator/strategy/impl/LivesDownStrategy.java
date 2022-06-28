package com.jrq.xvgdl.generator.strategy.impl;

import com.jrq.xvgdl.generator.strategy.RuleActionStrategy;
import com.jrq.xvgdl.model.rules.GameRuleResultType;

public class LivesDownStrategy implements RuleActionStrategy {

    @Override
    public boolean supports(GameRuleResultType gameRuleResultType) {
        return GameRuleResultType.LIVES_UP.equals(gameRuleResultType);
    }

    @Override
    public String getRuleActionValue() {
        return "" + random.nextInt(3);
    }
}
