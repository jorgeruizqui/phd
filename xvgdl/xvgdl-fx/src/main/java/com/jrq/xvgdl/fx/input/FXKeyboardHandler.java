package com.jrq.xvgdl.fx.input;

import com.jrq.xvgdl.fx.context.FXGameContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FXKeyboardHandler implements EventHandler<KeyEvent> {

    private Map<KeyCode, KeyCode> input = new HashMap<>();
    private final FXGameContext fxGameContext;

    @Override
    public void handle(KeyEvent keyEvent) {

        if (keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            // only add once... prevent duplicates
            if (input.get(keyEvent.getCode()) == null) {
                input.put(keyEvent.getCode(), keyEvent.getCode());

                if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
                    fxGameContext.getFxGamePlayer().updateVelocityY(2d);
                } else if (keyEvent.getCode().equals(KeyCode.LEFT)) {
                    fxGameContext.getFxGamePlayer().updateVelocityY(-2d);
                } else if (keyEvent.getCode().equals(KeyCode.UP)) {
                    fxGameContext.getFxGamePlayer().updateVelocityX(-2d);
                } else if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                    fxGameContext.getFxGamePlayer().updateVelocityX(2d);
                }
            }
        } else if (keyEvent.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            input.remove(keyEvent.getCode());

//            fxGameContext.getFxGamePlayer().resetSpeed();
            if (input.get(KeyCode.LEFT) == null && input.get(KeyCode.RIGHT) == null) {
                fxGameContext.getFxGamePlayer().updateVelocityY(0d);
            } else if (input.get(KeyCode.UP) == null && input.get(KeyCode.DOWN) == null) {
                fxGameContext.getFxGamePlayer().updateVelocityX(0d);
            }
        }
    }
}
