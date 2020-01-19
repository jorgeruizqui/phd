package com.jrq.xvgdl.tetris;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.engine.GameEngine;

/**
 * Launcher
 *
 * @author jrquinones
 */
public class LaunchTetris {

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        GameEngine ge = XvgdlGameApp.launchGameApp("/engine/tetrisEngineConfiguration.xml");
        ge.start();
    }

}