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

    /**
     * Key Code.
     */
    private int keyCode;

    /**
     * @param keyCode Key Code
     */
    public KeyboardGameEvent(int keyCode) {
        this.keyCode = keyCode;
        this.executor = new KeyboardGameExecutor();
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    /**
     * Keyboard Event executor Game
     */
    public class KeyboardGameExecutor implements IGameEventExecutor {

        @Override
        public void executeEvent(IGameEvent event, GameContext context) {
            if (event.getEventType().equals(GameEventType.KEYBOARD)) {

                KeyboardGameEvent kbGe = (KeyboardGameEvent) event;
                IGameObject player = context.getCurrentGamePlayer();

                switch (kbGe.getKeyCode()) {
                    case NativeKeyEvent.VC_LEFT:
                        player.moveTo(player.getX() - 1, player.getY(), player.getZ());
                        break;
                    case NativeKeyEvent.VC_RIGHT:
                        player.moveTo(player.getX() + 1, player.getY(), player.getZ());
                        break;
                    case NativeKeyEvent.VC_UP:
                        player.moveTo(player.getX(), player.getY() + 1, player.getZ());
                        break;
                    case NativeKeyEvent.VC_DOWN:
                        player.moveTo(player.getX(), player.getY() + -1, player.getZ());
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
