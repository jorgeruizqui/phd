package com.jrq.xvgdl.pacman.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.util.MinimumDistance;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * This comparator will compare the distance of two objects to a target game object Also will check the distance of the
 * nearest ghost to penalise the result. The AI will try to move to the nearest item which has the longest distance to
 * any of the Ghosts
 */
@AllArgsConstructor
@Slf4j
public class PlayerNextMoveComparatorFixedRadius {

    private static final int RADIUS_FOR_ENEMIES = 1;
    private static final int FILTER_BY_ABS_DISTANCE = 3;
    private GameContext gameContext;
    private int searchRadius = 3;

    public List<ItemMovementSolution> orderedSolutions() {
        long startTime = System.currentTimeMillis();
        log.debug("Starting orderedSolutionsAlgorithm");
        IGameObject player = getPlayer();
        int maxRadius = gameContext.getGameMap().getSizeX() > gameContext.getGameMap().getSizeY() ?
            gameContext.getGameMap().getSizeX() : gameContext.getGameMap().getSizeY();
        List<IGameObject> items = getItemsInRadius(player, maxRadius);

        List<ItemMovementSolution> solutions = new ArrayList<>();
        if (items.size() == 0) {
            log.debug("[PACMAN - AI] : Basic solution approach");
            solutions = createBasicSolution(gameContext, player);
        } else {
            log.debug("[PACMAN - AI] : Trying to calculate minimum distance to a total of {} items", items.size());
            // Filtering nearer items to avoid long searchs
            List<IGameObject> filteredItems = filterItemsByAbsDistance(player, items);
            for (IGameObject item : filteredItems) {
                List<String> solutionPath = MinimumDistance.calculateMinimumDistanceFixedRadius(
                    getArrayRepresentationOfMap(),
                    player.getPosition(),
                    item.getPosition(),
                    searchRadius
                );
                if (!solutionPath.isEmpty()) {
                    ItemMovementSolution aSolution = new ItemMovementSolution();
                    aSolution.setDistance(solutionPath.size() - 1);
                    aSolution.setGameObjectToGo(item);
                    aSolution.setPathFromList(solutionPath);

                    aSolution.setNumberOfEnemiesSurrounding(getEnemiesInRadius(item).size());
                    aSolution.setEnemiesInPath(getEnemiesInPath(aSolution.getPath()));

                    solutions.add(aSolution);
                }
            }
        }

        if (solutions.isEmpty()) {
            solutions = createBasicSolution(gameContext, player);
        }

        Collections.sort(solutions);
        log.debug("End orderedSolutionsAlgorithm in :" + (System.currentTimeMillis() - startTime) + " ms.");

        return solutions;
    }

    private List<IGameObject> filterItemsByAbsDistance(IGameObject player, List<IGameObject> items) {
        List<IGameObject> rto = new ArrayList<>();
        int absDistanceFilterModifier = 0;

        while (rto.isEmpty()) {
            int finalAbsDistanceFilterModifier = absDistanceFilterModifier;
            items.stream().forEach(item -> {
                int absDistance = Math.abs(player.getPosition().getX() - item.getPosition().getX())
                    + Math.abs(player.getPosition().getY() - item.getPosition().getY());
                if (absDistance <= FILTER_BY_ABS_DISTANCE + finalAbsDistanceFilterModifier) {
                    rto.add(item);
                }
            });
            absDistanceFilterModifier++;
        }
        return rto;
    }

    private List<ItemMovementSolution> createBasicSolution(GameContext gameContext, IGameObject player) {
        List<ItemMovementSolution> solutions = new ArrayList<>();
        if (gameContext.getObjectsMap().get(GameObjectType.ITEM) != null) {
            List<IGameObject> items = gameContext.getObjectsMap().get(GameObjectType.ITEM).stream().filter(
                IGameObject::isLocatedAnyWhereInMap).collect(Collectors.toList());

            items.stream().forEach(item -> {
                    // Base on abs Distance:
                    ItemMovementSolution aSolution = new ItemMovementSolution();
                    aSolution.setDistance(
                        Math.abs(player.getPosition().getX() - item.getPosition().getX())
                        + Math.abs(player.getPosition().getY() - item.getPosition().getY())
                    );
                    aSolution.setGameObjectToGo(item);

                    aSolution.setPathFromList(getBasicMove(gameContext, player, item));

                    aSolution.setNumberOfEnemiesSurrounding(getEnemiesInRadius(item).size());
                    aSolution.setEnemiesInPath(getEnemiesInPath(aSolution.getPath()));

                    solutions.add(aSolution);
                }

            );
        }
        return solutions;
    }

    private List<String> getBasicMove(GameContext gameContext, IGameObject player, IGameObject item) {
        int newX = player.getPosition().getX();
        int newY = player.getPosition().getY();

        boolean moved = false;
        if (player.getPosition().getX() != item.getPosition().getX()) {
            if (player.getPosition().getX() > item.getPosition().getX()
            && (gameContext.getObjectAt(player.getPosition().getX() - 1, player.getPosition().getY(), 0) == null
            || !GameObjectType.WALL.equals(gameContext.getObjectAt(player.getPosition().getX() - 1, player.getPosition().getY(), 0).getObjectType()))) {
                newX--;
                moved = true;
            } else if (
                player.getPosition().getX() < item.getPosition().getX() &&
                    (gameContext.getObjectAt(player.getPosition().getX() + 1, player.getPosition().getY(), 0) == null
                || !GameObjectType.WALL.equals(gameContext.getObjectAt(player.getPosition().getX() + 1, player.getPosition().getY(), 0).getObjectType()))) {
                newX++;
                moved = true;
            }
        }

        if (!moved) {
            if (player.getPosition().getY() != item.getPosition().getY()) {
                if (player.getPosition().getY() > item.getPosition().getY()
                && (gameContext.getObjectAt(player.getPosition().getX(), player.getPosition().getY() - 1, 0) == null
                || !GameObjectType.WALL.equals(gameContext.getObjectAt(player.getPosition().getX(), player.getPosition().getY() -1, 0).getObjectType()))) {
                    newY--;
                    moved = true;
                } else if (
                    player.getPosition().getY() > item.getPosition().getY() &&
                        (gameContext.getObjectAt(player.getPosition().getX(), player.getPosition().getY() + 1, 0) == null
                    || !GameObjectType.WALL.equals(gameContext.getObjectAt(player.getPosition().getX(), player.getPosition().getY() + 1, 0).getObjectType()))) {
                    newY++;
                    moved = true;
                }
            }
        }

        // Try to move somewhere different from last postion.
        if (!moved) {
            if (player.getPosition().getX() >= player.getLastPosition().getX() &&
                (gameContext.getObjectAt(player.getPosition().getX() + 1, player.getPosition().getY(), 0) == null
                    || !GameObjectType.WALL.equals(gameContext.getObjectAt(player.getPosition().getX() + 1, player.getPosition().getY(), 0).getObjectType()))) {
                newX++;
                moved = true;
            } else if (player.getPosition().getX() <= player.getLastPosition().getX() &&
                (gameContext.getObjectAt(player.getPosition().getX() - 1, player.getPosition().getY(), 0) == null
                    || !GameObjectType.WALL.equals(gameContext.getObjectAt(player.getPosition().getX() - 1, player.getPosition().getY(), 0).getObjectType()))) {
                newX--;
                moved = true;
            }
        }

        if (!moved) {
            if (player.getPosition().getY() >= player.getLastPosition().getY() &&
                (gameContext.getObjectAt(player.getPosition().getX(), player.getPosition().getY() + 1, 0) == null
                    || !GameObjectType.WALL.equals(gameContext.getObjectAt(player.getPosition().getX(), player.getPosition().getY() + 1, 0).getObjectType()))) {
                newY++;
                moved = true;
            } else if (player.getPosition().getY() <= player.getLastPosition().getY() &&
                (gameContext.getObjectAt(player.getPosition().getX(), player.getPosition().getY() - 1, 0) == null
                    || !GameObjectType.WALL.equals(gameContext.getObjectAt(player.getPosition().getX(), player.getPosition().getY() - 1, 0).getObjectType()))) {
                newY--;
                moved = true;
            }
        }

        if (!moved) {
            log.error("Couldn't move pacman in any way");
        }

        return List.of(player.getPosition().getX() + "," + player.getPosition().getY(), newX + "," + newY);
    }

    private boolean getEnemiesInPath(List<Pair<Integer, Integer>> path) {
        AtomicBoolean thereAreEnemiesInPath = new AtomicBoolean(false);
        path.forEach(pair -> {
            if (gameContext.getObjectAt(pair.getLeft(), pair.getRight(), 0) != null
                && gameContext.getObjectAt(pair.getLeft(), pair.getRight(), 0).getObjectType()
                .equals(GameObjectType.ENEMY)) {
                thereAreEnemiesInPath.set(true);
            }
        });
        return thereAreEnemiesInPath.get();
    }

    private List<IGameObject> getItemsInRadius(IGameObject player, int maxRadius) {
        List<IGameObject> itemsInRadius = new ArrayList<>();

        if (gameContext.getObjectsMap().get(GameObjectType.ITEM) != null) {
            List<IGameObject> items = gameContext.getObjectsMap().get(GameObjectType.ITEM).stream().filter(
                IGameObject::isLocatedAnyWhereInMap).collect(Collectors.toList());

            //            if (items.size() <= 1) {
            //                itemsInRadius.addAll(items);
            //                searchRadius = maxRadius;
            //                return itemsInRadius;
            //            }

            //            while (itemsInRadius.size() < 1 ) {
            itemsInRadius = items.stream().filter(
                item -> isInRadius(player, item, searchRadius)).collect(Collectors.toList());
            //                if (itemsInRadius.isEmpty()) {
            //                    searchRadius++;
            //                }
            //            }
        }
        log.debug(
            "Trying to find items in radius, we have for radius [" + searchRadius + "] a total of " + itemsInRadius
                .size() + " items.");
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
        return Math.abs(gameObject1.getPosition().getX() - gameObject2.getPosition().getX()) <= radius &&
            Math.abs(gameObject1.getPosition().getY() - gameObject2.getPosition().getY()) <= radius &&
            Math.abs(gameObject1.getPosition().getZ() - gameObject2.getPosition().getZ()) <= radius;
    }

    private int[][] getArrayRepresentationOfMap() {
        // Check if the array is OK
        int[][] gameMapArray = new int[gameContext.getGameMap().getSizeX()][gameContext.getGameMap().getSizeY()];
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
        boolean enemiesInPath;

        @Override
        public int compareTo(ItemMovementSolution anotherSolution) {
            return Integer.compare(
                this.distance + this.getEnemiesModifier(),
                anotherSolution.getDistance() + anotherSolution.getEnemiesModifier());
        }

        public void setPathFromList(List<String> listOfPairs) {
            listOfPairs.forEach(pair -> {
                this.path.add(new MutablePair<>(
                    Integer.valueOf(pair.split(",")[0]),
                    Integer.valueOf(pair.split(",")[1])));
            });
        }

        private int getEnemiesModifier() {
            boolean pacmanPowerUp = gameContext.getCurrentGameState().equalsIgnoreCase("pacmanPowerUp");
            int enemiesInPathPenalty = enemiesInPath ? 100 : 0;

            int total = enemiesInPathPenalty + (10 * getNumberOfEnemiesSurrounding());
            return pacmanPowerUp ? 0 : total;
        }
    }
}
