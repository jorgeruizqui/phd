package es.jor.phd.xvgdl.model.rules;

import org.junit.Assert;
import org.junit.Test;

/**
 * Rules tests.
 *
 * @author jrquinones
 *
 */
public class RulesTest {

    /**
     * Test Rule Result type converting to String
     */
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
