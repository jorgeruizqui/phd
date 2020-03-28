package com.jrq.xvgdl.breakout;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.exception.XvgdlException;
import lombok.extern.slf4j.Slf4j;

/**
 * XVGDL Engine Launcher for Breakout XVGDL game
 * @author jrquinones
 *
 */
@Slf4j
public class LaunchBreakout {

    public static void main(String[] args) {
        try {
            XvgdlGameApp.launchGameApp("/context/breakoutXvgdl.xml").start();
        } catch (XvgdlException e) {
            log.error("Error running XVGDL game.", e);
            System.exit(-1);
        }
    }

}
