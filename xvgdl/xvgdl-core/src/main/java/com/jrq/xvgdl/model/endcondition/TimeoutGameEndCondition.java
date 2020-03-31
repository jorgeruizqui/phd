package com.jrq.xvgdl.model.endcondition;

import com.jrq.xvgdl.context.GameContext;

/**
 * Timeout End condition.
 */
public class TimeoutGameEndCondition extends AGameEndCondition {

    @Override
    public boolean checkCondition(GameContext c) {
        boolean rto = false;

        Long timeout = Long.parseLong(getGameEndConditionDefinition().getValue());
        if (timeout > 0 && c.getStartTime() > 0) {
            rto = System.currentTimeMillis() - c.getStartTime() > timeout;
        }
        return rto;
    }

}
