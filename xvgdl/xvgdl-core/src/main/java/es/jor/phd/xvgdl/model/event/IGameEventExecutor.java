package es.jor.phd.xvgdl.model.event;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Game Event interface
 *
 * @author jrquinones
 *
 */
public interface IGameEventExecutor {

    /**
     *
     * @param event Game Event
     * @param context Game context
     */
    void executeEvent(IGameEvent event, GameContext context);

}
