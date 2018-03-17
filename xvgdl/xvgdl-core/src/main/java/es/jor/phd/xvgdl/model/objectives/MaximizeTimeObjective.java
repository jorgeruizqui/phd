package es.jor.phd.xvgdl.model.objectives;

import es.jor.phd.xvgdl.context.GameContext;

public class MaximizeTimeObjective extends AGameObjective {

    @Override
    public double checkObjective(GameContext c) {
        // The higher time, the higher value returned
        return c.getTimePlayed() * getWeight();
    }

}
