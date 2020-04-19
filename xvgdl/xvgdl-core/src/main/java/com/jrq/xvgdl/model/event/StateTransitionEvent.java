package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;
import lombok.Data;

/**
 * State Transition event implementation
 */
@Data
public class StateTransitionEvent extends AGameEvent {

    private String state = GameContext.DEFAULT_STATE;

    public StateTransitionEvent() {
        this.executor = new StateTransitionExecutor();
    }

    @Override
    public Boolean isConsumable() {
        return true;
    }

    public class StateTransitionExecutor implements IGameEventExecutor {

        @Override
        public void executeEvent(IGameEvent event, GameContext gameContext) {
            gameContext.setCurrentGameState(getState());
            gameContext.getCurrentGamePlayer().resetSpeedFactor();
        }
    }
}
