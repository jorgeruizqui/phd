package es.jor.phd.xvgdl.event;

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

}
