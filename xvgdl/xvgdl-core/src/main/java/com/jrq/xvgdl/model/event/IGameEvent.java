package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameEventDefinition;

/**
 * Game Event interface
 *
 * @author jrquinones
 */
public interface IGameEvent {

    /**
     * @return the event type
     */
    GameEventType getEventType();

    /**
     * @return the event time in milliseconds
     */
    Long getTimeStamp();

    /**
     * Launcher Timer of the event.
     *
     * @return the timer in milliseconds
     */
    Long getTimer();

    /**
     * Executes the event.
     */
    void executeEvent(GameContext gameContext);

    /**
     * @return the game event definition.
     * May be used by executors getting event properties
     * That provides the possibility of configuring multiple
     * properties that every executor may treat.
     */
    GameEventDefinition getGameEventDefinition();

    /**
     * Flag indicating if is necessary to remove the event after processed.
     *
     * @return
     */
    Boolean isConsumable();

    Double getValue();

    void setEventType(GameEventType gameEventType);

    void setTimeStamp(Long timeStamp);
}
