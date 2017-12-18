package es.jor.phd.xvgdl.model.object.ai;

import java.util.List;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Basic Artificial Intelligence applies to Player, going to the nearest item
 *
 * @author jrquinones
 *
 */
public class PlayerChaseNearestItemAI implements IGameObjectAI {

    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject player) {

        List<IGameObject> items = gameContext.getObjectsMap().get(GameObjectType.ITEM);
        int shortestDinstance = Integer.MAX_VALUE;
        IGameObject shortestDistanceItem = null;

        for (IGameObject item : items) {
            int distance = distanceTo(player, item);
            if (distance < shortestDinstance) {
                shortestDistanceItem = item;
                shortestDinstance = distance;
            }
        }

        if (shortestDistanceItem != null) {
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
     *
     * @param player Player
     * @param item Item
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
