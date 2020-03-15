package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.objectives.IGameObjective;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class GameObjectiveDefinitionTest {

    private GameObjectiveDefinition gameObjectiveDefinition;

    @Before
    public void setUp() throws Exception {
        gameObjectiveDefinition = new GameObjectiveDefinition();
        gameObjectiveDefinition.setObjectiveCheckerClass("com.jrq.xvgdl.model.objectives.PlayerWinsObjective");
        gameObjectiveDefinition.setScore(0.5);
        gameObjectiveDefinition.setWeight(0.1);
    }

    @Test
    public void toModelWithCorrectDefinition() throws ClassNotFoundException {
        IGameObjective gameObjective = gameObjectiveDefinition.toModel();

        assertNotNull(gameObjective);
        assertEquals("Checking the game objective", gameObjective.getGameObjectiveDefinition(), gameObjectiveDefinition);
        assertThat("Incorrect class", gameObjective, instanceOf(Class.forName(gameObjectiveDefinition.getObjectiveCheckerClass())));
        assertEquals("Incorrect field" , gameObjective.getScore(), gameObjectiveDefinition.getScore());
        assertEquals("Incorrect field" , gameObjective.getWeight(), gameObjectiveDefinition.getWeight());
    }

    @Test
    public void toModelWithIncorrectDefinition() throws ClassNotFoundException {
        gameObjectiveDefinition.setObjectiveCheckerClass("Invalid");
        IGameObjective gameObjective = gameObjectiveDefinition.toModel();

        assertNull(gameObjective);
    }
}