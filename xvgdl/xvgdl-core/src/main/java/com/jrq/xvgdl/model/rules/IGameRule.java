package com.jrq.xvgdl.model.rules;

import com.jrq.xvgdl.context.GameContext;

import java.util.List;

/**
 * Game Rule interface
 *
 * @author jrquinones
 */
public interface IGameRule {

    String getName();

    Boolean getFixed();

    GameRuleType getType();

    List<IGameRuleAction> getRuleActions();

    IGameRuleAction getRuleActionByObjectName(String objectName);

    List<String> getGameStates();

    void evolution();

    void addRuleAction(IGameRuleAction action);

    boolean applyGameRule(GameContext gameContext);
}
