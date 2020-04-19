package com.jrq.xvgdl.model.rules;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

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

    public static boolean executeResult(GameContext gameContext, IGameObject gameObject, IGameRuleAction gameRuleAction) {
        if (gameRuleAction != null) {

            log.debug("GRU: Executing result [" + gameRuleAction.getResultType() + " for game object [" + gameObject.getName() + "]");
            switch (gameRuleAction.getResultType()) {
                case TELETRANSPORT:
                    applyTeletransportRuleResult(gameContext, gameObject, gameRuleAction);
                    break;
                case DISAPPEAR:
                    gameContext.removeGameObject(gameObject);
                    break;
                case CANT_MOVE:
                    gameObject.resetMove();
                    break;
                case FREEZE:
                    GameEngine.getInstance().freezeObject(gameObject, gameRuleAction.getValueAsLong());
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
                    gameObject.getDirection().invert();
                     break;
                case STATE_TRANSITION:
                    gameContext.setCurrentGameState(gameRuleAction.getValue());
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
                case BOUNCE:
                case DUPLICATE:
                case TIME_RESET:
                case TRANSFORM:
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
                gameObject.moveTo(objectReferenced.getX(), objectReferenced.getY(), objectReferenced.getZ());
            } else {
                // allows format "x,y,z"
                String[] position = value.split(",");
                gameObject.moveTo(Integer.parseInt(position[0]), Integer.parseInt(position[1]),
                        Integer.parseInt(position[2]));
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
