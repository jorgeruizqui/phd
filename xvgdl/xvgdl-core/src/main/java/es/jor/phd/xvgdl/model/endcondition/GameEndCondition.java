package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.context.xml.GameEndConditionDefinition;
import lombok.Data;

/**
 * Basic implementation for an End Game Condition.
 * @author Jor
 *
 */
@Data
public class GameEndCondition implements IGameEndCondition {

    private static final String XML_WINNING_CONDITION = "winningCondition";

	/** Game End condition defintion. */
    private GameEndConditionDefinition gameEndConditionDefinition;

    /** Game End condition checker. */
    private IGameEndConditionChecker gameEndConditionChecker;

    @Override
    public boolean checkCondition() {

        boolean rto = true;
        if (gameEndConditionChecker != null) {
            rto = gameEndConditionChecker.checkCondition(GameContext.getInstance(), this);
        }
        return rto;
    }

    public boolean isWinningCondition() {
    	return gameEndConditionDefinition.getBooleanValue(XML_WINNING_CONDITION, Boolean.FALSE);
    }

}
