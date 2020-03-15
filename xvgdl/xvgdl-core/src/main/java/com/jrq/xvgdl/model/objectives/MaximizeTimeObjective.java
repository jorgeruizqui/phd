package com.jrq.xvgdl.model.objectives;

import com.jrq.xvgdl.context.GameContext;

public class MaximizeTimeObjective extends AGameObjective {

    @Override
    public Double checkObjective(GameContext c) {
        // The higher time, the higher value returned
        return c.getTimePlayed() * getWeight();
    }

}
