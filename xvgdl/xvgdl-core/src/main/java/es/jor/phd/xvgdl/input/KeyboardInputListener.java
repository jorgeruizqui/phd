package es.jor.phd.xvgdl.input;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.event.GameEventType;
import es.jor.phd.xvgdl.model.event.IGameEvent;
import es.jor.phd.xvgdl.model.event.KeyboardGameEvent;

/**
 * Default Keyboard input listener
 *
 * @author jrquinones
 *
 */
@Slf4j
public class KeyboardInputListener implements NativeKeyListener {

    /**
     * Instance of the game context.
     */
    private GameContext gameContext;
    
    private List<KeyboardGameEvent> contextDefinedKbGameEvents = new ArrayList<>();

    /**
     *
     * @param gameContext Game Context
     */
    public KeyboardInputListener(GameContext gameContext) {
        this.gameContext = gameContext;
    }
    
    public void addContextDefinedKeyboardGameEvent(KeyboardGameEvent kbGameEvent) {
        this.contextDefinedKbGameEvents.add(kbGameEvent);
    }
    
    public void addContextDefinedKeyboardGameEvent(List<KeyboardGameEvent> kbGameEvent) {
        this.contextDefinedKbGameEvents.addAll(kbGameEvent);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        
        for (IGameEvent kbGameEvent : contextDefinedKbGameEvents) {
            if (((KeyboardGameEvent) kbGameEvent).getKeyCode() == event.getKeyCode()) {
                gameContext.addEvent(kbGameEvent);
                return;
            }
        }

        KeyboardGameEvent keyEvent = new KeyboardGameEvent(event.getKeyCode());
        keyEvent.setTimeStamp(System.currentTimeMillis());
        keyEvent.setEventType(GameEventType.KEYBOARD);
        gameContext.addEvent(keyEvent);

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent arg0) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {
    }

}
