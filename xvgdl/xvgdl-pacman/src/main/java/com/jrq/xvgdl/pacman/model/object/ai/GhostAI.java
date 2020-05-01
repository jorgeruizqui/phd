package com.jrq.xvgdl.pacman.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.map.IGameMap;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;

/**
 * Basic Artificial Intelligence for ghosts:
 * - Try to get Pacman if Pacman is not powered up
 * - Try to scape from Pacman if Pacman is powered up
 *
 * @author jrquinones
 */
public class GhostAI implements IGameObjectAI {

    private int newX, newY;

    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject object) {

        IGameObject player = gameContext.getObjectsMap().get(GameObjectType.PLAYER).get(0);
        boolean isPacmanPoweredUp = gameContext.getCurrentGameState().equals("pacmanPowerUp");
        boolean moved;

        if (!isPacmanPoweredUp) {
            moved = moveChasingPlayer(gameContext, object, player);
        } else {
            moved = moveEscapingFromPlayer(gameContext, object, player);
        }

        if (!moved) {
            forceMoveSomewhereElse(gameContext, object);
        }
        object.moveTo(newX, newY, 0);
    }

    private void forceMoveSomewhereElse(GameContext gameContext, IGameObject object) {
        if (canMove(gameContext, object.getX() + 1, object.getY())
          && object.getLastPosition().getX() != object.getX() + 1) {
            newX = object.getX() + 1;
        } else if (canMove(gameContext, object.getX(), object.getY() + 1)
                && object.getLastPosition().getY() != object.getY() + 1) {
            newY = object.getY() + 1;
        } else if (canMove(gameContext, object.getX() - 1, object.getY())
                && object.getLastPosition().getX() != object.getX() - 1) {
            newX = object.getX() - 1;
        } else if (canMove(gameContext, object.getX(), object.getY() - 1)
                && object.getLastPosition().getY() != object.getY() - 1) {
            newY = object.getY() - 1;
        }
    }

    private boolean moveChasingPlayer(GameContext gameContext, IGameObject object, IGameObject player) {
        newX = object.getX();
        boolean moved = false;
        if (player.getX() > object.getX()) {
            if (canMove(gameContext, object.getX() + 1, object.getY())) {
                newX = object.getX() + 1;
                moved = true;
            }
        } else if (player.getX() < object.getX()) {
            if (canMove(gameContext, object.getX() - 1, object.getY())) {
                newX = object.getX() - 1;
                moved = true;
            }
        }

        newY = object.getY();
        if (!moved) {
            if (player.getY() > object.getY()) {
                if (canMove(gameContext, object.getX(), object.getY() + 1)) {
                    newY = object.getY() + 1;
                    moved = true;
                }
            } else if (player.getY() < object.getY()) {
                if (canMove(gameContext, object.getX(), object.getY() - 1)) {
                    newY = object.getY() - 1;
                    moved = true;
                }
            }
        }
        return moved;
    }

    private boolean moveEscapingFromPlayer(GameContext gameContext, IGameObject object, IGameObject player) {
        newX = object.getX();
        boolean moved = false;
        if (player.getX() > object.getX()) {
            if (canMove(gameContext, object.getX() - 1, object.getY())) {
                newX = object.getX() - 1;
                moved = true;
            }
        } else if (player.getX() < object.getX()) {
            if (canMove(gameContext, object.getX() + 1, object.getY())) {
                newX = object.getX() + 1;
                moved = true;
            }
        }

        newY = object.getY();
        if (!moved) {
            if (player.getY() > object.getY()) {
                if (canMove(gameContext, object.getX(), object.getY() - 1)) {
                    newY = object.getY() - 1;
                    moved = true;
                }
            } else if (player.getY() < object.getY()) {
                if (canMove(gameContext, object.getX(), object.getY() + 1)) {
                    newY = object.getY() + 1;
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
