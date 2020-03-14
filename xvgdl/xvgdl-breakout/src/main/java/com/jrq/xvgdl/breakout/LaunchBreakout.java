package com.jrq.xvgdl.breakout;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.engine.GameEngine;

/**
 * XVGDL Engine Launcher for Breakout XVGDL game
 * @author jrquinones
 *
 */
public class LaunchBreakout {

    public static void main(String[] args) {
        XvgdlGameApp.launchGameApp("/context/breakoutXvgdl.xml").start();
    }

}
