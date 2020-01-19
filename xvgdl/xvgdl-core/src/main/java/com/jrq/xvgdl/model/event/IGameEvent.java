package com.jrq.xvgdl.model.event;

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
    long getTimeStamp();

    /**
     * Launcher Timer of the event.
     *
     * @return the timer in milliseconds
     */
    long getTimer();

    /**
     * Executes the event.
     */
    void executeEvent();

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
    boolean isConsumable();

}
