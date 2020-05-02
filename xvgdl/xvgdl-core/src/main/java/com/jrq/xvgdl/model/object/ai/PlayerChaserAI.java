package com.jrq.xvgdl.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;

/**
 * Basic Artificial Intelligence applies to an object chasing always the Player
 *
 * @author jrquinones
 */
public class PlayerChaserAI implements IGameObjectAI {

    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject object) {

        IGameObject player = gameContext.getObjectsMap().get(GameObjectType.PLAYER).get(0);
        int newX = object.getPosition().getX();
        boolean moved = false;
        if (player.getPosition().getX() > object.getPosition().getX()) {
            if (canMove(gameContext, object.getPosition().getX() + 1, object.getPosition().getY(), object.getPosition().getZ())) {
                newX = object.getPosition().getX() + 1;
                moved = true;
            }
        } else if (player.getPosition().getX() < object.getPosition().getX()) {
            if (canMove(gameContext, object.getPosition().getX() - 1, object.getPosition().getY(), object.getPosition().getZ())) {
                newX = object.getPosition().getX() - 1;
                moved = true;
            }
        }

        int newY = object.getPosition().getY();
        if (!moved) {
            if (player.getPosition().getY() > object.getPosition().getY()) {
                if (canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() + 1, object.getPosition().getZ())) {
                    newY = object.getPosition().getY() + 1;
                    moved = true;
                }
            } else if (player.getPosition().getY() < object.getPosition().getY()) {
                if (canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() - 1, object.getPosition().getZ())) {
                    newY = object.getPosition().getY() - 1;
                    moved = true;
                }
            }
        }

        // At least, try to move to postition different from the last one
        if (!moved) {
            if (canMove(gameContext, object.getPosition().getX() + 1, object.getPosition().getY(), object.getPosition().getZ())
              && object.getLastPosition().getX() != object.getPosition().getX() + 1) {
                newX = object.getPosition().getX() + 1;
            } else if (canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() + 1, object.getPosition().getZ())
                    && object.getLastPosition().getY() != object.getPosition().getY() + 1) {
                newY = object.getPosition().getY() + 1;
            } else if (canMove(gameContext, object.getPosition().getX() - 1, object.getPosition().getY(), object.getPosition().getZ())
                    && object.getLastPosition().getX() != object.getPosition().getX() - 1) {
                newX = object.getPosition().getX() - 1;
            } else if (canMove(gameContext, object.getPosition().getX(), object.getPosition().getY() - 1, object.getPosition().getZ())
                    && object.getLastPosition().getY() != object.getPosition().getY() - 1) {
                newY = object.getPosition().getY() - 1;
            }
        }
        object.moveTo(newX, newY, 0);

    }

    /**
     * Check if an object can move to a concrete square
     *
     * @param gc
     * @param x
     * @param y
     * @param z
     * @return
     */
    private boolean canMove(GameContext gc, int x, int y, int z) {
        IGameObject elementAt = gc.getObjectAt(x, y, z);
        return (elementAt == null || !GameObjectType.WALL.equals(elementAt.getObjectType()));
    }

}
