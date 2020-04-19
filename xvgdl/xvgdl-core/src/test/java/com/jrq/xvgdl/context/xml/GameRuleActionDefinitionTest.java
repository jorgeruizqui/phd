package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.rules.GameRuleResultType;
import com.jrq.xvgdl.model.rules.IGameRuleAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class GameRuleActionDefinitionTest {

    private GameRuleActionDefinition gameRuleActionDefinition;

    @Before
    public void setUp() throws Exception {
        gameRuleActionDefinition = new GameRuleActionDefinition();
        gameRuleActionDefinition.setObjectName("objectName");
        gameRuleActionDefinition.setResult("CANT_MOVE");
        gameRuleActionDefinition.setValue("value");
    }

    @Test
    public void toModelWithCorrectDefinition() {
        IGameRuleAction gameRuleAction = gameRuleActionDefinition.toModel();

        assertNotNull(gameRuleAction);
        assertEquals("Incorrect field", gameRuleAction.getObjectName(), gameRuleActionDefinition.getObjectName());
        assertEquals("Incorrect field", gameRuleAction.getValue(), gameRuleActionDefinition.getValue());
        assertEquals("Incorrect field", gameRuleAction.getResultType(), GameRuleResultType.fromString(gameRuleActionDefinition.getResult()));
    }

    @Test
    public void toModelWithIncorrectDefinition() {
        gameRuleActionDefinition.setResult(null);

        IGameRuleAction gameRuleAction = gameRuleActionDefinition.toModel();

        assertEquals(GameRuleResultType.NONE, gameRuleAction.getResultType());
    }
}