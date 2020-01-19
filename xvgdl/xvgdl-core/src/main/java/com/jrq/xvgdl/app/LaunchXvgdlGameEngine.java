package com.jrq.xvgdl.app;

import com.jrq.xvgdl.engine.GameEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Launcher
 *
 * @author jrquinones
 */
public class LaunchXvgdlGameEngine {

    /**
     * Main class
     *
     * @param args Arguments
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println(
                    "Error starting Game Engine. Correct use: java -jar xvgdl-core <xvgdl-configuration-file>");
            System.exit(-1);
        }

        try (FileInputStream targetStream = new FileInputStream(new File(args[0]))) {
            GameEngine ge = GameApp.launchGameApp(args[0]);
            ge.start();
        } catch (IOException e) {
            System.out.println(
                    "Error reading configuration file: " + args[0]);
            System.exit(-1);
        }
    }

}
