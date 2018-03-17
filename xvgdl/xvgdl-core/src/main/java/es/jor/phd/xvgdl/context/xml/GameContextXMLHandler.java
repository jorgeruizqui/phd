package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.log.ELogger;
import es.indra.eplatform.util.xml.BasicXMLHandler;
import es.indra.eplatform.util.xml.IgnorableXMLElementParser;
import es.indra.eplatform.util.xml.XMLException;
import es.indra.eplatform.util.xml.XMLObjectParser;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.model.event.IGameEvent;
import es.jor.phd.xvgdl.model.map.IGameMap;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.model.objectives.IGameObjective;
import es.jor.phd.xvgdl.model.rules.IGameRule;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * XML Handler for Context configuration
 *
 * @author jrquinones
 *
 */
public class GameContextXMLHandler extends BasicXMLHandler {

    /** MAP tag. */
    private static final String XMLTAG_GAME_DEFINITION = "gameDefinition";
    /** Objects tag. */
    private static final String XMLTAG_OBJECTS = "objects";
    /** Players tag. */
    private static final String XMLTAG_PLAYERS = "players";
    /** Avatars tag. */
    private static final String XMLTAG_AVATARS = "avatars";
    /** Physics tag. */
    private static final String XMLTAG_PHYSICS = "physics";
    /** Events tag. */
    private static final String XMLTAG_EVENTS = "events";
    /** Rules tag. */
    private static final String XMLTAG_RULES = "rules";
    /** End conditions tag. */
    private static final String XMLTAG_END_CONDITIONS = "endConditions";
    /** Objectives tag. */
    private static final String XMLTAG_OBJECTIVES = "objectives";

    /** Instance of game context. */
    private GameContext gameContext;

    /**
     * Constructor.
     *
     * @param gameContext Game Context instance
     */
    public GameContextXMLHandler(GameContext gameContext) {
        super();
        this.gameContext = gameContext;

        // Ignoring tags:
        this.register(new IgnorableXMLElementParser(XMLTAG_GAME_DEFINITION));
        this.register(new IgnorableXMLElementParser(XMLTAG_OBJECTS));
        this.register(new IgnorableXMLElementParser(XMLTAG_PLAYERS));
        this.register(new IgnorableXMLElementParser(XMLTAG_AVATARS));
        this.register(new IgnorableXMLElementParser(XMLTAG_PHYSICS));
        this.register(new IgnorableXMLElementParser(XMLTAG_EVENTS));
        this.register(new IgnorableXMLElementParser(XMLTAG_RULES));
        this.register(new IgnorableXMLElementParser(XMLTAG_END_CONDITIONS));
        this.register(new IgnorableXMLElementParser(XMLTAG_OBJECTIVES));

        // Parsing tags:
        this.register(new Properties.PropertyXMLElement());
        this.register(new XMLObjectParser(GameMapDefinition.XMLTAG, GameMapDefinition.class));
        this.register(new XMLObjectParser(GameObjectDefinition.XMLTAG, GameObjectDefinition.class));
        this.register(new XMLObjectParser(GamePlayerDefinition.XMLTAG, GamePlayerDefinition.class));
        this.register(new XMLObjectParser(GameRuleDefinition.XMLTAG, GameRuleDefinition.class));
        this.register(new XMLObjectParser(GameRuleActionDefinition.XMLTAG, GameRuleActionDefinition.class));
        this.register(new XMLObjectParser(GameEventDefinition.XMLTAG, GameEventDefinition.class));
        this.register(new XMLObjectParser(GameEndConditionDefinition.XMLTAG, GameEndConditionDefinition.class));
        this.register(new XMLObjectParser(GameObjectiveDefinition.XMLTAG, GameObjectiveDefinition.class));
    }

    @Override
    public void parseResource(String resource) {

        try {
            super.parseResource(resource);
        } catch (XMLException e) {
            ELogger.error(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY, "XML Exception parsing context resource file.", e);
        }
    }

    @Override
    public void onElementFinished(String xmlTag, Object obj) {

        if (xmlTag.equals(Properties.XMLTAG_PROPERTY)) {
            String key = obj.toString().substring(0, obj.toString().indexOf(':'));
            String value = obj.toString().substring(obj.toString().indexOf(':') + 1);
            ELogger.debug(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY, "Added property: " + key + " - " + value);
            gameContext.put(key, value);

        } else if (xmlTag.equals(GameMapDefinition.XMLTAG)) {
            IGameMap gameMap = GameMapDefinition.convert((GameMapDefinition) obj);
            ELogger.debug(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY, "Created Game Map: " + gameMap.toString());
            gameContext.setGameMap(gameMap);

        } else if (xmlTag.equals(GameObjectDefinition.XMLTAG)) {
            GameObjectDefinition objectDefinition = (GameObjectDefinition) obj;

            // Create the instances of object
            for (int i = 0; i < objectDefinition.getIntegerValue(GameObjectDefinition.XMLATTR_INSTANCES, 1); i++) {
                IGameObject gameObject = GameObjectDefinition.convert(objectDefinition, i);
                ELogger.debug(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                        "Created Object: " + gameObject.toString());
                gameContext.addObject(gameObject);
            }

        } else if (xmlTag.equals(GamePlayerDefinition.XMLTAG)) {
            GamePlayerDefinition objectDefinition = (GamePlayerDefinition) obj;

            // Create the instances of object
            IGameObject gameObject = GamePlayerDefinition.convert(objectDefinition);
            ELogger.debug(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY, "Created Player: " + gameObject.toString());
            gameContext.addObject(gameObject);

        } else if (xmlTag.equals(GameRuleDefinition.XMLTAG)) {
            GameRuleDefinition ruleDefinition = (GameRuleDefinition) obj;
            IGameRule gameRule = ruleDefinition.convert(ruleDefinition);
            gameContext.addRule(gameRule);

        } else if (xmlTag.equals(GameRuleActionDefinition.XMLTAG)) {
            GameRuleActionDefinition ruleActionDefinition = (GameRuleActionDefinition) obj;

            if (getParent() instanceof GameRuleDefinition) {
                GameRuleDefinition parentRuleDefinition = (GameRuleDefinition) getParent();
                parentRuleDefinition.addActionDefinition(ruleActionDefinition);
            }

        } else if (xmlTag.equals(GameEventDefinition.XMLTAG)) {
            GameEventDefinition eventDefinition = (GameEventDefinition) obj;
            IGameEvent event = GameEventDefinition.convert(eventDefinition);
            gameContext.addEvent(event);

        } else if (xmlTag.equals(GameEndConditionDefinition.XMLTAG)) {
            GameEndConditionDefinition endConditionDefinition = (GameEndConditionDefinition) obj;
            IGameEndCondition endCondition = GameEndConditionDefinition.convert(endConditionDefinition);
            gameContext.addEndCondition(endCondition);

        } else if (xmlTag.equals(GameObjectiveDefinition.XMLTAG)) {
        	GameObjectiveDefinition gameObjectiveDefinition = (GameObjectiveDefinition) obj;
            IGameObjective gameObjective = GameObjectiveDefinition.convert(gameObjectiveDefinition);
            gameContext.addGameObjective(gameObjective);
        }
    }
}
