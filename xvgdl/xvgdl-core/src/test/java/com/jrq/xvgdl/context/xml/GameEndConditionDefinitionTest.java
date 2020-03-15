package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.*;

public class GameEndConditionDefinitionTest {

    private GameEndConditionDefinition gec;

    @Before
    public void initialize() {
        gec = new GameEndConditionDefinition();
        gec.setCheckerClass("com.jrq.xvgdl.model.endcondition.LivesZeroGameEndCondition");
        gec.setObjectNames("objectNames");
        gec.setProperty("property");
        gec.setValue("value");
        gec.setWinningCondition(true);
    }

    @Test
    public void toModelForACorrectDefinition() throws ClassNotFoundException {
        IGameEndCondition aGameEndCondition = gec.toModel();

        assertNotNull(aGameEndCondition);
        assertThat("Incorrect class", aGameEndCondition, instanceOf(Class.forName(gec.getCheckerClass())));
        assertEquals("Error checking field", aGameEndCondition.getGameEndConditionDefinition(), gec);
    }

    @Test
    public void toModelForAnIncorrectClassDefinition() throws ClassNotFoundException {
        gec.setCheckerClass("Invalid");
        IGameEndCondition aGameEndCondition = gec.toModel();

        assertNull(aGameEndCondition);
    }
}