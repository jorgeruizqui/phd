package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.GamePlayer;
import com.jrq.xvgdl.model.object.IGameObject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GamePlayerDefinitionTest {

    private GamePlayerDefinition gamePlayerDefinition;

    @Before
    public void setUp() {
        gamePlayerDefinition = new GamePlayerDefinition();
        gamePlayerDefinition.setInitialLives(3);
        gamePlayerDefinition.setLivePercentage(89);
        gamePlayerDefinition.setLives(1);
        gamePlayerDefinition.setScore(9.0);
    }

    @Test
    public void toModelWithCorrectDefinition() {
        GamePlayer gamePlayer = (GamePlayer) gamePlayerDefinition.toModel();

        assertNotNull(gamePlayer);
        assertEquals("Incorrect Field", gamePlayer.getInitialLives(), gamePlayerDefinition.getInitialLives());
        assertEquals("Incorrect Field", gamePlayer.getLivePercentage(), gamePlayerDefinition.getLivePercentage());
        assertEquals("Incorrect Field", gamePlayer.getLives(), gamePlayerDefinition.getLives());
        assertEquals("Incorrect Field", gamePlayer.getScore(), gamePlayerDefinition.getScore());
    }

    @Test
    public void toModelWithInvalidAIClass() {
        gamePlayerDefinition.setAi("Invalid");
        IGameObject gameObject = gamePlayerDefinition.toModel();

        assertNull(gameObject);
    }
}