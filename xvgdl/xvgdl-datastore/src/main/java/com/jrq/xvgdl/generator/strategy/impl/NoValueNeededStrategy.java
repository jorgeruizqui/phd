package com.jrq.xvgdl.generator.strategy.impl;

import com.jrq.xvgdl.generator.strategy.RuleActionStrategy;
import com.jrq.xvgdl.model.rules.GameRuleResultType;

public class NoValueNeededStrategy implements RuleActionStrategy {

    @Override
    public boolean supports(GameRuleResultType gameRuleResultType) {
        return GameRuleResultType.INITIAL_POSITION.equals(gameRuleResultType) ||
                GameRuleResultType.DISAPPEAR.equals(gameRuleResultType) ||
                GameRuleResultType.CANT_MOVE.equals(gameRuleResultType) ||
                GameRuleResultType.LIVES_DOWN.equals(gameRuleResultType) ||
                GameRuleResultType.LIVES_UP.equals(gameRuleResultType) ||
                GameRuleResultType.LIVES_RESET.equals(gameRuleResultType) ||
                GameRuleResultType.CHANGE_DIRECTION.equals(gameRuleResultType) ||
                GameRuleResultType.SCORE_RESET.equals(gameRuleResultType) ||
                GameRuleResultType.LIVES_PERCENTAGE_RESET.equals(gameRuleResultType) ||
                GameRuleResultType.SPEED_RESET.equals(gameRuleResultType) ||
                GameRuleResultType.TIME_RESET.equals(gameRuleResultType) ||
                GameRuleResultType.NONE.equals(gameRuleResultType);
    }

    @Override
    public String getRuleActionValue() {
        return "";
    }
}
