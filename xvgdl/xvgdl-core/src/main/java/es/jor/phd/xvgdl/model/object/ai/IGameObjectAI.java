package es.jor.phd.xvgdl.model.object.ai;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Game Object Artificial Intelligence Interface
 *
 * @author jrquinones
 *
 */
public interface IGameObjectAI {

    /**
     * Updates Object state according to the Game Context
     *
     * @param gameContext Game Context
     * @param object Object to apply AI
     */
    void applyAIonObject(GameContext gameContext, IGameObject object);
}
