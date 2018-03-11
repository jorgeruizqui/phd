package es.jor.phd.xvgdl.model.object.ai;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Basic Artificial Intelligence applies to an object chasing always the Player
 *
 * @author jrquinones
 *
 */
public class PlayerChaserAI implements IGameObjectAI {

    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject object) {

        IGameObject player = gameContext.getObjectsMap().get(GameObjectType.PLAYER).get(0);
        int newX = object.getX();
        boolean moved = false;
        if (player.getX() > object.getX()) {
            if (canMove(gameContext, object.getX() + 1, object.getY(), object.getZ())) {
                newX = object.getX() + 1;
                moved = true;
            }
        } else if (player.getX() < object.getX()) {
            if (canMove(gameContext, object.getX() - 1, object.getY(), object.getZ())) {
                newX = object.getX() - 1;
                moved = true;
            }
        }

        int newY = object.getY();
        if (!moved) {
            if (player.getY() > object.getY()) {
                if (canMove(gameContext, object.getX(), object.getY() + 1, object.getZ())) {
                    newY = object.getY() + 1;
                }
            } else if (player.getY() < object.getY()) {
                if (canMove(gameContext, object.getX(), object.getY() - 1, object.getZ())) {
                    newY = object.getY() - 1;
                }
            }
        }

        object.moveTo(newX, newY, 0);

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
