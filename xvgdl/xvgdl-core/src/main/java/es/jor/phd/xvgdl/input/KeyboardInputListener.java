package es.jor.phd.xvgdl.input;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.event.GameEventType;
import es.jor.phd.xvgdl.model.event.KeyboardGameEvent;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Default Keyboard input listener
 *
 * @author jrquinones
 *
 */
public class KeyboardInputListener implements NativeKeyListener {

    /**
     * Instance of the game context.
     */
    private GameContext gameContext;

    /**
     *
     * @param gameContext Game Context
     */
    public KeyboardInputListener(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {

        KeyboardGameEvent keyEvent = new KeyboardGameEvent(event.getKeyCode());
        keyEvent.setTimeStamp(System.currentTimeMillis());
        keyEvent.setEventType(GameEventType.KEYBOARD);
        ELogger.debug(this, GameConstants.GAME_ENGINE_LOGGER_CATEGORY, "Keyboard pressed: " + event.getKeyChar());
        gameContext.addEvent(keyEvent);

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent arg0) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {
    }

}
