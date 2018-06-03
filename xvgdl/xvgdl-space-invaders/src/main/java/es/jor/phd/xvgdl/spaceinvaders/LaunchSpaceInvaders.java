package es.jor.phd.xvgdl.spaceinvaders;

import es.jor.phd.xvgdl.app.GameApp;
import es.jor.phd.xvgdl.engine.GameEngine;

/**
 * Launcher
 * @author jrquinones
 *
 */
public class LaunchSpaceInvaders {

    /**
     * Main class
     * @param args Arguments
     */
    public static void main(String[] args) {

        GameEngine ge = GameApp.launchGameApp("/engine/spaceInvadersEngineConfiguration.xml");
        ge.start();
    }

}
