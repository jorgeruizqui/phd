package com.jrq.xvgdl.pacman.experiment.challenging;

import com.jrq.xvgdl.pacman.experiment.optimization.PacmanOptimization;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher for the Pacman Challenging Process
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchPacmanChallenging {

    public static void main(String[] args) {
        try {
            PacmanChallenging pacmanChallenging = new PacmanChallenging();
            pacmanChallenging.configureOptimizationAndRun();
        } catch (Exception e) {
            log.error("Exception running Pacman Challenging: " + e.getMessage(), e);
            System.exit(-1);
        }
    }

}
