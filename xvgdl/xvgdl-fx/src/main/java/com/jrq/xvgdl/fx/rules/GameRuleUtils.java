package com.jrq.xvgdl.fx.rules;

import com.jrq.xvgdl.fx.context.FXGameContext;
import com.jrq.xvgdl.fx.model.object.FXGameObject;
import com.jrq.xvgdl.model.rules.IGameRule;
import com.jrq.xvgdl.model.rules.IGameRuleAction;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameRuleUtils {

    public static void applyGameRule(IGameRule gameRule, FXGameContext fxGameContext) {

        switch (gameRule.getType()) {
            case COLLISION:
                manageCollisionRule(gameRule, fxGameContext);
                break;
            case GENERIC:
                manageGenericRule(gameRule, fxGameContext);
                break;
            case END_CONDITION:
            case DISTANT:
            case PROXIMITY:
        }
    }

    private static List<FXGameObject> getObjectListByName(String name, FXGameContext fxGameContext) {
        return fxGameContext.getFxGameObjects().stream().filter(fxGameObject -> fxGameObject.getName().equals(name)).collect(Collectors.toList());
    }

    private static void manageCollisionRule(IGameRule gameRule, FXGameContext fxGameContext) {

        List<String> objectNames = gameRule.getRuleActions().stream().map(IGameRuleAction::getObjectName)
            .collect(Collectors.toList());

        if (objectNames.size() == 2) {
            List<FXGameObject> listOfFirstRuleObjects = getObjectListByName(objectNames.get(0), fxGameContext);
            List<FXGameObject> listOfSecondRuleObjects = getObjectListByName(objectNames.get(1), fxGameContext);

            for (FXGameObject object1 : listOfFirstRuleObjects) {
                for (FXGameObject object2 : listOfSecondRuleObjects) {
                    if (objectsAreCollisioning(object1, object2)) {

                        log.info("Collision Rule " + gameRule.getName()
                            + " satisfied for two objects ["
                            + object1.getId() + ", " + object2.getId() + "]");
                        gameRule.getRuleActionByObjectName(object1.getName()).executeGameRuleAction(
                            fxGameContext, object1, object2);
                        gameRule.getRuleActionByObjectName(object2.getName()).executeGameRuleAction(
                            fxGameContext, object2, object1);
                    }
                }
            }
        } else {
            log.error("Error applying Collision Rule " + gameRule.getName()
                + ". Two Action objects must be specified for this kind of rules.");
        }
    }

    private static boolean objectsAreCollisioning(FXGameObject object1, FXGameObject object2) {
        return object1.getSprite() != null && object2.getSprite() != null &&
            object1.getSprite().intersects(object2.getSprite());
    }

    private static void manageGenericRule(IGameRule gameRule, FXGameContext fxGameContext) {
        gameRule.getRuleActions().forEach(ra -> ra.executeGameRuleAction(fxGameContext, null, null));
    }
}
