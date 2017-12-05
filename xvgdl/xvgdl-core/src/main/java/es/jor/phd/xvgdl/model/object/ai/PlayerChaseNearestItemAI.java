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
	        int newX = 0;
	        if (player.getX() > shortestDistanceItem.getX()) {
	            newX = shortestDistanceItem.getX() + 1;
	        } else if (player.getX() < shortestDistanceItem.getX()) {
	            newX = shortestDistanceItem.getX() - 1;
	        } else {
	            newX = shortestDistanceItem.getX();
	        }

	        int newY = 0;
	        if (player.getY() > shortestDistanceItem.getY()) {
	            newY = shortestDistanceItem.getY() + 1;
	        } else if (player.getY() < shortestDistanceItem.getY()) {
	            newY = shortestDistanceItem.getY() - 1;
	        } else {
	            newY = shortestDistanceItem.getY();
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

}
