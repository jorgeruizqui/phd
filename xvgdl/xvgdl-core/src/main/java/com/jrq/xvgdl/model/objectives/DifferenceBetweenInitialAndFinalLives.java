package com.jrq.xvgdl.model.objectives;

import com.jrq.xvgdl.context.GameContext;

public class DifferenceBetweenInitialAndFinalLives extends AGameObjective {

    @Override
    public double checkObjective(GameContext c) {
        double rto = 0;

        // Number of lives lost shouldn't be 0 and shouldn't be the initial
        // lives (force lost some lives... so the game is not so easy)
        if (c.getCurrentGamePlayer().getLives() == c.getCurrentGamePlayer().getInitialLives()
                || c.getCurrentGamePlayer().getLives() == 0) {
            rto = getScore() * 0.1d * getWeight();
        }
        // Lost less than half lives
        else if (c.getCurrentGamePlayer().getLives() >= 0.5d * c.getCurrentGamePlayer().getInitialLives()) {
            rto = getScore() * 0.5d * getWeight();
        }
        // Lost more than half lives
        else if (c.getCurrentGamePlayer().getLives() < 0.5d * c.getCurrentGamePlayer().getInitialLives()) {
            rto = getScore() * 0.8d * getWeight();
        }

        return rto;
    }

}
