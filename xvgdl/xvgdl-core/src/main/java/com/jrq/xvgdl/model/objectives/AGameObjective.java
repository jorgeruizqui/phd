package com.jrq.xvgdl.model.objectives;

import com.jrq.xvgdl.context.xml.GameObjectiveDefinition;
import lombok.Data;

@Data
public abstract class AGameObjective implements IGameObjective {

    private GameObjectiveDefinition gameObjectiveDefinition;

    public Double getScore() {
        return gameObjectiveDefinition.getScore();
    }

    public Double getWeight() {
        return gameObjectiveDefinition.getWeight();
    }
}

