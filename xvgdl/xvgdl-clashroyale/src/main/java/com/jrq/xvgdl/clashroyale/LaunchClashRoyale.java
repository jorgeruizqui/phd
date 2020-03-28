package com.jrq.xvgdl.clashroyale;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.exception.XvgdlException;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher
 * @author jrquinones
 *
 */
@Slf4j
public class LaunchClashRoyale {

    /**
     * Main class
     * @param args Arguments
     */
    public static void main(String[] args) {

        try {
            XvgdlGameApp.launchGameApp("/engine/clashRoyaleEngineConfiguration.xml").start();
        } catch (XvgdlException e) {
            log.error("Error running XVGDL game.", e);
            System.exit(-1);
        }
    }

}
