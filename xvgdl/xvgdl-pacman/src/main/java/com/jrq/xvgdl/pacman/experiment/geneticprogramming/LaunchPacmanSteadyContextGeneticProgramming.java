package com.jrq.xvgdl.pacman.experiment.geneticprogramming;

import lombok.extern.slf4j.Slf4j;

/**
 * Launcher for the Pacman Generational Genetic Programming
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchPacmanSteadyContextGeneticProgramming {

    public static void main(String[] args) {
        try {
            PacmanSteadyContextGeneticProgramming pacmanSteadyContextGeneticProgramming = new PacmanSteadyContextGeneticProgramming();
            pacmanSteadyContextGeneticProgramming.configureGeneticAlgorithmAndRun();
        } catch (Exception e) {
            log.error("Exception running Pacman Steady Context Genetic Programming: " + e.getMessage(), e);
            System.exit(-1);
        }
    }

}
