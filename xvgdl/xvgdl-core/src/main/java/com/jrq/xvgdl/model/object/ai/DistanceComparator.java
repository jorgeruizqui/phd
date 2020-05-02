package com.jrq.xvgdl.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.AllArgsConstructor;

/**
 * This comparator will compare the distance of two objects to a target game object
 */
@AllArgsConstructor
public class DistanceComparator implements java.util.Comparator<IGameObject> {

    private IGameObject gameObjectTarget;

    @Override
    public int compare(IGameObject anObject, IGameObject otherObject) {
        int distanceObject1 = distanceTo(anObject);
        int distanceObject2 = distanceTo(otherObject);

        return distanceObject1 - distanceObject2;
    }

    /**
     * @param object The object to check the distance from
     * @return Distance between the object and the target object
     */
    private int distanceTo(IGameObject object) {
        int distX = Math.abs(gameObjectTarget.getPosition().getX() - object.getPosition().getX());
        int distY = Math.abs(gameObjectTarget.getPosition().getY() - object.getPosition().getY());
        int distZ = Math.abs(gameObjectTarget.getPosition().getZ() - object.getPosition().getZ());
        return distX + distY + distZ;
    }
}
