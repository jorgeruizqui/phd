package com.jrq.xvgdl.pacman.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.util.MinimumDistance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This comparator will compare the distance of two objects to a target game object
 * Also will check the distance of the nearest ghost to penalise the result.
 * The AI will try to move to the nearest item which has the longest distance to any
 * of the Ghosts
 */
@AllArgsConstructor
@Slf4j
public class PlayerNextMoveComparator {

    private static final int RADIUS_FOR_ENEMIES = 1;
    private GameContext gameContext;
    private int searchRadius = 3;

    public List<ItemMovementSolution> orderedSolutions() {
        long startTime = System.currentTimeMillis();
        log.debug("Starting orderedSolutionsAlgorithm");
        IGameObject player = getPlayer();
        List<IGameObject> items = getItemsInRadius(player);

        List<ItemMovementSolution> solutions = new ArrayList<>();
        for (IGameObject item : items) {
            List<String> solutionPath = MinimumDistance.calculateMinimumDistance(
                    getArrayRepresentationOfMap(),
                    player.getPosition(),
                    item.getPosition(),
                    searchRadius
            );
            ItemMovementSolution aSolution = new ItemMovementSolution();
            aSolution.setDistance(solutionPath.size() - 1);
            aSolution.setGameObjectToGo(item);
            aSolution.setPathFromList(solutionPath);
            List<IGameObject> enemies = getEnemiesInRadius(item);
            aSolution.setNumberOfEnemiesSurrounding(enemies.size());
            solutions.add(aSolution);
        }
        Collections.sort(solutions);
        log.debug("End orderedSolutionsAlgorithm in :" + (System.currentTimeMillis() - startTime) + " ms.");

        return solutions;
    }

    private List<IGameObject> getItemsInRadius(IGameObject player) {
        List<IGameObject> itemsInRadius = new ArrayList<>();

        if (gameContext.getObjectsMap().get(GameObjectType.ITEM) != null) {
            List<IGameObject> items = gameContext.getObjectsMap().get(GameObjectType.ITEM).stream().filter(
                    IGameObject::isLocatedAnyWhereInMap).collect(Collectors.toList());

            if (items.size() <= 1) {
                itemsInRadius.addAll(items);
                return itemsInRadius;
            }
            // We need at least two items to decide where to go
            while (itemsInRadius.size() < 1 ) {
                itemsInRadius = items.stream().filter(
                        item -> isInRadius(player, item, searchRadius)).collect(Collectors.toList());
                if (itemsInRadius.isEmpty()) {
                    searchRadius++;
                }
            }
        }
        return itemsInRadius;
    }

    private List<IGameObject> getEnemiesInRadius(IGameObject anObject) {
        List<IGameObject> enemiesInRadius = new ArrayList<>();

        if (gameContext.getObjectsMap().get(GameObjectType.ENEMY) != null) {
            List<IGameObject> items = gameContext.getObjectsMap().get(GameObjectType.ENEMY).stream().filter(
                    IGameObject::isLocatedAnyWhereInMap).collect(Collectors.toList());
            enemiesInRadius = items.stream().filter(
                        item -> isInRadius(anObject, item, RADIUS_FOR_ENEMIES)).collect(Collectors.toList());
        }
        return enemiesInRadius;
    }

    private IGameObject getPlayer() {
        return gameContext.getCurrentGamePlayer();
    }

    private boolean isInRadius(IGameObject gameObject1, IGameObject gameObject2, int radius) {
        return Math.abs(gameObject1.getX() - gameObject2.getPosition().getX()) <= radius &&
                Math.abs(gameObject1.getY() - gameObject2.getPosition().getY()) <= radius &&
                Math.abs(gameObject1.getZ() - gameObject2.getPosition().getZ()) <= radius;
    }

    private int[][] getArrayRepresentationOfMap() {
        // Check if the array is OK
        int [][] gameMapArray =  new int[gameContext.getGameMap().getSizeX()][gameContext.getGameMap().getSizeY()];
        for (int[] ints : gameMapArray) {
            Arrays.fill(ints, 0);
        }
        gameContext.getObjectsListByType(GameObjectType.WALL).stream().filter(
                IGameObject::isLocatedAnyWhereInMap).forEach(
                o -> {
                    gameMapArray[o.getPosition().getX()][o.getPosition().getY()] = Integer.MAX_VALUE;
                });

        return gameMapArray;
    }

    @Data
    protected class ItemMovementSolution implements Comparable<ItemMovementSolution> {
        int distance;
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        IGameObject gameObjectToGo;
        int numberOfEnemiesSurrounding;

        @Override
        public int compareTo(ItemMovementSolution anotherSolution) {
            return Integer.compare(
                    distance + (10 * numberOfEnemiesSurrounding),
                    anotherSolution.getDistance()  + (10 * anotherSolution.getNumberOfEnemiesSurrounding()));
        }

        public void setPathFromList(List<String> listOfPairs) {
            listOfPairs.forEach(pair -> {
                this.path.add(new MutablePair<>(
                        Integer.valueOf(pair.split(",")[0]),
                        Integer.valueOf(pair.split(",")[1])));
            });
        }
    }
}
