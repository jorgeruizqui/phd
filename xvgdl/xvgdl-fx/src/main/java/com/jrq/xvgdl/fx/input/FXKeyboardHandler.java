package com.jrq.xvgdl.fx.input;

import com.jrq.xvgdl.context.GameContext;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FXKeyboardHandler implements EventHandler<KeyEvent> {

    private List<KeyCode> input = new ArrayList<>();
    private final GameContext gameContext;

    @Override
    public void handle(KeyEvent keyEvent) {

        if (keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            // only add once... prevent duplicates
            if (!input.contains(keyEvent.getCode())) {
                input.add(keyEvent.getCode());
            }
        } else if (keyEvent.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            input.remove(keyEvent.getCode());
        }
    }
}
