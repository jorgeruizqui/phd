package com.jrq.xvgdl.generator.strategy;

import com.jrq.xvgdl.model.rules.GameRuleAction;
import com.jrq.xvgdl.model.rules.GameRuleResultType;

import java.util.Random;

public interface RuleActionStrategy {

    public static Random random = new Random();

    boolean supports(GameRuleResultType gameRuleResultType);

    String getRuleActionValue();
}
