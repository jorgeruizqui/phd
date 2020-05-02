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
            int newX = player.getPosition().getX();
            if (player.getPosition().getX() < shortestDistanceItem.getPosition().getX()) {
                if (canMove(gameContext, player.getPosition().getX() + 1, player.getPosition().getY(), player.getPosition().getZ())) {
                    newX = player.getPosition().getX() + 1;
                    moved = true;
                }
            } else if (player.getPosition().getX() > shortestDistanceItem.getPosition().getX()) {
                if (canMove(gameContext, player.getPosition().getX() - 1, player.getPosition().getY(), player.getPosition().getZ())) {
                    newX = player.getPosition().getX() - 1;
                    moved = true;
                }
            }

            int newY = player.getPosition().getY();
            if (!moved) {
                if (player.getPosition().getY() < shortestDistanceItem.getPosition().getY()) {
                    if (canMove(gameContext, player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ())) {
                        newY = player.getPosition().getY() + 1;
                    }
                } else if (player.getPosition().getY() > shortestDistanceItem.getPosition().getY()) {
                    if (canMove(gameContext, player.getPosition().getX(), player.getPosition().getY() - 1, player.getPosition().getZ())) {
                        newY = player.getPosition().getY() - 1;
                    }
                }
            }

            player.moveTo(newX, newY, 0);
        }
    }

    /**
     * Check if an object can move to an specific position, not occupied by a WALL
     */
    private boolean canMove(GameContext gc, int x, int y, int z) {
        IGameObject elementAt = gc.getObjectAt(x, y, z);
        return (elementAt == null || !GameObjectType.WALL.equals(elementAt.getObjectType()));
    }

}
