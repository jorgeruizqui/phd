package com.jrq.xvgdl.model.endcondition;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;

/**
 * Timeout End condition.
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
