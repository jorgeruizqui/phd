package es.jor.phd.xvgdl.pacman;

import es.jor.phd.xvgdl.app.GameApp;
import es.jor.phd.xvgdl.engine.GameEngine;

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

        GameEngine ge = GameApp.launchGameApp("/engine/simul/pacmanSimulatorEngineConfiguration.xml");
        ge.start();
    }

}
