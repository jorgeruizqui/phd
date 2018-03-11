package es.jor.phd.xvgdl.model.event;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.context.xml.GameEventDefinition;
import lombok.Data;

/**
 * Abstract class representing the superclass of all game events
 *
 * @author jrquinones
 *
 */
@Data
public abstract class AGameEvent implements IGameEvent {

    /** Game Event Type. */
    private GameEventType eventType;

    /** Game Event Time stamp in milliseconds. */
    private long timeStamp;

    /**
     * Game Event Timer in milliseconds. According to last time stamp, this
     * timer
     * indicates not to launch the event if not enough time has passed.
     */
    private long timer;

    /** Game Event Executor. */
    protected IGameEventExecutor executor;

    /** Game Event definition. Needed by event executors. */
    private GameEventDefinition gameEventDefinition;

    @Override
    public void executeEvent() {
        setTimeStamp(System.currentTimeMillis());
        executor.executeEvent(this, GameContext.getInstance());
    }

    /**
     *
     * @param gameEventDefinition Game event definition to set
     */
    public void setGameEventDefinition(GameEventDefinition gameEventDefinition) {
        this.gameEventDefinition = gameEventDefinition;
    }

    /**
     *
     * @return Game event definition
     */
    public GameEventDefinition getGameEventDefinition() {
        return gameEventDefinition;
    }

}
