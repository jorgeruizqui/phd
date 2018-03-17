package es.jor.phd.xvgdl.app;

import es.indra.eplatform.util.IOUtils;
import es.jor.phd.xvgdl.engine.GameEngine;

/**
 * Launcher
 * @author jrquinones
 *
 */
public class LaunchXvgdlGameEngine {

    /**
     * Main class
     * @param args Arguments
     */
    public static void main(String[] args) {
        
        if (args.length < 1) {
            System.out.println( 
                    "Error starting Game Engine. Correct use: java -jar xvgdl-core <xvgdl-configuration-file>");
            System.exit(0);
        }

        if (IOUtils.getInputStream(args[0]) == null) {
            System.out.println( 
                    "Error reading configuration file: " + args[0]);
            System.exit(0);
        }

        GameEngine ge = GameApp.launchGameApp(args[0]);
        ge.start();
    }

}
