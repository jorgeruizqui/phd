package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;

/**
 * Game Event Executor interface
 *
 * @author jrquinones
 */
public interface IGameEventExecutor {

    void executeEvent(IGameEvent event, GameContext context);

}
