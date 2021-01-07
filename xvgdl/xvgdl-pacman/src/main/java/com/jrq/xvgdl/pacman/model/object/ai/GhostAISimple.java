package com.jrq.xvgdl.pacman.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.map.IGameMap;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Basic Artificial Intelligence for ghosts:
 * - Try move to a next postition different from the last one
 * - Try to scape from Pacman if Pacman is powered up
 *
 * @author jrquinones
 */
public class GhostAISimple implements IGameObjectAI {

    private int newX, newY;
    private static Random random = new Random();

    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject object) {

        if (!object.getPosition().equals(object.getIntendedPosition())) {
            return;
        }

        IGameObject player = gameContext.getObjectsMap().get(GameObjectType.PLAYER).get(0);
        boolean isPacmanPoweredUp = gameContext.getCurrentGameState().equals("pacmanPowerUp");
        boolean moved;

        if (!isPacmanPoweredUp) {
            moved = moveRandomlyFollowingPath(gameContext, object, player);
        } else {
            moved = moveEscapingFromPlayer(gameContext, object, player);
        }

        if (!moved) {
            forceMoveSomewhereElse(gameContext, object);
        }
        object.moveTo(newX, newY, 0);
    }

    private void forceMoveSomewhereElse(GameContext gameContext, IGameObject object) {
        if (canMove(gameContext, object.getPosition().getX() + 1, object.getPosition().getY())
          && object.getLastPosition().getX() != object.getPosition().getX() + 1) {
            newX = object.getPosition().getX() + 1;
        } else if (canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() + 1)
                && object.getLastPosition().getY() != object.getPosition().getY() + 1) {
            newY = object.getPosition().getY() + 1;
        } else if (canMove(gameContext, object.getPosition().getX() - 1, object.getPosition().getY())
                && object.getLastPosition().getX() != object.getPosition().getX() - 1) {
            newX = object.getPosition().getX() - 1;
        } else if (canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() - 1)
                && object.getLastPosition().getY() != object.getPosition().getY() - 1) {
            newY = object.getPosition().getY() - 1;
        }
    }

    private boolean moveRandomlyFollowingPath(GameContext gameContext, IGameObject object, IGameObject player) {
        int lastX = object.getLastPosition().getX();
        int lastY = object.getLastPosition().getY();

        List<Pair<Integer, Integer>> listOfPossibleMoves = new ArrayList<>();

        if ((lastX != object.getPosition().getX() + 1)
                && canMove(gameContext, object.getPosition().getX() + 1, object.getPosition().getY())) {
            listOfPossibleMoves.add(new MutablePair<>(object.getPosition().getX() + 1, object.getPosition().getY()));
        }

        if ((lastX != object.getPosition().getX() - 1)
                && canMove(gameContext, object.getPosition().getX() - 1, object.getPosition().getY())) {
            listOfPossibleMoves.add(new MutablePair<>(object.getPosition().getX() - 1, object.getPosition().getY()));
        }

        if ((lastY != object.getPosition().getY() + 1)
                && canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() + 1)) {
            listOfPossibleMoves.add(new MutablePair<>(object.getPosition().getX(), object.getPosition().getY() + 1));
        }
        if ((lastY != object.getPosition().getY() - 1)
                && canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() - 1)) {
            listOfPossibleMoves.add(new MutablePair<>(object.getPosition().getX(), object.getPosition().getY() - 1));
        }

        // Maybe we can't move because of an non-exist way, so we've to go back
        if (listOfPossibleMoves.isEmpty()) {
            newX = lastX;
            newY = lastY;
        } else if (listOfPossibleMoves.size() == 1) {
            newX = listOfPossibleMoves.get(0).getLeft();
            newY = listOfPossibleMoves.get(0).getRight();
        } else {
            Pair<Integer, Integer> selected = selectRandomPairToMove(listOfPossibleMoves);
            newX = selected.getLeft();
            newY = selected.getRight();
        }
        return newX != object.getPosition().getX() || newY != object.getPosition().getY();
    }

    private Pair<Integer, Integer> selectRandomPairToMove(List<Pair<Integer, Integer>> listOfPossibleMoves) {
        double randomGenerated = random.nextDouble();
        double prob = 1.0 / (double) listOfPossibleMoves.size();
        double accProb = prob;
        int selectedIndex = 0;

        for (int i = 0; i < listOfPossibleMoves.size(); i++) {
            if (randomGenerated < accProb) {
                selectedIndex = i;
                break;
            }
            accProb += prob;
        }
        return listOfPossibleMoves.get(selectedIndex);
    }

    private boolean moveEscapingFromPlayer(GameContext gameContext, IGameObject object, IGameObject player) {
        newX = object.getPosition().getX();
        boolean moved = false;
        if (player.getPosition().getX() > object.getPosition().getX()) {
            if (canMove(gameContext, object.getPosition().getX() - 1, object.getPosition().getY())) {
                newX = object.getPosition().getX() - 1;
                moved = true;
            }
        } else if (player.getPosition().getX() < object.getPosition().getX()) {
            if (canMove(gameContext, object.getPosition().getX() + 1, object.getPosition().getY())) {
                newX = object.getPosition().getX() + 1;
                moved = true;
            }
        }

        newY = object.getPosition().getY();
        if (!moved) {
            if (player.getPosition().getY() > object.getPosition().getY()) {
                if (canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() - 1)) {
                    newY = object.getPosition().getY() - 1;
                    moved = true;
                }
            } else if (player.getPosition().getY() < object.getPosition().getY()) {
                if (canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() + 1)) {
                    newY = object.getPosition().getY() + 1;
                    moved = true;
                }
            }
        }
        return moved;
    }

    private boolean canMove(GameContext gc, int x, int y) {
        IGameObject elementAt = gc.getObjectAt(x, y, 0);
        return isPositionInMap(gc, x, y) &&
                (elementAt == null || !GameObjectType.WALL.equals(elementAt.getObjectType()));
    }

    private boolean isPositionInMap(GameContext gc, int x, int y) {
        IGameMap map = gc.getGameMap();
        return x >= 0 && x < map.getSizeX() &&
                y >= 0 && y < map.getSizeY();
    }
}
