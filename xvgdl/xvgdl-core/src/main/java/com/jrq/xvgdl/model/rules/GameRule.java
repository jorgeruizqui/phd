package com.jrq.xvgdl.model.rules;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameRuleDefinition;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Game Rule
 *
 * @author jrquinones
 */
@Data
@Slf4j
public class GameRule implements IGameRule {

    private String name;
    private Boolean fixed;
    private GameRuleType type;
    private GameRuleDefinition gameRuleDefinition;
    private List<IGameRuleAction> ruleActions = new ArrayList<>();
    private List<String> gameStates = new ArrayList<>();

    @Override
    public void addRuleAction(IGameRuleAction action) {
        this.ruleActions.add(action);
    }

    @Override
    public IGameRuleAction getRuleActionByObjectName(String objectName) {
        return ruleActions.stream().filter(s -> s.getObjectName().equalsIgnoreCase(objectName)).findFirst()
                .orElse(null);
    }

    @Override
    public void evolution() {
        ruleActions.forEach(this::evolveRuleAction);
    }

    @Override
    public List<String> getGameStates() {
        return this.gameStates;
    }

    public boolean applyGameRule(GameContext gameContext) {

        boolean rto = false;
        switch (getType()) {
            case COLLISION:
                rto = manageCollisionRule(gameContext);
                break;
            case GENERIC:
                rto = manageGenericRule(gameContext);
                break;
            case END_CONDITION:
            case DISTANT:
            case PROXIMITY:
                return true;
        }
        return rto;
    }

    public void addGameState(String gameState) {
        if(StringUtils.isNotEmpty(gameState)) {
            this.gameStates.addAll(Arrays.asList(gameState.split(",")));
        } else {
            this.gameStates.add(GameContext.DEFAULT_STATE);
        }
    }

    private boolean manageGenericRule(GameContext gameContext) {
        this.getRuleActions().forEach(ra -> ra.executeGameRuleAction(gameContext, null, null));
        return true;
    }

    private void evolveRuleAction(IGameRuleAction gameRuleAction) {
        if (ThreadLocalRandom.current().nextDouble() >= 0.95d) {
            GameRuleUtils.evolutionOfRuleAction(gameRuleAction);
        }
    }

    private boolean manageCollisionRule(GameContext gameContext) {
        boolean rto = false;

        // Getting the object names applying to this rule
        List<String> objectNames = this.getRuleActions().stream().map(IGameRuleAction::getObjectName)
                .collect(Collectors.toList());

        if (objectNames.size() == 2) {
            List<IGameObject> listOfFirstRuleObjects = gameContext.getObjectsListByName(objectNames.get(0));
            List<IGameObject> listOfSecondRuleObjects = gameContext.getObjectsListByName(objectNames.get(1));
            for (IGameObject object1 : listOfFirstRuleObjects) {
                for (IGameObject object2 : listOfSecondRuleObjects) {
                    if (objectsAreCollisioning(object1, object2)) {
                        log.debug("Collision Rule " + this.getName()
                                + " satisfied for two objects ["
                                + object1.getId() + ", " + object2.getId() + "]");
                        this.getRuleActionByObjectName(object1.getName()).executeGameRuleAction(
                                gameContext, object1, object2);
                        this.getRuleActionByObjectName(object2.getName()).executeGameRuleAction(
                                gameContext, object2, object1);
                    }
                }
            }
        } else {
            log.error("Error applying Collision Rule " + this.getName()
                    + ". Two Action objects must be specified for this kind of rules.");
        }
        return rto;
    }

    /**
     * Objects are collisioning if
     * 1. They are in the same position
     * 2. They interchange their positions
     * @param object1
     * @param object2
     * @return
     */
    private boolean objectsAreCollisioning(IGameObject object1, IGameObject object2) {
        return locatedInSamePosition(object1, object2) || exchangingPosition(object1, object2);
    }

    private boolean locatedInSamePosition(IGameObject object1, IGameObject object2) {
        return object1.getPosition().equals(object2.getPosition());
    }

    private boolean exchangingPosition(IGameObject object1, IGameObject object2) {
        return object1.getPosition().equals(object2.getLastPosition())
                && object1.getLastPosition().equals(object2.getPosition());
    }
}
