package com.jrq.xvgdl.pacman;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.engine.GameEngine;

/**
 * Launcher
 *
 * @author jrquinones
 */
public class LaunchPacman {

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        GameEngine ge = XvgdlGameApp.launchGameApp("/engine/pacmanEngineConfiguration.xml");
        ge.start();
    }

}
