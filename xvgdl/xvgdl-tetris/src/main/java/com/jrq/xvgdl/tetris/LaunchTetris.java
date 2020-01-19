package com.jrq.xvgdl.tetris;

import com.jrq.xvgdl.app.GameApp;
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

        GameEngine ge = GameApp.launchGameApp("/engine/tetrisEngineConfiguration.xml");
        ge.start();
    }

}
