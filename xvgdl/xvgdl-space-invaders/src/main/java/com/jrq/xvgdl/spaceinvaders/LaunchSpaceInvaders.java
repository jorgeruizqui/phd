package com.jrq.xvgdl.spaceinvaders;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.exception.XvgdlException;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher
 * @author jrquinones
 *
 */
@Slf4j
public class LaunchSpaceInvaders {

    /**
     * Main class
     * @param args Arguments
     */
    public static void main(String[] args) {

        try {
            String configurationFile = "/context/spaceInvadersXvgdl.xml";
            if (args.length > 0) {
                configurationFile = args[0];
            }
            XvgdlGameApp.launchGameApp(configurationFile).start();
        } catch (XvgdlException e) {
            log.error("Error running XVGDL game.", e);
            System.exit(-1);
        }
    }

}
