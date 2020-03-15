package com.jrq.xvgdl.model.rules;

import org.junit.Assert;
import org.junit.Test;

/**
 * Rules tests.
 *
 * @author jrquinones
 */
public class RulesTest {

    @Test
    public void ruleResultTypeToString() {
        Assert.assertNull(GameRuleResultType.fromString("nonValid"));
        GameRuleResultType type = GameRuleResultType.fromString("bounce");
        Assert.assertEquals(GameRuleResultType.BOUNCE, type);
        type = GameRuleResultType.fromString("BOUNCE");
        Assert.assertEquals(GameRuleResultType.BOUNCE, type);
        type = GameRuleResultType.fromString("BOUNCE    ");
        Assert.assertEquals(GameRuleResultType.BOUNCE, type);
        type = GameRuleResultType.fromString("   BOUNCE    ");
        Assert.assertEquals(GameRuleResultType.BOUNCE, type);
        type = GameRuleResultType.fromString("   BOUNCE");
        Assert.assertEquals(GameRuleResultType.BOUNCE, type);
    }

}
