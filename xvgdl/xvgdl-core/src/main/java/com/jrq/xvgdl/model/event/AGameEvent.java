package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameEventDefinition;
import lombok.Data;

import java.util.Optional;

/**
 * Abstract class representing the superclass of all game events
 *
 * @author jrquinones
 */
@Data
public abstract class AGameEvent implements IGameEvent {

    /**
     * Game Event Type.
     */
    private GameEventType eventType;

    /**
     * Game Event Time stamp in milliseconds.
     */
    private Long timeStamp;

    /**
     * Game Event Timer in milliseconds. According to last time stamp, this
     * timer
     * indicates not to launch the event if not enough time has passed.
     */
    private Long timer;

    /**
     * Game Event Executor.
     */
    protected IGameEventExecutor executor;

    /**
     * Game Event definition. Needed by event executors.
     */
    private GameEventDefinition gameEventDefinition;

    @Override
    public void executeEvent(GameContext gameContext) {
        setTimeStamp(System.currentTimeMillis());
        executor.executeEvent(this, gameContext);
    }

    /**
     * @param gameEventDefinition Game event definition to set
     */
    public void setGameEventDefinition(GameEventDefinition gameEventDefinition) {
        this.gameEventDefinition = gameEventDefinition;
        updateDefinitionFields();
    }

    protected void updateDefinitionFields() {
    }

    /**
     * @return Game event definition
     */
    public GameEventDefinition getGameEventDefinition() {
        return gameEventDefinition;
    }

    public Double getValue() {
        return gameEventDefinition.getValue();
    }

    public Long getTimeStamp() {
        return Optional.ofNullable(timeStamp).orElse(-1L);
    }

    public Long getTimer() {
        return Optional.ofNullable(timer).orElse(-1L);
    }
}
