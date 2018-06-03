package es.jor.phd.xvgdl.spaceinvaders.model.ai;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.model.object.ai.IGameObjectAI;

/**
 * Basic Artificial Intelligence applies to an object chasing always the Player
 *
 * @author jrquinones
 *
 */
public class EnemyDiscAI implements IGameObjectAI {

    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject object) {
        object.moveTo(object.getX() + 1, object.getY(), object.getZ());
    }

}
