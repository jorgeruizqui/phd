package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.engine.GameEngine;

/**
 * Timeout End condition.
 *
 */
public class LivesZeroGameEndCondition extends AGameEndCondition {

    @Override
    public boolean checkCondition(GameContext c) {
        boolean rto = false;
        if (GameEngine.getInstance().getGameContext().getCurrentGamePlayer().getLives() <= 0) {
            rto = true;
        }
        return rto;
    }

}
