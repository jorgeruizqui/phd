package com.jrq.xvgdl.spaceinvaders.model.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;

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
