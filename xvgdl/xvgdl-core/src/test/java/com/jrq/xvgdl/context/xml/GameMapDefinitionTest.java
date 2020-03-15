package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.map.GameMapType;
import com.jrq.xvgdl.model.map.IGameMap;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class GameMapDefinitionTest {

    private GameMapDefinition gameMapDefinition;

    @Before
    public void setUp() {
        gameMapDefinition = new GameMapDefinition();
        gameMapDefinition.setFile("file");
        gameMapDefinition.setGenerator("com.jrq.xvgdl.model.map.FileBasedGameMapGenerator");
        gameMapDefinition.setSizeX(1);
        gameMapDefinition.setSizeY(2);
        gameMapDefinition.setSizeZ(3);
        gameMapDefinition.setToroidal(true);
        gameMapDefinition.setType("2D");
    }

    @Test
    public void toModelWithCorrectDefinition() {
        IGameMap gameMap = gameMapDefinition.toModel();

        assertNotNull(gameMap);
        assertEquals("Error in field", gameMap.getMapFile(), gameMapDefinition.getFile());
        assertEquals("Error in field", gameMap.getSizeX(), gameMapDefinition.getSizeX());
        assertEquals("Error in field", gameMap.getSizeY(), gameMapDefinition.getSizeY());
        assertEquals("Error in field", gameMap.getSizeZ(), gameMapDefinition.getSizeZ());
        assertEquals("Error in field", gameMap.getIsToroidal(), gameMapDefinition.getToroidal());
        assertEquals("Error in field", gameMap.getMapType(), GameMapType.fromString(gameMapDefinition.getType()));
    }

    @Test
    public void toModelWithIncorrectDefinition() {
        gameMapDefinition.setGenerator("Invalid");
        IGameMap gameMap = gameMapDefinition.toModel();

        assertNull(gameMap);
    }
}