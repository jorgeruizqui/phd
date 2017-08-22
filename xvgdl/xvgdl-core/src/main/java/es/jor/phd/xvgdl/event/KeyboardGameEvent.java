package es.jor.phd.xvgdl.event;

/**
 * Keyboard game event.
 *
 * @author jrquinones
 *
 */
public class KeyboardGameEvent extends AGameEvent {

    /** Key Code. */
    private int keyCode;

    /**
     *
     * @param keyCode Key Code
     */
    public KeyboardGameEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * @return the keyCode
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * @param keyCode the keyCode to set
     */
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

}
