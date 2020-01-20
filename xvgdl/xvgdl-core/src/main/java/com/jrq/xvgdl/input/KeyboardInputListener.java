package com.jrq.xvgdl.input;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.event.GameEventType;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.event.KeyboardGameEvent;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Default Keyboard input listener
 *
 * @author jrquinones
 */
@Slf4j
public class KeyboardInputListener implements NativeKeyListener {

    /**
     * Instance of the game context.
     */
    private GameContext gameContext;

    private List<KeyboardGameEvent> contextDefinedKbGameEvents = new ArrayList<>();

    /**
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
