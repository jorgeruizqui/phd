package com.jrq.xvgdl.pacman.model.objectives;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameObjectiveDefinition;
import com.jrq.xvgdl.model.objectives.IGameObjective;
import com.jrq.xvgdl.pacman.experiment.optimization.PacmanOptimizationSolution;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PacmanSolutionEvaluation implements IGameObjective {

    private PacmanOptimizationSolution pacmanOptimizationSolution;
    private GameObjectiveDefinition gameObjectiveDefinition;

    @Override
    public Double checkObjective(GameContext c) {
        return getScore();
    }

    @Override
    public Double getScore() {
        return (double) pacmanOptimizationSolution.getScore();
    }

    @Override
    public Double getWeight() {
        return 1.0d;
    }

    @Override
    public GameObjectiveDefinition getGameObjectiveDefinition() {
        return gameObjectiveDefinition;
    }

    @Override
    public void setGameObjectiveDefinition(GameObjectiveDefinition gameObjectiveDefinition) {
        this.gameObjectiveDefinition = gameObjectiveDefinition;
    }
}
