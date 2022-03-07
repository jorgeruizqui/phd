package com.jrq.xvgdl.model.rules;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Game Rule Utils
 *
 * @author jrquinones
 */
@Slf4j
public final class GameRuleUtils {

    private static final Random RANDOM = new Random();

    private GameRuleUtils() {
    }

    public static boolean executeResult(GameContext gameContext, IGameObject gameObject1, IGameObject gameObject2,
        IGameRuleAction gameRuleAction) {
        if (gameRuleAction != null && gameRuleAction.getResultType() != null) {

            // may be it's null because it's a generic rule, in this case we assign any of the objects
            if (gameObject1 == null) {
                gameObject1 = gameContext.getObjectsListByName(gameRuleAction.getObjectName()).stream().findAny()
                    .orElse(null);
            }

            log.debug("GRU: Executing result [" + gameRuleAction.getResultType() + " for game object [" + gameRuleAction
                .getObjectName() + "]");
            switch (gameRuleAction.getResultType()) {
                case TELETRANSPORT:
                    applyTeletransportRuleResult(gameContext, gameObject1, gameRuleAction);
                    break;
                case INITIAL_POSITION:
                    gameObject1.setPosition(gameObject1.getInitialPosition().getX(),
                        gameObject1.getInitialPosition().getY(),
                        gameObject1.getInitialPosition().getZ());
                    break;
                case DISAPPEAR:
                    if (gameObject1 != null && !GameObjectType.PLAYER.equals(gameObject1.getObjectType())) {
                        gameContext.removeGameObject(gameObject1);
                    }
                    break;
                case CANT_MOVE:
                    gameObject1.resetMove(gameObject2);
                    break;
                case FREEZE:
                    GameEngine.getInstance().freezeObject(gameObject1, gameRuleAction.getValueAsLong());
                    break;
                case LIVES_DOWN:
                    gameContext.getCurrentGamePlayer().livesDown();
                    break;
                case LIVES_UP:
                    gameContext.getCurrentGamePlayer().livesUp();
                    break;
                case LIVES_RESET:
                    gameContext.getCurrentGamePlayer().liveReset();
                    break;
                case SCORE_DOWN:
                    gameContext.getCurrentGamePlayer().scoreDown(gameRuleAction.getValueAsLong());
                    break;
                case SCORE_RESET:
                    gameContext.getCurrentGamePlayer().scoreSetTo(0);
                    break;
                case SCORE_SET_TO:
                    gameContext.getCurrentGamePlayer().scoreSetTo(gameRuleAction.getValueAsLong());
                    break;
                case SCORE_UP:
                    gameContext.getCurrentGamePlayer().scoreUp(gameRuleAction.getValueAsLong());
                    break;
                case TIME_DOWN:
                    gameContext.setTimeout(gameContext.getTimeout() - gameRuleAction.getValueAsLong());
                    break;
                case TIME_UP:
                    gameContext.setTimeout(gameContext.getTimeout() + gameRuleAction.getValueAsLong());
                    break;
                case CHANGE_DIRECTION:
                    gameObject1.getDirection().invert();
                    break;
                case STATE_TRANSITION:
                    if (!StringUtils.isEmpty(gameRuleAction.getValue())) {
                        gameContext.setCurrentGameState(gameRuleAction.getValue());
                    } else {
                        gameContext.setCurrentGameState("default");
                    }
                    break;
                case LIVES_PERCENTAGE_UP:
                    gameContext.getCurrentGamePlayer().setLivePercentage(
                        gameContext.getCurrentGamePlayer().getLivePercentage() + gameRuleAction.getValueAsInteger());
                    break;
                case LIVES_PERCENTAGE_DOWN:
                    gameContext.getCurrentGamePlayer().setLivePercentage(
                        gameContext.getCurrentGamePlayer().getLivePercentage() - gameRuleAction.getValueAsInteger());
                    break;
                case LIVES_PERCENTAGE_RESET:
                    gameContext.getCurrentGamePlayer().setLivePercentage(100);
                    break;
                case SPEED_UP:
                    gameContext.getObjectsListByName(gameRuleAction.getObjectName()).forEach(iGameObject ->
                        iGameObject.increaseSpeedFactor(gameRuleAction.getValueAsDouble()));
                    break;
                case SPEED_DOWN:
                    gameContext.getObjectsListByName(gameRuleAction.getObjectName()).forEach(iGameObject ->
                        iGameObject.decreaseSpeedFactor(gameRuleAction.getValueAsDouble()));
                    break;
                case SPEED_RESET:
                    gameContext.getObjectsListByName(gameRuleAction.getObjectName()).forEach(
                        IGameObject::resetSpeedFactor);
                    break;
                case DUPLICATE:
                    if (!GameObjectType.PLAYER.equals(gameObject1.getObjectType())) {
                        //                        gameContext.addObject(gameObject1.copy());
                    }
                    break;
                case TRANSFORM:
                    if (!GameObjectType.PLAYER.equals(gameObject1.getObjectType())) {
                        gameObject1.setObjectType(GameObjectType.fromString(gameRuleAction.getValue()));
                    }
                    break;
                case BOUNCE:
                case TIME_RESET:
                case NONE:
                default:
            }
        }
        return true;
    }

    private static void applyTeletransportRuleResult(GameContext gameContext, IGameObject gameObject,
        IGameRuleAction gameRuleAction) {
        String value = gameRuleAction.getValue();
        if (value == null || value.isEmpty()) {
            gameObject.moveTo(RANDOM.nextInt(gameContext.getGameMap().getSizeX()),
                RANDOM.nextInt(gameContext.getGameMap().getSizeY()), 0);
        } else {
            List<IGameObject> objects = GameEngine.getInstance().getGameContext().getObjectsListByName(value);
            if (objects != null && !objects.isEmpty()) {
                IGameObject objectReferenced = objects.get(0);
                gameObject.moveTo(objectReferenced.getPosition().getX(),
                    objectReferenced.getPosition().getY(),
                    objectReferenced.getPosition().getZ());
            } else {
                // allows format "x,y,z"
                String[] position = value.split(",");
                if (position.length == 3) {
                    gameObject.moveTo(Integer.parseInt(position[0]), Integer.parseInt(position[1]),
                        Integer.parseInt(position[2]));
                } else {
                    gameObject
                        .moveTo(gameObject.getInitialPosition().getX(), gameObject.getInitialPosition().getY(), 0);
                }
            }
        }
    }

    public static void evolutionOfRuleAction(IGameRuleAction gameRuleAction) {
        if (gameRuleAction != null) {
            switch (gameRuleAction.getResultType()) {
                // TODO To be implemented for each of the rules
                default:
                    break;
            }
        }
    }
}
