package com.jrq.xvgdl.pacman.experiment.optimization;

import lombok.extern.slf4j.Slf4j;

/**
 * Launcher for the Pacman Optimization Process
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchPacmanOptimization {

    public static void main(String[] args) {
        try {
            PacmanOptimization pacmanOptimization = new PacmanOptimization();
            pacmanOptimization.configureOptimizationAndRun();
        } catch (Exception e) {
            log.error("Exception running Pacman Optimization: " + e.getMessage(), e);
            System.exit(-1);
        }
    }

}
