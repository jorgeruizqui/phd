package es.jor.phd.xvgdl.model.rules;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.engine.GameEngine;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Rule Utils
 *
 * @author jrquinones
 *
 */
public final class GameRuleUtils {

    /** Private constructor. */
    private GameRuleUtils() {
    }

    /**
     * Apply game rule
     *
     * @param gameContext Game Context
     * @param gameRule Game Rule to be applied
     * @return {@link Boolean} <code>true</code> if the rule is applied correctly. <code>false</code> otherwise
     */
    public static boolean applyGameRule(GameContext gameContext, IGameRule gameRule) {

        boolean rto = true;
        if (GameRuleType.COLLISION.equals(gameRule.getGameRuleType())) {
            // Take rule actions and see objects involved in the rule
            List<String> objectNames = gameRule.getRuleActions().stream().map(IGameRuleAction::getObjectName)
                    .collect(Collectors.toList());

            // TODO Assuming there are two objects
            if (objectNames.size() == 2) {
                List<IGameObject> objects1 = gameContext.getObjectsListByName(objectNames.get(0));
                List<IGameObject> objects2 = gameContext.getObjectsListByName(objectNames.get(1));
                for (IGameObject object1 : objects1) {
                    for (IGameObject object2 : objects2) {
                        if (object1.getX() == object2.getX() && object1.getY() == object2.getY()
                                && object1.getZ() == object2.getZ()) {
                            ELogger.debug(GameRuleUtils.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                                    "Collision Rule " + gameRule.getRuleName() + " satisfied for two objects ["
                                            + object1.getId() + ", " + object2.getId() + "]");

                            GameRuleUtils.executeResult(gameContext, object1,
                                    gameRule.getRuleActionByName(object1.getName()));
                            GameRuleUtils.executeResult(gameContext, object2,
                                    gameRule.getRuleActionByName(object2.getName()));

                        }
                    }
                }
            } else {
                ELogger.error(GameRuleUtils.class, GameConstants.GAME_ENGINE_LOGGER_CATEGORY,
                        "Error applying Collision Rule " + gameRule.getRuleName()
                                + ". Two Action objects must be specified for this kind of rules.");
                rto = false;
            }
        } else if (GameRuleType.END_CONDITION.equals(gameRule.getGameRuleType())) {

        }

        return rto;
    }

    /**
     *
     * @param gameContext Game Context
     * @param gameObject Game Object
     * @param gameRuleAction Game Rule Action
     */
    public static void executeResult(GameContext gameContext, IGameObject gameObject, IGameRuleAction gameRuleAction) {
        if (gameRuleAction != null) {
            if (GameRuleResultType.TELETRANSPORT.equals(gameRuleAction.getResultType())) {
                Random r = new Random();
                gameObject.moveTo(r.nextInt(gameContext.getMap().getSizeX()),
                        r.nextInt(gameContext.getMap().getSizeY()), 0);
            } else if (GameRuleResultType.DISAPPEAR.equals(gameRuleAction.getResultType())) {
                gameContext.removeGameObject(gameObject);
            } else if (GameRuleResultType.CANT_MOVE.equals(gameRuleAction.getResultType())) {
                gameObject.resetMove();
            } else if (GameRuleResultType.BOUNCE.equals(gameRuleAction.getResultType())) {
            } else if (GameRuleResultType.DUPLICATE.equals(gameRuleAction.getResultType())) {
            } else if (GameRuleResultType.FREEZE.equals(gameRuleAction.getResultType())) {
            	GameEngine.getInstance().freezeObject(gameObject, Long.parseLong(gameRuleAction.getValue()));
            } else if (GameRuleResultType.LIVES_DOWN.equals(gameRuleAction.getResultType())) {
            	gameContext.getCurrentGamePlayer().livesDown();
            } else if (GameRuleResultType.LIVES_RESET.equals(gameRuleAction.getResultType())) {
            	gameContext.getCurrentGamePlayer().liveReset();
            } else if (GameRuleResultType.LIVES_UP.equals(gameRuleAction.getResultType())) {
            	gameContext.getCurrentGamePlayer().livesUp();
            } else if (GameRuleResultType.SCORE_DOWN.equals(gameRuleAction.getResultType())) {
            	gameContext.getCurrentGamePlayer().scoreDown(Integer.parseInt(gameRuleAction.getValue()));
            } else if (GameRuleResultType.SCORE_RESET.equals(gameRuleAction.getResultType())) {
            	gameContext.getCurrentGamePlayer().scoreSetTo(0);
            } else if (GameRuleResultType.SCORE_SET_TO.equals(gameRuleAction.getResultType())) {
            	gameContext.getCurrentGamePlayer().scoreSetTo(Integer.parseInt(gameRuleAction.getValue()));
            } else if (GameRuleResultType.SCORE_UP.equals(gameRuleAction.getResultType())) {
            	gameContext.getCurrentGamePlayer().scoreUp(Integer.parseInt(gameRuleAction.getValue()));
            } else if (GameRuleResultType.TIME_DOWN.equals(gameRuleAction.getResultType())) {
            } else if (GameRuleResultType.TIME_RESET.equals(gameRuleAction.getResultType())) {
            } else if (GameRuleResultType.TIME_UP.equals(gameRuleAction.getResultType())) {
            } else if (GameRuleResultType.TRANSFORM.equals(gameRuleAction.getResultType())) {
            }
        }
    }

}
