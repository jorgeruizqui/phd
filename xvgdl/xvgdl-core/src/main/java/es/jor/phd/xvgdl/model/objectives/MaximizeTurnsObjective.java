package es.jor.phd.xvgdl.model.objectives;

import es.jor.phd.xvgdl.context.GameContext;

public class MaximizeTurnsObjective extends AGameObjective {

    @Override
    public double checkObjective(GameContext c) {
        // The higher turns, the higher value returned
        return c.getTurns() * getWeight();
    }

}
