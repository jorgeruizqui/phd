package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameEventDefinition;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Abstract class representing the superclass of all game events
 *
 * @author jrquinones
 */
@Data
public abstract class AGameEvent implements IGameEvent {

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

    private List<String> gameStates = new ArrayList<>();

    protected IGameEventExecutor executor;

    private GameEventDefinition gameEventDefinition;

    @Override
    public void executeEvent(GameContext gameContext) {
        setTimeStamp(System.currentTimeMillis());
        executor.executeEvent(this, gameContext);
    }

    public void setGameEventDefinition(GameEventDefinition gameEventDefinition) {
        this.gameEventDefinition = gameEventDefinition;
        updateDefinitionFields();
    }

    protected void updateDefinitionFields() {
    }

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

    @Override
    public List<String> getGameStates() {
        return gameStates;
    }

    public void addGameState(String gameState) {
        if(StringUtils.isNotEmpty(gameState)) {
            this.gameStates.addAll(Arrays.asList(gameState.split(",")));
        } else {
            this.gameStates.add(GameContext.DEFAULT_STATE);
        }
    }
}
