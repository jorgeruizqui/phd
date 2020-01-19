package com.jrq.xvgdl.model.object.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;

/**
 * Game Object Artificial Intelligence Interface
 *
 * @author jrquinones
 */
public interface IGameObjectAI {

    /**
     * Updates Object state according to the Game Context
     *
     * @param gameContext Game Context
     * @param object      Object to apply AI
     */
    void applyAIonObject(GameContext gameContext, IGameObject object);
}
