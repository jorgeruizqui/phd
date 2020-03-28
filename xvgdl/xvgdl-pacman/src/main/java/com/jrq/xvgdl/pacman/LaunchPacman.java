package com.jrq.xvgdl.pacman;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.exception.XvgdlException;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchPacman {

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        try {
            XvgdlGameApp.launchGameApp("/engine/pacmanEngineConfiguration.xml").start();
        } catch (XvgdlException e) {
            log.error("Exception running XVGDL game.", e);
            System.exit(-1);
        }
    }

}
