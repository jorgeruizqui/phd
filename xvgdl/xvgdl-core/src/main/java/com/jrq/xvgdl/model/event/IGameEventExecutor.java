package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;

/**
 * Game Event interface
 *
 * @author jrquinones
 */
public interface IGameEventExecutor {

    /**
     * @param event   Game Event
     * @param context Game context
     */
    void executeEvent(IGameEvent event, GameContext context);

}
