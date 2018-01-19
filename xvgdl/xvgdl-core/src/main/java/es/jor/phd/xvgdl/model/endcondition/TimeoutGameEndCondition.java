package es.jor.phd.xvgdl.model.endcondition;

import java.util.concurrent.ThreadLocalRandom;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Timeout End condition.
 *
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
