package com.jrq.xvgdl.model.rules;

import java.util.List;

/**
 * Game Rule interface
 *
 * @author jrquinones
 */
public interface IGameRule {

    /**
     * @return Rule Name
     */
    String getName();

    /**
     * @return Game Rule Type
     */
    GameRuleType getType();

    /**
     * @param action Rule Action to be added
     */
    void addRuleAction(IGameRuleAction action);

    /**
     * @return List of game Rule Actions.
     */
    List<IGameRuleAction> getRuleActions();

    /**
     * @param objectName Object Name Reference
     * @return The game rule action associated with that name or null if not
     * exits
     */
    IGameRuleAction getRuleActionByObjectName(String objectName);

    /**
     * Evolve game Rule
     */
    void evolution();

    void setGameState(String gameState);
}
