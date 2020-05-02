package com.jrq.xvgdl.pacman.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.event.AGameEvent;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.event.IGameEventExecutor;
import com.jrq.xvgdl.model.object.GameObjectType;
import lombok.Data;

/**
 * State Transition event implementation
 */
@Data
public class PacmanStateTransitionEvent extends AGameEvent {

    private String state = GameContext.DEFAULT_STATE;

    public PacmanStateTransitionEvent() {
        this.executor = new StateTransitionExecutor();
    }

    @Override
    public Boolean isConsumable() {
        return true;
    }

    public class StateTransitionExecutor implements IGameEventExecutor {

        @Override
        public void executeEvent(IGameEvent event, GameContext gameContext) {
            playerSpeedReset(gameContext);
            ghostsSpeedReset(gameContext);
            gameContext.setCurrentGameState(getState());
        }
        private void playerSpeedReset(GameContext gameContext) {
            gameContext.getCurrentGamePlayer().resetSpeedFactor();
        }

        private void ghostsSpeedReset(GameContext gameContext) {
            gameContext.getObjectsListByType(GameObjectType.ENEMY).forEach(o -> {
                o.resetSpeedFactor();
            });
        }

    }
}
