package com.jrq.xvgdl.pacman.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.map.GameMap;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.GamePlayer;
import com.jrq.xvgdl.model.object.IGameObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerNextMoveComparatorTest {

    private GameContext gameContext;
    private PlayerNextMoveComparator playerNextMoveComparator;

    @Before
    public void init() {
        gameContext = get4x5ExampleGameContext();
        playerNextMoveComparator = new PlayerNextMoveComparator(gameContext, 6);
    }

    @Test
    public void calculateMinimumDistanceJustOneStep() {
        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(0).y(1).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(1, solutionList.get(0).getDistance());
    }

    @Test
    public void calculateMinimumDistanceItemInMapLimits() {
        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(0).y(0).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);
        gameContext.getCurrentGamePlayer().setPosition(2, 2, 0);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(4, solutionList.get(0).getDistance());
    }

    @Test
    public void calculateMinimumDistanceItemInMapLimitsMax() {
        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(3).y(4).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);
        gameContext.getCurrentGamePlayer().setPosition(2, 2, 0);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(5, solutionList.get(0).getDistance());
    }

    @Test
    public void calculateMinimumDistanceNoWallsInTheMiddleWithGameContext() {
        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(3).y(0).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(3, solutionList.get(0).getDistance());
    }


    @Test
    public void calculateMinimumDistanceWallsEasyWithGameContext() {
        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(2).y(2).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(4, solutionList.get(0).getDistance());
    }

    @Test
    public void calculateMinimumDistanceWallsHardWithGameContext() {
        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(3).y(3).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(8, solutionList.get(0).getDistance());
    }

    @Test
    public void calculateMinimumDistanceWallsHardWithItemIncludedInRadiusRestriction() {
        gameContext.setGameMap(get25x25Map());

        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(3).y(3).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(8, solutionList.get(0).getDistance());
    }
/*
    @Test
    public void calculateMinimumDistanceWallsHardWithItemExcludedInRadiusRestriction() {
        gameContext.setGameMap(get25x25Map());

        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(8).y(8).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);

        // Radius will be greater and greater until an item is found
        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(16, solutionList.get(0).getDistance());
    }*/

    @Test
    public void calculateMinimumDistanceWithNoItems() {
        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertTrue(solutionList.isEmpty());
    }

    @Test
    public void calculateMinimumDistanceWallsWithUserInTheMiddleOfTheMap() {
        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(0).y(0).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);
        gameContext.getCurrentGamePlayer().setPosition(2, 2, 2);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(4, solutionList.get(0).getDistance());
    }

    @Test
    public void calculateMinimumDistanceWallsWithUserInTheMiddleOfABigMap() {
        gameContext.setGameMap(get25x25Map());

        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(18).y(16).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);
        gameContext.getCurrentGamePlayer().setPosition(16, 12, 0);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(6, solutionList.get(0).getDistance());
    }
/*
    @Test
    public void calculateMinimumDistanceWallsWithUserInTheMiddleOfABigMapExceedingRadius() {
        gameContext.setGameMap(get25x25Map());

        IGameObject item = new GameObject();
        item.setPosition(Position.builder().x(24).y(16).z(0).build());
        item.setObjectType(GameObjectType.ITEM);
        gameContext.addObject(item);
        gameContext.getCurrentGamePlayer().setPosition(12, 12, 0);

        List<PlayerNextMoveComparator.ItemMovementSolution> solutionList =
                playerNextMoveComparator.orderedSolutions();

        assertEquals(16, solutionList.get(0).getDistance());
    }*/

    /**
     *
     * @return a matrix representing this map:
     * O O O X O
     * O X O O O
     * O O O X O
     * O X X O O
     *
     * Where the X are walls and O are empty positions
     */
    private GameContext get4x5ExampleGameContext() {
        GameMap gameMap = get4x5Map();

        GameContext gameContext = new GameContext();
        gameContext.setGameMap(gameMap);

        IGameObject wall1 = new GameObject();
        wall1.setObjectType(GameObjectType.WALL);
        wall1.setPosition(Position.builder().x(0).y(3).z(0).build());
        gameContext.addObject(wall1);

        IGameObject wall2 = new GameObject();
        wall2.setObjectType(GameObjectType.WALL);
        wall2.setPosition(Position.builder().x(1).y(1).z(0).build());
        gameContext.addObject(wall2);

        IGameObject wall3 = new GameObject();
        wall3.setObjectType(GameObjectType.WALL);
        wall3.setPosition(Position.builder().x(2).y(3).z(0).build());
        gameContext.addObject(wall3);

        IGameObject wall4 = new GameObject();
        wall4.setObjectType(GameObjectType.WALL);
        wall4.setPosition(Position.builder().x(3).y(1).z(0).build());
        gameContext.addObject(wall4);

        IGameObject wall5 = new GameObject();
        wall5.setObjectType(GameObjectType.WALL);
        wall5.setPosition(Position.builder().x(3).y(2).z(0).build());
        gameContext.addObject(wall5);

        GamePlayer player = new GamePlayer();
        player.setPosition(0, 0, 0);
        player.setObjectType(GameObjectType.PLAYER);
        gameContext.addObject(player);

        return gameContext;
    }

    private GameMap get4x5Map() {
        GameMap gameMap = new GameMap();
        gameMap.setSizeX(4);
        gameMap.setSizeY(5);
        gameMap.setSizeZ(0);
        return gameMap;
    }

    private GameMap get25x25Map() {
        GameMap gameMap = new GameMap();
        gameMap.setSizeX(25);
        gameMap.setSizeY(25);
        gameMap.setSizeZ(0);
        return gameMap;
    }
}