package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.engine.GameEngine;

/**
 * Timeout End condition.
 *
 */
public class TurnsGameEndCondition implements IGameEndConditionChecker {
    
    private int numberOfTurns = 0;
    
    
    public TurnsGameEndCondition(int turns) {
        this.numberOfTurns = turns;
    }

    @Override
    public boolean checkCondition(GameContext c, IGameEndCondition gameEndCondition) {
        boolean rto = false;
        if (GameEngine.getInstance().getTurns() > this.numberOfTurns) {
            rto = true;
        }
        return rto;
    }

}
