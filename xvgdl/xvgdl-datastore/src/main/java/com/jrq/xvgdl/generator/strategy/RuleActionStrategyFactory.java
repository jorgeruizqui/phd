package com.jrq.xvgdl.generator.strategy;

import com.jrq.xvgdl.model.rules.GameRuleResultType;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
public class RuleActionStrategyFactory {

    private static List<RuleActionStrategy> strategies = new ArrayList();

    public static List<RuleActionStrategy> getStrategies() {
        if (strategies.isEmpty()) {
            initializeStrategies();
        }
        return Collections.unmodifiableList(strategies);
    }

    private static void initializeStrategies() {
        Reflections reflections = new Reflections("com.jrq.xvgdl.generator.strategy");

        Set<Class<? extends RuleActionStrategy>> strategiesReflection =
                reflections.getSubTypesOf(RuleActionStrategy.class);


        strategiesReflection.stream().forEach(s -> {
            try {
                strategies.add(s.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                log.error("Error Loading RuleAction Strategies.", e);
            }
        });

    }

    public static RuleActionStrategy getRuleActionStrategy(GameRuleResultType gameRuleResultType) {
        return getStrategies().stream().filter(
                strategy -> strategy.supports(gameRuleResultType)).findFirst().orElse(null);
    }
}
