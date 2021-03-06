package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * Keyboard game event.
 *
 * @author jrquinones
 */
@NoArgsConstructor
@Getter
@Setter
public class KeyboardGameEvent extends AGameEvent {

    private int keyCode;

    public KeyboardGameEvent(int keyCode) {
        this.keyCode = keyCode;
        this.setEventType(GameEventType.KEYBOARD);
        this.executor = new KeyboardGameExecutor();
    }

    @Override
    public Boolean isConsumable() {
        return true;
    }

    public class KeyboardGameExecutor implements IGameEventExecutor {

        @Override
        public void executeEvent(IGameEvent event, GameContext context) {
            if (event.getEventType().equals(GameEventType.KEYBOARD)) {

                KeyboardGameEvent kbGe = (KeyboardGameEvent) event;
                IGameObject player = context.getCurrentGamePlayer();

                switch (kbGe.getKeyCode()) {
                    case NativeKeyEvent.VC_LEFT:
                        player.moveTo(player.getPosition().getX(), player.getPosition().getY() - 1, player.getPosition().getZ());
                        break;
                    case NativeKeyEvent.VC_RIGHT:
                        player.moveTo(player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
                        break;
                    case NativeKeyEvent.VC_UP:
                        player.moveTo(player.getPosition().getX() + 1, player.getPosition().getY(), player.getPosition().getZ());
                        break;
                    case NativeKeyEvent.VC_DOWN:
                        player.moveTo(player.getPosition().getX() - 1, player.getPosition().getY(), player.getPosition().getZ());
                        break;
                    case NativeKeyEvent.VC_P:
                        context.setGamePaused(true);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
