package com.jrq.xvgdl.generator.strategy;

import com.jrq.xvgdl.generator.strategy.impl.TeletransportStrategy;
import junit.framework.TestCase;
import org.junit.Test;

public class RuleActionStrategyFactoryTest extends TestCase {

    @Test
    public void testStrategiesInitializer() {
        RuleActionStrategy strategy =
                RuleActionStrategyFactory.getStrategies().stream().filter(s -> s instanceof TeletransportStrategy).findFirst().orElse(null);

        assertNotNull(strategy);
        assertEquals(14, RuleActionStrategyFactory.getStrategies().size());
    }
}