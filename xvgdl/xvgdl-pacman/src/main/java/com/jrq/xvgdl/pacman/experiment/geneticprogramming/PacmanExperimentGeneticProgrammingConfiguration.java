package com.jrq.xvgdl.pacman.experiment.geneticprogramming;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class PacmanExperimentGeneticProgrammingConfiguration {

    private static final int MIN_BIG_DOTS = 1;
    private static final int MAX_BIG_DOTS = 5;
    private static final int MIN_LIVES = 1;
    private static final int MAX_LIVES = 5;
    private static final int MIN_DOTS_PERCENTAGE = 10;
    private static final int MAX_DOTS_PERCENTAGE = 45;
    private static final int MIN_GAMEPLAY_TIMEOUT = 10000;
    private static final int MAX_GAMEPLAY_TIMEOUT = 40000;
    private static final int MIN_GHOSTS = 1;
    private static final int MAX_GHOSTS = 5;

    private int ghosts;
    private int dotsPercentage;
    private int bigDots;
    private int lives;
    private int gamePlayTimeout;

    public static PacmanExperimentGeneticProgrammingConfiguration generateInitialPoint() {
        return PacmanExperimentGeneticProgrammingConfiguration.builder().
                bigDots(ThreadLocalRandom.current().nextInt(MIN_BIG_DOTS, MAX_BIG_DOTS + 1)).
                lives(ThreadLocalRandom.current().nextInt(MIN_LIVES, MAX_LIVES + 1)).
                dotsPercentage(ThreadLocalRandom.current().nextInt(MIN_DOTS_PERCENTAGE, MAX_DOTS_PERCENTAGE + 1)).
                gamePlayTimeout(ThreadLocalRandom.current().nextInt(MIN_GAMEPLAY_TIMEOUT, MAX_GAMEPLAY_TIMEOUT + 1)).
                ghosts(ThreadLocalRandom.current().nextInt(MIN_GHOSTS, MAX_GHOSTS + 1)).
                build();
    }


    public String toCsv(int evalualtionNumber) {
        return evalualtionNumber + "$"
                + this.getGhosts() + "$"
                + this.getDotsPercentage() + "$"
                + this.getBigDots() + "$"
                + this.getLives() + "$"
                + this.getGamePlayTimeout() + "$";
    }
}
