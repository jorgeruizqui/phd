package es.jor.phd.xvgdl.model.endcondition;

import java.util.concurrent.ThreadLocalRandom;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Timeout End condition.
 *
 */
public class TurnsGameEndCondition extends AGameEndCondition {

    private int numberOfTurns = 0;

    public TurnsGameEndCondition(int turns) {
        this.numberOfTurns = turns;
    }

    @Override
    public boolean checkCondition(GameContext c) {
        boolean rto = false;
        if (c.getTurns() > this.numberOfTurns) {
            rto = true;
        }
        return rto;
    }

    @Override
    public void evolution() {
        // Generate integer between 0 and 10 and + or - according the
        // probability
        double probability = ThreadLocalRandom.current().nextDouble(1.1d);
        int turns = ThreadLocalRandom.current().nextInt(10);
        if (probability >= 0.95d) {
            this.numberOfTurns += turns;
        } else if (probability <= 0.05d) {
            this.numberOfTurns -= turns;
        }

    }

}
