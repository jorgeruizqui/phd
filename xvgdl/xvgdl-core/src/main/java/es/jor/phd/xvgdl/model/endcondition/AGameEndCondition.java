package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.xml.GameEndConditionDefinition;
import lombok.Data;

import java.util.Optional;

/**
 * Basic implementation for an End Game Condition.
 *
 * @author Jor
 *
 */
@Data
public abstract class AGameEndCondition implements IGameEndCondition {

    /** Game End condition defintion. */
    private GameEndConditionDefinition gameEndConditionDefinition;

    @Override
    public void evolution() {
        // Default implementation of the evolution is empty
    }

    @Override
    public boolean isWinningCondition() {
        return gameEndConditionDefinition != null
                ? Optional.ofNullable(gameEndConditionDefinition.getWinningCondition()).orElse(false) : false;
    }
}
