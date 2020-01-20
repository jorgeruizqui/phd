package com.jrq.xvgdl.spaceinvaders;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.engine.GameEngine;

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

        GameEngine ge = XvgdlGameApp.launchGameApp("/engine/spaceInvadersEngineConfiguration.xml");
        ge.start();
    }

}
