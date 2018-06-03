package es.jor.phd.xvgdl.tetris;

import es.jor.phd.xvgdl.app.GameApp;
import es.jor.phd.xvgdl.engine.GameEngine;

/**
 * Launcher
 * @author jrquinones
 *
 */
public class LaunchTetris {

    /**
     * Main class
     * @param args Arguments
     */
    public static void main(String[] args) {

        GameEngine ge = GameApp.launchGameApp("/engine/tetrisEngineConfiguration.xml");
        ge.start();
    }

}
