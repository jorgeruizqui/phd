package es.jor.phd.xvgdl.pacman;

import es.jor.phd.xvgdl.pacman.genetic.PacmanGeneticAlg;

/**
 * Launcher
 * @author jrquinones
 *
 */
public class LaunchPacmanSimulator {

    /**
     * Main class
     * @param args Arguments
     */
    public static void main(String[] args) {
        PacmanGeneticAlg alg = new PacmanGeneticAlg(5);
        alg.startSimulation();
    }

}
