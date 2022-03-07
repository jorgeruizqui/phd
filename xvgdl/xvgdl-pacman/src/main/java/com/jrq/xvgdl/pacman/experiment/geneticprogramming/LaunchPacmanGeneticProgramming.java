package com.jrq.xvgdl.pacman.experiment.geneticprogramming;

import com.jrq.xvgdl.app.XvgdlGameApp;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher for the Pacman Generational Genetic Programming
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchPacmanGeneticProgramming {

    public static void main(String[] args) {
        try {
            PacmanGenerationalGeneticProgramming pacmanGenerationalGP = new PacmanGenerationalGeneticProgramming();
            pacmanGenerationalGP.configureGeneticAlgorithmAndRun();
        } catch (Exception e) {
            log.error("Exception running Pacman Generational Genetic Programming: " + e.getMessage(), e);
            System.exit(-1);
        }
    }

}
