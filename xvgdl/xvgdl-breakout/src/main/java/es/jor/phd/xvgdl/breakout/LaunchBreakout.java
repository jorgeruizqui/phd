package es.jor.phd.xvgdl.breakout;

import es.jor.phd.xvgdl.app.GameApp;
import es.jor.phd.xvgdl.engine.GameEngine;

/**
 * Launcher
 * @author jrquinones
 *
 */
public class LaunchBreakout {

    /**
     * Main class
     * @param args Arguments
     */
    public static void main(String[] args) {

        GameEngine ge = GameApp.launchGameApp("/engine/breakoutEngineConfiguration.xml");
        ge.start();
    }

}
