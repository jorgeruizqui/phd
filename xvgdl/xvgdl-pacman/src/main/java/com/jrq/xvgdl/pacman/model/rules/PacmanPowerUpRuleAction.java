package com.jrq.xvgdl.pacman.model.rules;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.event.AGameEvent;
import com.jrq.xvgdl.model.event.GameEventType;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.rules.GameRuleAction;
import com.jrq.xvgdl.pacman.model.event.PacmanStateTransitionEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * This rule has several effects
 * 1. Pacman will speed up
 * 2. Game state will be updated to pacmanPowerUp
 * 3. A new event will be created to return back to default game state
 *
 */
@Slf4j
public class PacmanPowerUpRuleAction extends GameRuleAction {

    @Override
    public boolean executeGameRuleAction(GameContext gameContext, IGameObject gameObject1, IGameObject gameObject2) {
        log.debug("Executing PacmanPowerUpRuleAction rule action");
        playerSpeedUp(gameObject1);
        playerScoreUp(gameContext);
        ghostsSpeedDown(gameContext);
        setPacmanPowerUpContext(gameContext);
        createTransitionToDefaultGameState(gameContext);
        return true;
    }

    private void createTransitionToDefaultGameState(GameContext gameContext) {
        AGameEvent transitionEvent = new PacmanStateTransitionEvent();
        transitionEvent.setEventType(GameEventType.ENGINE);
        transitionEvent.setTimer(5000L);
        transitionEvent.setGameStates(Arrays.asList("pacmanPowerUp"));
        transitionEvent.setTimeStamp(System.currentTimeMillis());
        gameContext.addEvent(transitionEvent);
        log.debug("PacmanPowerUpRuleAction: New event created to be executed at " + transitionEvent.getTimer());
    }

    private void setPacmanPowerUpContext(GameContext gameContext) {
        gameContext.setCurrentGameState("pacmanPowerUp");
    }

    private void playerSpeedUp(IGameObject gameObject) {
        gameObject.increaseSpeedFactor(1.0);
    }

    private void playerScoreUp(GameContext gameContext) {
        gameContext.getCurrentGamePlayer().scoreUp(getValueAsDouble());
    }

    private void ghostsSpeedDown(GameContext gameContext) {
        gameContext.getObjectsListByType(GameObjectType.ENEMY).forEach(o -> {
            o.decreaseSpeedFactor(1.0d);
        });
    }
}
