package com.jrq.xvgdl.model.objectives;

import lombok.Data;

@Data
public abstract class AGameObjective implements IGameObjective {

    private double weight;
    private double score;
}
