package com.jrq.xvgdl.generator.utils;

import com.jrq.xvgdl.context.xml.GameDefinition;
import com.jrq.xvgdl.context.xml.GameRuleActionDefinition;
import com.jrq.xvgdl.context.xml.GameRuleDefinition;
import com.jrq.xvgdl.generator.strategy.RuleActionStrategy;
import com.jrq.xvgdl.generator.strategy.RuleActionStrategyFactory;
import com.jrq.xvgdl.model.rules.GameRuleResultType;
import com.jrq.xvgdl.model.rules.GameRuleType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuleDefinitionGeneratorUtils {

    private static final Random random = new Random();
    private static final String DEFAULT_GAME_STATE = "default";
    private static final List<String> objects = Arrays.asList("pacman", "ghost", "wall", "smallDot", "bigDot");

    public static GameDefinition generateGameDefinition(int numberOfRules) {
        GameDefinition gameDefinition = new GameDefinition();
        //FOR
        for (int i = 0; i < numberOfRules; i++) {
            GameRuleDefinition gameRule = generateGameRule();
            gameDefinition.getRules().add(gameRule);
        }
        return gameDefinition;
    }

    public static GameRuleDefinition generateGameRule() {

        String gameRuleTypeName = getRandomRuleType().name();

        GameRuleDefinition gameRuleDefinition = new GameRuleDefinition();
        gameRuleDefinition.setGameState(DEFAULT_GAME_STATE);
        gameRuleDefinition.setName(gameRuleTypeName + "-" + UUID.randomUUID().toString().substring(0, 5));
        gameRuleDefinition.setType(gameRuleTypeName);
        gameRuleDefinition.setRuleActions(generateGameRuleActions());
        gameRuleDefinition.setFixed(false);
        return gameRuleDefinition;
    }

    public static List<GameRuleActionDefinition> generateGameRuleActions() {

        List<GameRuleActionDefinition> gameRuleActionDefinitionList = new ArrayList<>();
        gameRuleActionDefinitionList.add(generateGameRuleAction(objects.get(random.nextInt(objects.size() - 1))));
        gameRuleActionDefinitionList.add(generateGameRuleAction(objects.get(random.nextInt(objects.size() - 1))));
        return gameRuleActionDefinitionList;
    }

    public static GameRuleActionDefinition generateGameRuleAction(String objectName) {
        GameRuleActionDefinition gameRuleActionDefinition = new GameRuleActionDefinition();

        GameRuleResultType resultType = getRandomRuleResultType();
        gameRuleActionDefinition.setResult(resultType.name());
        gameRuleActionDefinition.setObjectName(objectName);

        // TODO The rule value must be coherent with the type of the rule result type
        RuleActionStrategy strategy = RuleActionStrategyFactory.getRuleActionStrategy(resultType);
        if (strategy != null) {
            gameRuleActionDefinition.setValue(Optional.ofNullable(strategy.getRuleActionValue()).orElse(""));
        } else {
            log.warn("STRATEGY NOT FOUND for result Type: " + resultType);
        }

        return gameRuleActionDefinition;
    }

    private static GameRuleType getRandomRuleType() {
        GameRuleType generated = GameRuleType.GENERIC;
        boolean isGenerated = false;
        while (!isGenerated) {
            generated = GameRuleType.values()[random.nextInt(GameRuleType.values().length)];
            if (!GameRuleType.END_CONDITION.equals(generated)) {
                isGenerated = true;
            }
        }
        return generated;
    }

    private static GameRuleResultType getRandomRuleResultType() {
        return GameRuleResultType.values()[random.nextInt(GameRuleResultType.values().length)];
    }
}
