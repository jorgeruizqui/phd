package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.context.xml.GameEndConditionDefinition;

/**
 * Basic implementation for an End Game Condition.
 * @author Jor
 *
 */
public class GameEndCondition implements IGameEndCondition {

    /** Game End condition defintion. */
    private GameEndConditionDefinition definition;

    /** Game End condition checker. */
    private IGameEndConditionChecker checker;

    @Override
    public void setGameEndConditionDefinition(GameEndConditionDefinition def) {
        this.definition = def;
    }

    @Override
    public void setGameEndConditionChecker(IGameEndConditionChecker checker) {
        this.checker = checker;
    }

    @Override
    public boolean checkCondition() {

        boolean rto = true;
        if (checker != null) {
            rto = checker.checkCondition(GameContext.getInstance(), this);
        }
        return rto;
    }

    @Override
    public GameEndConditionDefinition getGameEndConditionDefinition() {
        return this.definition;
    }
    
    @Override
    public String toString() {
        return checker.toString();
    }

}
