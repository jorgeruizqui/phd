package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Player Lives End condition.
 *
 */
public class LivesGameEndCondition implements IGameEndConditionChecker {

    @Override
    public boolean checkCondition(GameContext c, IGameEndCondition gameEndCondition) {
        boolean rto = false;
        if (c.getCurrentGamePlayer().getLives() <= 0) {
            rto = true;
        }
        return rto;
    }

}
