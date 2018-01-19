package es.jor.phd.xvgdl.model.objectives;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.engine.GameEngine;

public class PlayerWinsObjective extends AGameObjective {

	@Override
	public double checkObjective(GameContext c) {
		// If player wins, return the weight. If not, return -weight
		return GameEngine.getInstance().gameWinning() ? getScore() * getWeight() : getScore() * getWeight() * - 1d;
	}

}
