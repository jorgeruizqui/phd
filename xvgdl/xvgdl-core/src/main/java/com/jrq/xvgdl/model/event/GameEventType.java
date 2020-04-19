package com.jrq.xvgdl.model.event;

/**
 * Different kind of events
 *
 * @author jrquinones
 */
public enum GameEventType {

    ENGINE,
    MOUSE,
    KEYBOARD;

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
