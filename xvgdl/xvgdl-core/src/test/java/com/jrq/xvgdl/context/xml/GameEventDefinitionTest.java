package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.event.GameEventType;
import com.jrq.xvgdl.model.event.IGameEvent;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class GameEventDefinitionTest {

    private GameEventDefinition gameEventDefinition;

    @Before
    public void setUp() {
        gameEventDefinition = new GameEventDefinition();
        gameEventDefinition.setClassName("com.jrq.xvgdl.model.event.KeyboardGameEvent");
        gameEventDefinition.setKeyCode(0);
        gameEventDefinition.setObjectName("objectName");
        gameEventDefinition.setTimer(5L);
        gameEventDefinition.setType("type");
        gameEventDefinition.setValue(15.0);
    }

    @Test
    public void toModelWithCorrectDefinition() throws ClassNotFoundException {
        IGameEvent gameEvent = gameEventDefinition.toModel();

        assertEquals("Checking the game definition", gameEvent.getGameEventDefinition(), gameEventDefinition);
        assertThat("Incorrect class", gameEvent, instanceOf(Class.forName(gameEventDefinition.getClassName())));
        assertEquals("Incorrect event type" , gameEvent.getEventType(), GameEventType.fromString(gameEventDefinition.getType()));
        assertEquals("Incorrect timer" , gameEvent.getTimer(), gameEventDefinition.getTimer());
        assertEquals("Incorrect value" , gameEvent.getValue(), gameEventDefinition.getValue());
    }

    @Test
    public void toModelWithIncorrectClassDefinition() throws ClassNotFoundException {
        gameEventDefinition.setClassName("Invalid");
        IGameEvent gameEvent = gameEventDefinition.toModel();

        assertNull("Should be null model" , gameEvent);
    }
}