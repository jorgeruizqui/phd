package com.jrq.xvgdl.input;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.event.GameEventType;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.event.KeyboardGameEvent;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    public KeyboardInputListener(GameContext gameContext) throws NativeHookException {
        setNativeHookLogLevelOff();
        this.gameContext = gameContext;
        initializeKeyboardListener();
    }

    public void initializeKeyboardListener() throws NativeHookException {
        registerNativeHook();
        addConfiguredKeyboardEvents();
        addDefaultKeyboardEvents();
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

    private void registerNativeHook() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }

    private void addConfiguredKeyboardEvents() {
        List<KeyboardGameEvent> definedKeyboardGameEvents =
                this.gameContext.getGameEvents().stream()
                        .filter(e -> e instanceof KeyboardGameEvent)
                        .map(o -> (KeyboardGameEvent) o)
                        .collect(Collectors.toList());

        this.addContextDefinedKeyboardGameEvent(definedKeyboardGameEvents);
    }

    private void addDefaultKeyboardEvents() {
        this.addContextDefinedKeyboardGameEvent(Arrays.asList(
                new KeyboardGameEvent(NativeKeyEvent.VC_LEFT),
                new KeyboardGameEvent(NativeKeyEvent.VC_RIGHT),
                new KeyboardGameEvent(NativeKeyEvent.VC_UP),
                new KeyboardGameEvent(NativeKeyEvent.VC_DOWN)));
    }

    private void setNativeHookLogLevelOff() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        Handler[] handlers = Logger.getLogger("").getHandlers();
        for (Handler handler : handlers) {
            handler.setLevel(Level.OFF);
        }
    }

    private void addContextDefinedKeyboardGameEvent(List<KeyboardGameEvent> kbGameEvent) {
        this.contextDefinedKbGameEvents.addAll(kbGameEvent);
    }

}
