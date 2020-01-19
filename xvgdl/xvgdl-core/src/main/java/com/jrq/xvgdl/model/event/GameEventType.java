package com.jrq.xvgdl.model.event;

/**
 * Different kind of events
 *
 * @author jrquinones
 */
public enum GameEventType {

    /**
     * Engine event.
     */
    ENGINE,
    /**
     * Mouse
     */
    MOUSE,
    /**
     * Keyboard
     */
    KEYBOARD;

    /**
     * @param typeSt Type as string
     * @return The GameObjectType according to the typeSt
     */
    public static GameEventType fromString(String typeSt) {
        GameEventType type = null;

        if (typeSt.trim().equalsIgnoreCase(MOUSE.toString())) {
            type = MOUSE;
        } else if (typeSt.trim().equalsIgnoreCase(KEYBOARD.toString())) {
            type = KEYBOARD;
        } else if (typeSt.trim().equalsIgnoreCase(ENGINE.toString())) {
            type = ENGINE;
        }

        return type;
    }
}
