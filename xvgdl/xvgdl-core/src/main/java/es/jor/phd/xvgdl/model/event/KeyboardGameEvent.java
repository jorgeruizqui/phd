package es.jor.phd.xvgdl.model.event;

import org.jnativehook.keyboard.NativeKeyEvent;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;

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
        this.executor = new KeyboardGameExecutor();
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

    @Override
    public boolean isConsumable() {
        return true;
    }

    /**
     * Keyboard Event executor Game
     *
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
