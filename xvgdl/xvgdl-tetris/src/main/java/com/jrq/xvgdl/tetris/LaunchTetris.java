package com.jrq.xvgdl.tetris;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.exception.XvgdlException;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchTetris {

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        try {
            XvgdlGameApp.launchGameApp("/engine/tetrisEngineConfiguration.xml").start();
        } catch (XvgdlException e) {
            log.error("Error running XVGDL game.", e);
            System.exit(-1);
        }
    }

}
