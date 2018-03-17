package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;
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
     * Checks the condition.
     * 
     * @return <code>true</code> if the condition is accomplished. Otherwise
     *         <code>false</code>
     *
     */
    boolean checkCondition(GameContext c);

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

    /**
     * Evolves the game condition according the implementation.
     */
    void evolution();
}
