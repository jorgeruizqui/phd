package com.jrq.xvgdl.model.objectives;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameObjectiveDefinition;

public interface IGameObjective {

    /**
     * Checks the objective and returns its value for the fitness function
     *
     * @return
     */
    Double checkObjective(GameContext c);

    /**
     * @return the score
     */
    Double getScore();

    /**
     * @return the weight
     */
    Double getWeight();

    GameObjectiveDefinition getGameObjectiveDefinition();

    void setGameObjectiveDefinition(GameObjectiveDefinition gameObjectiveDefinition);
}
