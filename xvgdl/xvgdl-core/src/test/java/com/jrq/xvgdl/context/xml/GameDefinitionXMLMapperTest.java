package com.jrq.xvgdl.context.xml;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GameDefinitionXMLMapperTest {

    @Test
    public void mapXmlEmptyGameDefinition() {
        GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();

        GameDefinition cg = parser.parse("/com/jrq/xvgdl/context/xml/emptyGameDefinition.xml");

        assertNotNull(cg);
    }

    @Test
    public void mapXmlGamePropertiesDefinition() {
        GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();

        GameDefinition cg = parser.parse("/com/jrq/xvgdl/context/xml/propertiesGameDefinition.xml");

        assertNotNull(cg);
        assertNotNull(cg.getProperties());
        assertEquals("renderer_config.xml", cg.getProperties().get("rendererConfiguration"));
        assertEquals("20000", cg.getProperties().get("timeout"));
    }

    @Test
    public void mapXmlGameMapDefinition() {
        GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();

        GameDefinition cg = parser.parse("/com/jrq/xvgdl/context/xml/mapGameDefinition.xml");

        assertNotNull(cg);
        assertNotNull(cg.getMap());
        assertEquals("Error checking generator", "RandomLocationGameMapGenerator", cg.getMap().getGenerator());
        assertEquals("Error checking size X", 20, cg.getMap().getSizeX().longValue());
        assertEquals("Error checking size Y", 40, cg.getMap().getSizeY().longValue());
        assertEquals("Error checking size Z", 60, cg.getMap().getSizeZ().longValue());
        assertEquals("Error checking type", "2D", cg.getMap().getType());
        assertEquals("Error checking file", "MapFile", cg.getMap().getFile());
        assertEquals("Error checking toroidal", true, cg.getMap().getToroidal());
    }

    @Test
    public void mapXmlGamePlayersDefinition() {
        GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();

        GameDefinition cg = parser.parse("/com/jrq/xvgdl/context/xml/playersGameDefinition.xml");

        assertNotNull(cg);
        assertNotNull(cg.getPlayers());
        assertEquals("Error number of players", 2, cg.getPlayers().size());

        /*
        Player 1
         <player name="player1" score="100" lives="3" livePercentage="100" initialLives="5"
             ai="PlayerChaseNearestItemAI" />
         */
        assertEquals("Error in name", "player1", cg.getPlayers().get(0).getName());
        assertEquals("Error in score", 100.0, cg.getPlayers().get(0).getScore(), 0.1d);
        assertEquals("Error in live percentage", 100, cg.getPlayers().get(0).getLivePercentage().longValue());
        assertEquals("Error in initial lives", 5, cg.getPlayers().get(0).getInitialLives().longValue());
        assertEquals("Error in lives", 3, cg.getPlayers().get(0).getLives().longValue());
        assertEquals("Error in ai", "PlayerChaseNearestItemAI", cg.getPlayers().get(0).getAi());

        /*
        Player 2
        <player name="player2" score="200" lives="5" livePercentage="50" initialLives="6"
                ai="PlayerChaseNearestEnemyAI" />
         */
        assertEquals("Error in name", "player2", cg.getPlayers().get(1).getName());
        assertEquals("Error in score", 200.0, cg.getPlayers().get(1).getScore(), 0.1d);
        assertEquals("Error in live percentage", 50, cg.getPlayers().get(1).getLivePercentage().longValue());
        assertEquals("Error in initial lives", 6, cg.getPlayers().get(1).getInitialLives().longValue());
        assertEquals("Error in lives", 5, cg.getPlayers().get(1).getLives().longValue());
        assertEquals("Error in ai", "PlayerChaseNearestEnemyAI", cg.getPlayers().get(1).getAi());

    }

    @Test
    public void mapXmlGameObjectsDefinition() {
        GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();

        GameDefinition cg = parser.parse("/com/jrq/xvgdl/context/xml/objectsGameDefinition.xml");

        assertNotNull(cg);
        assertNotNull(cg.getObjects());
        assertEquals("Error number of objects", 2, cg.getObjects().size());

        /*
        <object name="wall" type="wall" dynamic="false" volatile="false"
                sizeX="1" sizeY="1" instances="60" />
        <object name="ghost" type="enemy" dynamic="true"
                volatile="true" sizeX="1" sizeY="1" instances="4"
                ai="PlayerChaserAI" />
         */
        assertEquals("Error in name", "wall", cg.getObjects().get(0).getName());
        assertNull("Error in ai", cg.getObjects().get(0).getAi());
        assertEquals("Error in type", "wall", cg.getObjects().get(0).getType());
        assertEquals("Error in size X", 1, cg.getObjects().get(0).getSizeX().intValue());
        assertEquals("Error in size Y", 1, cg.getObjects().get(0).getSizeY().intValue());
        assertNull("Error in null Z", cg.getObjects().get(0).getSizeZ());

        assertEquals("Error in name", "ghost", cg.getObjects().get(1).getName());
        assertEquals("Error in ai", "PlayerChaserAI", cg.getObjects().get(1).getAi());
        assertEquals("Error in type", "enemy", cg.getObjects().get(1).getType());
        assertEquals("Error in size X", 1, cg.getObjects().get(1).getSizeX().intValue());
        assertEquals("Error in size Y", 1, cg.getObjects().get(1).getSizeY().intValue());
        assertNull("Error in null Z", cg.getObjects().get(1).getSizeZ());
        assertEquals("Error in is volatile", true, cg.getObjects().get(1).getIsVolatile());
        assertEquals("Error in is dynamic", true, cg.getObjects().get(1).getIsDynamic());
        assertEquals("Error in instances", 4, cg.getObjects().get(1).getInstances().intValue());
    }

    @Test
    public void mapXmlGameEventsDefinition() {
        GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();

        GameDefinition cg = parser.parse("/com/jrq/xvgdl/context/xml/eventsGameDefinition.xml");

        assertNotNull(cg);
        assertNotNull(cg.getEvents());
        assertEquals("Error number of events", 1, cg.getEvents().size());

        /*
        <event type="engine" className="SpawnItemEvent"
               objectName="cherry" timer="5000" value="1000.5"/>
         */
        assertEquals("Error in type", "engine", cg.getEvents().get(0).getType());
        assertEquals("Error in class name", "SpawnItemEvent", cg.getEvents().get(0).getClassName());
        assertEquals("Error in object name", "cherry", cg.getEvents().get(0).getObjectName());
        assertEquals("Error in timer", 5000, cg.getEvents().get(0).getTimer().intValue());
        assertNull("Error in key code", cg.getEvents().get(0).getKeyCode());
        assertEquals("Error in value", 1000.5, cg.getEvents().get(0).getValue(), 0.5);

    }

    @Test
    public void mapXmlGameRulesDefinition() {
        GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();

        GameDefinition cg = parser.parse("/com/jrq/xvgdl/context/xml/rulesGameDefinition.xml");

        assertNotNull(cg);
        assertNotNull(cg.getRules());
        assertEquals("Error number of rules", 1, cg.getRules().size());

        /*
            <rules>
                <rule name="eatBigDot" type="collision">
                    <ruleActions>
                        <ruleAction objectName="pacman" result="scoreUp" value="300" />
                        <ruleAction objectName="bigDot" result="disappear" />
                    </ruleActions>
                </rule>
            </rules>
         */
        assertEquals("Error in name", "eatBigDot", cg.getRules().get(0).getName());
        assertEquals("Error in type", "collision", cg.getRules().get(0).getType());
        assertNotNull("Error null rule actions", cg.getRules().get(0).getRuleActions());
        assertEquals("Error rule actions number", 2, cg.getRules().get(0).getRuleActions().size());
        assertEquals("Error rule action name", "pacman", cg.getRules().get(0).getRuleActions().get(0).getObjectName());
        assertEquals("Error rule action result", "scoreUp", cg.getRules().get(0).getRuleActions().get(0).getResult());
        assertEquals("Error rule action value", "300", cg.getRules().get(0).getRuleActions().get(0).getValue());
        assertEquals("Error rule action name", "bigDot", cg.getRules().get(0).getRuleActions().get(1).getObjectName());
        assertEquals("Error rule action result", "disappear", cg.getRules().get(0).getRuleActions().get(1).getResult());
        assertNull("Error rule action value", cg.getRules().get(0).getRuleActions().get(1).getValue());
    }

    @Test
    public void mapXmlGameEndConditionsDefinition() {
        GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();

        GameDefinition cg = parser.parse("/com/jrq/xvgdl/context/xml/endConditionsGameDefinition.xml");

        assertNotNull(cg);
        assertNotNull(cg.getEndConditions());
        assertEquals("Error number of end conditions", 2, cg.getEndConditions().size());

        /*
        <endConditions>
            <endCondition checkerClass="TimeoutGameEndCondition"
                          property="timeout" value="0" />
            <endCondition checkerClass="NoObjectsPresentGameEndCondition"
                          objectNames="bigDot,smallDot" winningCondition="true"/>
        </endConditions>
         */
        assertEquals("Error in checkerClass", "TimeoutGameEndCondition", cg.getEndConditions().get(0).getCheckerClass());
        assertEquals("Error in property", "timeout", cg.getEndConditions().get(0).getProperty());
        assertEquals("Error in value", "0", cg.getEndConditions().get(0).getValue());
        assertEquals("Error in checkerClass", "NoObjectsPresentGameEndCondition", cg.getEndConditions().get(1).getCheckerClass());
        assertEquals("Error in object names", "bigDot,smallDot", cg.getEndConditions().get(1).getObjectNames());
        assertEquals("Error in winning condition", true, cg.getEndConditions().get(1).getWinningCondition());
    }
}