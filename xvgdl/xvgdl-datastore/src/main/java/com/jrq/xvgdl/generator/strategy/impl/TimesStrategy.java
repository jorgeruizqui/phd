package com.jrq.xvgdl.generator.strategy.impl;

import com.jrq.xvgdl.generator.strategy.RuleActionStrategy;
import com.jrq.xvgdl.model.rules.GameRuleResultType;

public class TimesStrategy implements RuleActionStrategy {

    @Override
    public boolean supports(GameRuleResultType gameRuleResultType) {
        return GameRuleResultType.TIME_UP.equals(gameRuleResultType) ||
                GameRuleResultType.TIME_DOWN.equals(gameRuleResultType);
    }

    @Override
    public String getRuleActionValue() {
        return "" + random.nextInt(5000);
    }
}
