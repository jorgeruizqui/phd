package es.jor.phd.xvgdl.event;

/**
 * Game Event interface
 *
 * @author jrquinones
 *
 */
public interface IGameEvent {

    /**
     *
     * @return the event type
     */
    GameEventType getEventType();

    /**
     *
     * @return the event time in milliseconds
     */
    long getTimeStamp();
}
