package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameEventDefinition;

import java.util.List;

/**
 * Game Event interface
 *
 * @author jrquinones
 */
public interface IGameEvent {

    GameEventType getEventType();

    Long getTimeStamp();

    Long getTimer();

    Double getValue();

    GameEventDefinition getGameEventDefinition();

    Boolean isConsumable();

    void setEventType(GameEventType gameEventType);

    void setTimeStamp(Long timeStamp);

    void executeEvent(GameContext gameContext);

    List<String> getGameStates();

}
