package com.jrq.xvgdl.clashroyale;

import com.jrq.xvgdl.app.GameApp;
import com.jrq.xvgdl.engine.GameEngine;

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
