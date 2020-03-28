package com.jrq.xvgdl.pacman;

import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.pacman.genetic.PacmanGeneticAlg;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchPacmanSimulator {

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
        try {
            PacmanGeneticAlg alg = new PacmanGeneticAlg(5, 100);
            alg.startSimulation();
        } catch (XvgdlException e) {
            log.error("Exception running Pacman Genetic Algorithm.", e);
            System.exit(-1);
        }
    }

}
