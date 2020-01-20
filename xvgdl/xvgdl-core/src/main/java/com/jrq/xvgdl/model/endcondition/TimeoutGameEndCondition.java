package com.jrq.xvgdl.model.endcondition;

import com.jrq.xvgdl.context.GameContext;

/**
 * Timeout End condition.
 */
public class TimeoutGameEndCondition extends AGameEndCondition {

    @Override
    public boolean checkCondition(GameContext c) {
        boolean rto = false;
        if (c.getTimeout() > 0 && c.getStartTime() > 0) {
            rto = System.currentTimeMillis() - c.getStartTime() > c.getTimeout();
        }
        return rto;
    }

}
