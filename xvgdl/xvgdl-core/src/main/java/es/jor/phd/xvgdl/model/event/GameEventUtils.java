package es.jor.phd.xvgdl.model.event;

import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Event Utils
 *
 * @author jrquinones
 *
 */
public final class GameEventUtils {

    /** Private constructor. */
    private GameEventUtils() {
    }

    /**
     *
     * @param gameContext Game Context
     * @param event Game Event
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
            ELogger.debug(GameEventUtils.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                    "Executing game event: " + event.getClass().getName());
            event.executeEvent();
        }
    }
}
