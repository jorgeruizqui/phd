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
        int newX = 0;
        if (player.getX() > object.getX()) {
            newX = object.getX() + 1;
        } else if (player.getX() < object.getX()) {
            newX = object.getX() - 1;
        } else {
            newX = object.getX();
        }

        int newY = 0;
        if (player.getY() > object.getY()) {
            newY = object.getY() + 1;
        } else if (player.getY() < object.getY()) {
            newY = object.getY() - 1;
        } else {
            newY = object.getY();
        }

        object.moveTo(newX, newY, 0);

    }

}
