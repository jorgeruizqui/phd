package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.xml.GameEndConditionDefinition;
import lombok.Data;

/**
 * Basic implementation for an End Game Condition.
 *
 * @author Jor
 *
 */
@Data
public abstract class AGameEndCondition implements IGameEndCondition {

	private static final String XML_WINNING_CONDITION = "winningCondition";

	/** Game End condition defintion. */
	private GameEndConditionDefinition gameEndConditionDefinition;

	@Override
	public void evolution() {
		// Default implementation of the evolution is empty
	}

	@Override
	public boolean isWinningCondition() {
		return gameEndConditionDefinition != null
				? gameEndConditionDefinition.getBooleanValue(XML_WINNING_CONDITION, Boolean.FALSE) : false;
	}
}
