package com.jrq.xvgdl.context;

import com.jrq.xvgdl.context.xml.GameDefinition;
import com.jrq.xvgdl.context.xml.GameDefinitionXMLMapper;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import com.jrq.xvgdl.model.endcondition.LivesZeroGameEndCondition;
import com.jrq.xvgdl.model.event.GameEventType;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.event.SpawnItemEvent;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.objectives.IGameObjective;
import com.jrq.xvgdl.model.objectives.PlayerWinsObjective;
import com.jrq.xvgdl.model.rules.GameRule;
import com.jrq.xvgdl.model.rules.IGameRule;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class GameContextTest {

    private GameContext gameContext;
    private LivesZeroGameEndCondition endCondition;
    private IGameObjective gameObjective;
    private IGameObject gameObject;
    private IGameRule gameRule;
    private IGameEvent gameEvent;

    @Before
    public void setUp() throws Exception {
        this.gameContext = new GameContext();
        endCondition = new LivesZeroGameEndCondition();
        gameObjective = new PlayerWinsObjective();
        gameObject = new GameObject();
        gameObject.setObjectType(GameObjectType.WALL);
        gameRule = new GameRule();
        gameEvent = new SpawnItemEvent();
        gameEvent.setEventType(GameEventType.ENGINE);
    }

    @Test
    public void testContextLoaded() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        assertNotNull(gameContext.toString());
        assertNotNull(gameContext);
    }

    @Test
    public void testContextLoadedUsingPreviousContext() throws XvgdlException {
        GameContext previousGameContext = buildMockGameContext();
        this.gameContext.loadGameContext(
                previousGameContext, "/com/jrq/xvgdl/context/fullContextGameDefinition.xml");

        assertNotNull(this.gameContext.getProperty("aProperty"));
        assertEquals(this.gameContext.getProperty("aProperty"), "aPropertyValue");

        assertNotNull(this.gameContext.getGameEndConditions());
        assertEquals(2, this.gameContext.getGameEndConditions().size());
        assertTrue(this.gameContext.getGameEndConditions().contains(this.endCondition));

        assertNotNull(this.gameContext.getGameObjectives());
        assertEquals(2, this.gameContext.getGameObjectives().size());
        assertTrue(this.gameContext.getGameObjectives().contains(this.gameObjective));

        assertNotNull(this.gameContext.getObjectsAsList());
        // We expect 66 objects
        // - 1 Player (from configuration)
        // - 60 wall (from configuration)
        // - 4 ghosts (from configuration)
        // - Plus the added object in the mock test configuration
        assertEquals(66, this.gameContext.getObjectsAsList().size());
        assertTrue(this.gameContext.getObjectsAsList().contains(this.gameObject));

        assertNotNull(this.gameContext.getObjectsAsList());
        // We expect 65 objects, 60 walls and 4 ghosts (from configuration) and the added object in test
        assertEquals(4, this.gameContext.getObjectsListByType(GameObjectType.ENEMY).size());
        assertTrue(this.gameContext.getObjectsAsList().contains(this.gameObject));

        assertNotNull(this.gameContext.getGameRules());
        assertEquals(2, this.gameContext.getGameRules().size());
        assertTrue(this.gameContext.getGameRules().contains(this.gameRule));

        assertNotNull(this.gameContext.getGameEvents());
        assertEquals(2, this.gameContext.getGameEvents().size());
        assertTrue(this.gameContext.getGameEvents().contains(this.gameEvent));

    }

    @Test (expected = XvgdlException.class)
    public void testInvalidContextNotLoaded() throws Exception {
        this.gameContext.loadGameContext("invalid");
    }

    @Test
    public void testGetGamePlayers() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        assertNotNull(gameContext.getGamePlayers());
        assertEquals(1, gameContext.getGamePlayers().size());
        assertEquals(GameObjectType.PLAYER, gameContext.getGamePlayers().get(0).getObjectType());
        assertEquals(gameContext.getCurrentGamePlayer(), gameContext.getGamePlayers().get(0));
    }

    @Test
    public void testGetGameObjectsByName() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        assertNotNull(gameContext.getObjectsListByName("wall"));
        assertEquals(60, gameContext.getObjectsListByName("wall").size());
    }

    @Test
    public void testAddNullObject() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        this.gameContext.addObject(null);
    }

    @Test
    public void testGetObjecAtIncorrectPosition() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        assertNull(this.gameContext.getObjectAt(-5, -5, -5));
    }

    @Test
    public void testGetObjecAtDefaultPosition() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        assertNotNull(this.gameContext.getObjectAt(-1, -1, -1));
    }
     @Test
    public void testGetObjecAtCorrectPosition() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        assertNotNull(this.gameContext.getObjectAt(1, 1, 1));
    }

    @Test
    public void testRemoveObject() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        IGameObject aGameObject = this.gameContext.getObjectAt(1, 1, 1);

        assertTrue(this.gameContext.getObjectsAsList().contains(aGameObject));
        assertNotNull(aGameObject);

        this.gameContext.removeGameObject(aGameObject);

        assertTrue(!this.gameContext.getObjectsAsList().contains(aGameObject));
    }

    @Test
    public void testTurnsIncreased() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        Integer currentTurn = this.gameContext.getTurns();

        this.gameContext.nextTurn();

        assertEquals(currentTurn + 1, this.gameContext.getTurns());
    }

    @Test
    public void testEventRemovedWhenProcessed() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        IGameEvent aGameEvent = this.gameContext.getGameEvents().stream().findFirst().orElse(null);
        assertNotNull(aGameEvent);

        this.gameContext.eventProcessed(aGameEvent);

        assertTrue(!this.gameContext.getGameEvents().contains(aGameEvent));
    }

    @Test
    public void testEventSortedByTimeStamp() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        IGameEvent aGameEvent1 = new SpawnItemEvent();
        aGameEvent1.setTimeStamp(-System.currentTimeMillis() - 1);
        this.gameContext.addEvent(aGameEvent1);

        Collection<IGameEvent> orderedEvents = this.gameContext.getGameSortedEventsByTime();

        assertTrue(orderedEvents.stream().findFirst().get().equals(aGameEvent1));
    }

    @Test
    public void testTimePlayed() throws XvgdlException {
        this.gameContext.loadGameContext("/com/jrq/xvgdl/context/fullContextGameDefinition.xml");
        long startTime = 150L;
        long endTime = 200L;

        this.gameContext.setStartTime(startTime);
        this.gameContext.setEndTime(endTime);

        assertEquals(endTime - startTime, this.gameContext.getTimePlayed());
    }

    private GameContext buildMockGameContext() throws XvgdlException {
        GameContext mockGameContext = new GameContext();
        mockGameContext.setProperty("aProperty", "aPropertyValue");
        mockGameContext.addEndCondition(endCondition);
        mockGameContext.addObjective(gameObjective);
        mockGameContext.addObject(gameObject);
        mockGameContext.addRule(gameRule);
        mockGameContext.addEvent(gameEvent);

        return mockGameContext;
    }

}