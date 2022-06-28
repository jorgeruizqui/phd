package com.jrq.xvgdl.generator.strategy.impl;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.generator.strategy.RuleActionStrategy;
import com.jrq.xvgdl.model.rules.GameRuleResultType;

public class StateTransitionStrategy implements RuleActionStrategy {

    @Override
    public boolean supports(GameRuleResultType gameRuleResultType) {
        return GameRuleResultType.STATE_TRANSITION.equals(gameRuleResultType);
    }

    @Override
    // Number of milliseconeds
    public String getRuleActionValue() {
        return "default";
    }
}
