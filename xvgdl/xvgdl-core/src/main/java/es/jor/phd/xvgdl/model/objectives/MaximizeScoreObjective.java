package es.jor.phd.xvgdl.model.objectives;

import es.jor.phd.xvgdl.context.GameContext;

public class MaximizeScoreObjective extends AGameObjective {

	@Override
	public double checkObjective(GameContext c) {
		// The higher score, the higher value returned
		return c.getCurrentGamePlayer().getScore() * getWeight();
	}

}
