package com.jrq.xvgdl.app;

import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.exception.XvgdlException;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchXvgdlGameEngine {

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            log.error("Error starting Game Engine. Correct use: java -jar xvgdl-core <xvgdl-configuration-file>");
            System.exit(-1);
        }

        try {
            XvgdlGameApp.launchGameApp(args[0]).start();
        } catch (XvgdlException e) {
            log.error("Error running XVGDL game.", e);
            System.exit(-1);
        }
    }
}
