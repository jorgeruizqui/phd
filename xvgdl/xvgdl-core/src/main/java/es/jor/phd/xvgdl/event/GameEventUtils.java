package es.jor.phd.xvgdl.event;

import org.jnativehook.keyboard.NativeKeyEvent;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Game Rule Utils
 *
 * @author jrquinones
 *
 */
public final class GameEventUtils {

    /** Private constructor. */
    private GameEventUtils() {
    }

    /**
     *
     * @param gameContext Game Context
     * @param event Game Event
     */
    public static void applyGameEvent(GameContext gameContext, IGameEvent event) {
        if (event.getEventType().equals(GameEventType.KEYBOARD)) {
            KeyboardGameEvent kbGe = (KeyboardGameEvent) event;
            IGameObject player = gameContext.getCurrentGamePlayer();
            if (kbGe.getKeyCode() == NativeKeyEvent.VC_LEFT) {
                player.moveTo(player.getX() - 1, player.getY(), player.getZ());
            } else if (kbGe.getKeyCode() == NativeKeyEvent.VC_RIGHT) {
                player.moveTo(player.getX() + 1, player.getY(), player.getZ());
            } else if (kbGe.getKeyCode() == NativeKeyEvent.VC_UP) {
                player.moveTo(player.getX(), player.getY() + 1, player.getZ());
            } else if (kbGe.getKeyCode() == NativeKeyEvent.VC_DOWN) {
                player.moveTo(player.getX(), player.getY() + -1, player.getZ());
            }
        }
    }
}
