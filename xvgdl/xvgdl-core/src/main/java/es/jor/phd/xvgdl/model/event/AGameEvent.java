package es.jor.phd.xvgdl.model.event;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.context.xml.GameEventDefinition;

/**
 * Abstract class representing the superclass of all game events
 *
 * @author jrquinones
 *
 */
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
    public GameEventType getEventType() {
        return eventType;
    }

    /**
     *
     * @param eventType The event type
     */
    public void setEventType(GameEventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     *
     * @param timeStamp the Time stamp
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public void executeEvent() {
        timeStamp = System.currentTimeMillis();
        executor.executeEvent(this, GameContext.getInstance());;
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

    @Override
    public long getTimer() {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(long timer) {
        this.timer = timer;
    }

    /**
     * Is consumible flag.
     */
    public abstract boolean isConsumable();

}
