package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class GameObjectDefinitionTest {

    private GameObjectDefinition gameObjectDefinition;

    @Before
    public void setUp() {
        gameObjectDefinition = new GameObjectDefinition();
        gameObjectDefinition.setInstances(1);
        gameObjectDefinition.setDirection("0,1,0");
        gameObjectDefinition.setAi("com.jrq.xvgdl.model.object.ai.PlayerChaseNearestItemAI");
        gameObjectDefinition.setIsDynamic(true);
        gameObjectDefinition.setIsVolatile(true);
        gameObjectDefinition.setName("objectName");
        gameObjectDefinition.setPositionX(5);
        gameObjectDefinition.setPositionY(6);
        gameObjectDefinition.setPositionZ(7);
        gameObjectDefinition.setSizeX(8);
        gameObjectDefinition.setSizeY(9);
        gameObjectDefinition.setSizeZ(10);
        gameObjectDefinition.setType("ENEMY");
    }

    @Test
    public void toModelWithCorrectDefinition() throws ClassNotFoundException {
        IGameObject gameObject = gameObjectDefinition.toModel(5);

        assertNotNull(gameObject);
        assertThat("Incorrect class", gameObject.getAi(), instanceOf(Class.forName(gameObjectDefinition.getAi())));
        assertEquals("Incorrect Field", gameObject.getName(), gameObjectDefinition.getName());
        assertEquals("Incorrect Field", gameObject.getObjectType(), GameObjectType.fromString(gameObjectDefinition.getType()));
        assertEquals("Incorrect Field", gameObject.getDirection(), DirectionVector.parseFromString(gameObjectDefinition.getDirection()));
        assertEquals("Incorrect Field", gameObject.getInstance(), Integer.valueOf(5));
        assertEquals("Incorrect Field", gameObject.getIntendedPosition().getX(), gameObjectDefinition.getPositionX());
        assertEquals("Incorrect Field", gameObject.getIntendedPosition().getY(), gameObjectDefinition.getPositionY());
        assertEquals("Incorrect Field", gameObject.getIntendedPosition().getZ(), gameObjectDefinition.getPositionZ());
        assertEquals("Incorrect Field", gameObject.getPosition().getX(), gameObjectDefinition.getPositionX());
        assertEquals("Incorrect Field", gameObject.getPosition().getY(), gameObjectDefinition.getPositionY());
        assertEquals("Incorrect Field", gameObject.getPosition().getZ(), gameObjectDefinition.getPositionZ());
        assertTrue("Incorrect Field", gameObject.getIsDynamic());
        assertTrue("Incorrect Field", gameObject.getIsVolatile());
    }

    @Test
    public void toModelWithInvalidAIClass() throws ClassNotFoundException {
        gameObjectDefinition.setAi("Invalid");
        IGameObject gameObject = gameObjectDefinition.toModel(5);

        assertNull(gameObject);
    }
}