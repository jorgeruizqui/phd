package com.jrq.xvgdl.pacman;

import com.jrq.xvgdl.pacman.genetic.PacmanGeneticAlg;

/**
 * Launcher
 *
 * @author jrquinones
 */
public class LaunchPacmanSimulator {

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
        PacmanGeneticAlg alg = new PacmanGeneticAlg(5, 100);
        alg.startSimulation();
    }

}
