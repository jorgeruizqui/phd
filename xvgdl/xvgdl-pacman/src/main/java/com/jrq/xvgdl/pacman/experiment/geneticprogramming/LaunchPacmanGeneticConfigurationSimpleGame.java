package com.jrq.xvgdl.pacman.experiment.geneticprogramming;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/**
 * Launcher for the Pacman Generational Genetic Programming
 *
 * @author jrquinones
 */
@Slf4j
public class LaunchPacmanGeneticConfigurationSimpleGame {

    public static final String CONFIGURATION_FILE = "/context/geneticprogramming/pacmanXvgdlGeneticProgrammingRender.xml";

    public static void main(String[] args) {
        try {
            GameContext gameContext = new GameContext();
            gameContext.loadGameContext(CONFIGURATION_FILE);
            allocatePlayerRandomly(gameContext);
            GameEngine gameEngine = GameEngine.createGameEngine(gameContext, null);
            gameEngine.start();
        } catch (Exception e) {
            log.error("Exception running Pacman Generational Genetic Programming: " + e.getMessage(), e);
            System.exit(-1);
        }
    }

    private static void allocatePlayerRandomly(GameContext gameContext) {
        IGameObject player = gameContext.getGamePlayers().get(0);
        boolean located = false;
        while (!located) {
            int x = (new Random()).nextInt(gameContext.getGameMap().getSizeX());
            int y = (new Random()).nextInt(gameContext.getGameMap().getSizeY());

            IGameObject objectAt = gameContext.getObjectAt(x, y, 0);

            if (objectAt == null
                || (!GameObjectType.ENEMY.equals(objectAt.getObjectType())
                && !GameObjectType.WALL.equals(objectAt.getObjectType()))) {
                player.setInitialPosition(x, y, 0);
                player.setPosition(x, y, 0);
                located = true;
            }
        }
    }
}
