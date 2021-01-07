package com.jrq.xvgdl.pacman.model.rules;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.rules.GameRuleAction;
import lombok.extern.slf4j.Slf4j;

/**
 * This rule is an special implementation of lives down for Pacman Game.
 * We know pacman and a ghost are collisioning, but we have to check if theres also
 * a dot in the same position. In case it is the last dot, then game has ended
 * and there's no effect
 *
 */
@Slf4j
public class PacmanCheckLivesDownRuleAction extends GameRuleAction {

    @Override
    public boolean executeGameRuleAction(GameContext gameContext, IGameObject gameObject) {

        if (!items(gameContext) || (itemInSamePosition(gameContext, gameObject.getPosition()) && isLastItem(gameContext))) {
            log.info("Last item caught at the same moment Pacman was caught. Lives won't be down");
        } else {
            gameContext.getCurrentGamePlayer().livesDown();
            gameObject.setPosition(gameObject.getInitialPosition().getX(),
                    gameObject.getInitialPosition().getY(),
                    gameObject.getInitialPosition().getZ());
        }
        return true;
    }

    private boolean items(GameContext gameContext) {
        return !gameContext.getObjectsListByType(GameObjectType.ITEM).isEmpty();
    }

    private boolean isLastItem(GameContext gameContext) {
        return gameContext.getObjectsListByType(GameObjectType.ITEM).size() <= 1;
    }

    private boolean itemInSamePosition(GameContext gameContext, Position position) {
        return (gameContext.getObjectsListByType(GameObjectType.ITEM).stream().anyMatch(
                o -> o.getPosition().equals(position)));
    }

}
