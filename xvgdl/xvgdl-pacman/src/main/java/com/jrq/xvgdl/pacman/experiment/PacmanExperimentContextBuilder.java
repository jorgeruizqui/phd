package com.jrq.xvgdl.pacman.experiment;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameEndConditionDefinition;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import com.jrq.xvgdl.model.endcondition.TimeoutGameEndCondition;
import com.jrq.xvgdl.model.map.GameMap;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.GamePlayer;
import com.jrq.xvgdl.pacman.model.object.ai.GhostAISimple;
import com.jrq.xvgdl.pacman.model.object.ai.PacmanAI;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
@Data
@Builder
public class PacmanExperimentContextBuilder {

    public static final String GHOST = "ghost";
    public static final String PACMAN = "pacman";
    public static final String SMALL_DOT = "smallDot";
    public static final String BIG_DOT = "bigDot";

    private int percentageOfDots;
    private int numberOfLives;
    private int numberOfGhosts;
    private long timeout;
    private int mapSizeX, mapSizeY;
    private int numberOfBigDots;

    private static Random random = new Random();

    public GameContext buildContext() {
        GameContext gameContext = new GameContext();

        setMapProperties(gameContext);
        generateBigDots(gameContext);
        generateSmallDots(gameContext);
        generatePlayer(gameContext);
        generateEnemies(gameContext);
        setEndContidions(gameContext);

        return gameContext;
    }

    private void setEndContidions(GameContext gameContext) {
        IGameEndCondition timeout = new TimeoutGameEndCondition();
        GameEndConditionDefinition definition = new GameEndConditionDefinition();
        definition.setValue(String.valueOf(this.timeout));
        definition.setWinningCondition(false);
        timeout.setGameEndConditionDefinition(definition);
        gameContext.addEndCondition(timeout);
        gameContext.setTimeout(this.timeout);
    }

    private void setMapProperties(GameContext gameContext) {
        GameMap gameMap = new GameMap();
        gameMap.setSizeX(this.mapSizeX);
        gameMap.setSizeY(this.mapSizeY);
        gameMap.setSizeZ(0);
        gameMap.setMapGenerator(new PacmanExperimentGameMapGenerator());
        gameContext.setGameMap(gameMap);
    }

    private void generateSmallDots(GameContext gameContext) {
        int freeSpaces = 1 + numberOfGhosts + numberOfBigDots;
        int numberOfDots = ((this.mapSizeX * this.mapSizeY * (this.percentageOfDots)) / 100) - freeSpaces;
        IntStream.range(0, numberOfDots).forEach(d -> {
            GameObject dot = new GameObject();
            dot.setName(SMALL_DOT);
            dot.setObjectType(GameObjectType.ITEM);
            assignObjectInstance(dot);
            gameContext.addObject(dot);
        });
    }

    private void generateBigDots(GameContext gameContext) {
        IntStream.range(0, this.numberOfBigDots).forEach(d -> {
            GameObject bigDot = new GameObject();
            bigDot.setName(BIG_DOT);
            bigDot.setObjectType(GameObjectType.ITEM);
            assignObjectInstance(bigDot);
            gameContext.addObject(bigDot);
        });
    }

    private void generateEnemies(GameContext gameContext) {

        IntStream.range(0, this.numberOfGhosts).forEach(o -> {
            GameObject enemy = new GameObject();
            enemy.setName(GHOST);
            enemy.setObjectType(GameObjectType.ENEMY);
            enemy.setSpeedFactor(1.0d);
            enemy.setInitialSpeedFactor(1.0d);
            enemy.setAi(new GhostAISimple());
            assignObjectInstance(enemy);
            gameContext.addObject(enemy);
        });
    }

    private void generatePlayer(GameContext gameContext) {
        GamePlayer player = new GamePlayer();
        player.setAi(new PacmanAI());
        player.setObjectType(GameObjectType.PLAYER);
        player.setSpeedFactor(1.0d);
        player.setInitialSpeedFactor(1.0d);
        player.setLives(this.numberOfLives);
        player.setInitialLives(this.numberOfLives);
        player.setScore(0d);
        player.setName(PACMAN);
        player.setCurrentPlayer(true);
        assignObjectInstance(player);
        gameContext.addObject(player);
    }

    private void assignObjectInstance(GameObject gameObject) {
        gameObject.setInstance(random.nextInt(Integer.MAX_VALUE));
    }
}
