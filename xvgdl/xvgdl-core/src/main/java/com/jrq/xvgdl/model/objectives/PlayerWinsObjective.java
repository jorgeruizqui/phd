package com.jrq.xvgdl.model.objectives;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;

public class PlayerWinsObjective extends AGameObjective {

    @Override
    public Double checkObjective(GameContext c) {
        // If player wins, return the weight. If not, return -weight
        return GameEngine.getInstance().isWinningGame() ? getScore() * getWeight() : getScore() * getWeight() * -1d;
    }

}
