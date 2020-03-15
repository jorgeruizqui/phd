package com.jrq.xvgdl.model.objectives;

import com.jrq.xvgdl.context.GameContext;

public class MaximizeTurnsObjective extends AGameObjective {

    @Override
    public Double checkObjective(GameContext c) {
        // The higher turns, the higher value returned
        return c.getTurns() * getWeight();
    }

}
