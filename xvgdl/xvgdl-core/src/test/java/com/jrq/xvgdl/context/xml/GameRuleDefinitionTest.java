package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.rules.GameRuleAction;
import com.jrq.xvgdl.model.rules.GameRuleType;
import com.jrq.xvgdl.model.rules.IGameRule;
import com.jrq.xvgdl.model.rules.IGameRuleAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameRuleDefinitionTest {

    @Mock
    private GameRuleActionDefinition gameRuleActionDefinition;

    private GameRuleDefinition gameRuleDefinition;

    @Before
    public void setUp() throws Exception {
        gameRuleDefinition = new GameRuleDefinition();
        gameRuleDefinition.setGameState("gameState");
        gameRuleDefinition.setName("name");
        gameRuleDefinition.setRuleActions(buildGameActionRulesDefinition());
        gameRuleDefinition.setType("collision");
    }

    @Test
    public void toModelWithCorrectDefinition() {
        IGameRuleAction mockedGameRuleAction = mockRuleAction();
        Mockito.when(gameRuleActionDefinition.toModel()).thenReturn(mockedGameRuleAction);

        IGameRule gameRule = gameRuleDefinition.toModel();

        assertNotNull("Game rule shouldn't be null", gameRule);
        assertEquals("Field error", gameRule.getType(), GameRuleType.fromString(gameRuleDefinition.getType()));
        assertEquals("Field error", gameRule.getName(), gameRuleDefinition.getName());
        assertEquals("Field error", gameRule.getRuleActions().size(), 1);
        assertEquals("Field error", gameRule.getRuleActionByObjectName("objectName"), mockedGameRuleAction);
    }

    @Test
    public void toModelWithIncorrectDefinitionForRuleAction() {
        Mockito.when(gameRuleActionDefinition.toModel()).thenThrow(new ClassCastException());

        IGameRule gameRule = gameRuleDefinition.toModel();

        assertNull("Game rule should be null", gameRule);
    }

    private List<GameRuleActionDefinition> buildGameActionRulesDefinition() {
        return Arrays.asList(gameRuleActionDefinition);
    }

    private IGameRuleAction mockRuleAction() {
        GameRuleAction gameRuleAction = new GameRuleAction();
        gameRuleAction.setObjectName("objectName");
        return gameRuleAction;
    }
}