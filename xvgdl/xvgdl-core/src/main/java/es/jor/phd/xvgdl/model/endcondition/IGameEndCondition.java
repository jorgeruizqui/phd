package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.xml.GameEndConditionDefinition;

/**
 * Game condition interface definition
 *
 */
public interface IGameEndCondition {

    /**
     *
     * @param def Definition to be set
     */
    void setGameEndConditionDefinition(GameEndConditionDefinition def);

    /**
     *
     * @param checker Condition checker
     */
    void setGameEndConditionChecker(IGameEndConditionChecker checker);

    /**
     * Checks the condition.
     * @return <code>true</code> if the condition is accomplished. Otherwise <code>false</code>
     *
     */
    boolean checkCondition();

    /**
     *
     * @return End condition definition
     */
    GameEndConditionDefinition getGameEndConditionDefinition();

    /**
     *
     * @return If the condition is a winning condition
     */
    boolean isWinningCondition();
}
