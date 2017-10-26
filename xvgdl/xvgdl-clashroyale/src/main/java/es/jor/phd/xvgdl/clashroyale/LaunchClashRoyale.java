package es.jor.phd.xvgdl.clashroyale;

import es.jor.phd.xvgdl.app.GameApp;
import es.jor.phd.xvgdl.engine.GameEngine;

/**
 * Launcher
 * @author jrquinones
 *
 */
public class LaunchClashRoyale {

    /**
     * Main class
     * @param args Arguments
     */
    public static void main(String[] args) {

        GameEngine ge = GameApp.launchGameApp("/engine/clashRoyaleEngineConfiguration.xml");
        ge.start();
    }

}
