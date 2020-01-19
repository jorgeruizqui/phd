package com.jrq.xvgdl.breakout;

import com.jrq.xvgdl.app.GameApp;
import com.jrq.xvgdl.engine.GameEngine;

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
