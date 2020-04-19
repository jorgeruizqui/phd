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

    private GameEventUtils() {
    }

    public static boolean processGameEvent(GameContext gameContext, IGameEvent event) {

        log.debug("Proessing Game Event: " + event);
        long currentTime = System.currentTimeMillis();
        long lastEventExecutionTime = event.getTimeStamp() > 0 ? event.getTimeStamp() : currentTime;
        long eventTimer = event.getTimer();
        boolean executeEvent = true;

        if (eventTimer > 0 && ((currentTime - lastEventExecutionTime) < eventTimer)) {
            executeEvent = false;
        }

        if (executeEvent) {
            log.debug("Executing game event: " + event.getClass().getName());
            event.executeEvent(gameContext);
        }
        return executeEvent;
    }
}
