package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Game End condition checker interface
 *
 */
public interface IGameEndConditionChecker {

    /**
     *
     * @param c Game Context
     * @param gameEndCondition Game end condition
     * @return <code>true</code> if the condition is accomplished. <code>false</code> otherwise
     */
    boolean checkCondition(GameContext c, IGameEndCondition gameEndCondition);
}
