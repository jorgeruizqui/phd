package com.jrq.xvgdl.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;

import java.util.List;

/**
 * Basic Artificial Intelligence applies to Player, going to the nearest item
 *
 * @author jrquinones
 */
public class PlayerChaseNearestItemAI implements IGameObjectAI {

    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject player) {

        List<IGameObject> items = gameContext.getObjectsMap().get(GameObjectType.ITEM);

        items.sort(new DistanceComparator(player));

        if (!items.isEmpty()) {
            IGameObject shortestDistanceItem = items.get(0);
            boolean moved = false;
            int newX = player.getX();
            if (player.getX() < shortestDistanceItem.getX()) {
                if (canMove(gameContext, player.getX() + 1, player.getY(), player.getZ())) {
                    newX = player.getX() + 1;
                    moved = true;
                }
            } else if (player.getX() > shortestDistanceItem.getX()) {
                if (canMove(gameContext, player.getX() - 1, player.getY(), player.getZ())) {
                    newX = player.getX() - 1;
                    moved = true;
                }
            }

            int newY = player.getY();
            if (!moved) {
                if (player.getY() < shortestDistanceItem.getY()) {
                    if (canMove(gameContext, player.getX(), player.getY() + 1, player.getZ())) {
                        newY = player.getY() + 1;
                    }
                } else if (player.getY() > shortestDistanceItem.getY()) {
                    if (canMove(gameContext, player.getX(), player.getY() - 1, player.getZ())) {
                        newY = player.getY() - 1;
                    }
                }
            }

            player.moveTo(newX, newY, 0);
        }
    }

    /**
     * @param player Player
     * @param item   Item
     * @return Distance between two game objects
     */
    private int distanceTo(IGameObject player, IGameObject item) {
        int distX = Math.abs(player.getX() - item.getX());
        int distY = Math.abs(player.getY() - item.getY());
        int distZ = Math.abs(player.getZ() - item.getZ());
        return distX + distY + distZ;
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
