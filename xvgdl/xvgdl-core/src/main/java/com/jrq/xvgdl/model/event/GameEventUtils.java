package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Game Event Utils
 *
 * @author jrquinones
 */
@Slf4j
public final class GameEventUtils {

    /**
     * Private constructor.
     */
    private GameEventUtils() {
    }

    /**
     * @param gameContext Game Context
     * @param event       Game Event
     */
    public static void processGameEvent(GameContext gameContext, IGameEvent event) {

        long lastEventExecutionTime = event.getTimeStamp();
        long eventTimer = event.getTimer();
        long currentTime = System.currentTimeMillis();
        boolean executeEvent = true;
        if (eventTimer > 0 && (currentTime - lastEventExecutionTime < eventTimer)) {
            executeEvent = false;
        }
        if (executeEvent) {
            log.debug("Executing game event: " + event.getClass().getName());
            event.executeEvent();
        }
    }
}
