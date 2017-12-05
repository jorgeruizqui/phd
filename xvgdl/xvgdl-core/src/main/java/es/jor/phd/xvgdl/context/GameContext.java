package es.jor.phd.xvgdl.context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import es.indra.eplatform.context.Context;
import es.indra.eplatform.util.IOUtils;
import es.jor.phd.xvgdl.context.xml.GameContextXMLHandler;
import es.jor.phd.xvgdl.context.xml.GameRendererXMLHandler;
import es.jor.phd.xvgdl.model.actions.IGameAction;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.model.event.IGameEvent;
import es.jor.phd.xvgdl.model.map.IGameMap;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.model.physics.IGamePhysic;
import es.jor.phd.xvgdl.model.rules.IGameRule;
import es.jor.phd.xvgdl.renderer.IGameRenderer;

/**
 * Game context
 *
 * @author jrquinones
 *
 */
public final class GameContext extends Context {

    /** Renderer configuration key. */
    private static final String RENDERER_CONFIGURATION = "rendererConfiguration";

    /** Game Startup time. */
    private static final String START_TIME = "START_TIME";

    /** Timeout configuration key. Set to -1 for no timeout. */
    private static final String TIMEOUT = "TIMEOUT";

    /** RENDERER key. */
    private static final String RENDERER = "RENDERER";

    /** Players key. */
    private static final String PLAYERS = "PLAYERS";

    /** Objects key. */
    private static final String OBJECTS = "OBJECTS";

    /** Map key. */
    private static final String MAP = "MAP";

    /** Rules key. */
    private static final String RULES = "RULES";

    /** Physics key. */
    private static final String PHYSICS = "PHYSICS";

    /** Actions key. */
    private static final String ACTIONS = "ACTIONS";

    /** Events key. */
    private static final String EVENTS = "EVENTS";

    /** End Conditions key. */
    private static final String END_CONDITIONS = "END_CONDITIONS";

    /** Player AI Enabled key. */
    private static final String PLAYER_AI_ENABLED = "PLAYER_AI_ENABLED";

    /** Singleton instance. */
    private static GameContext instance;

    /**
     * Constructor.
     *
     * @param configurationFile Configuration file
     */
    private GameContext(String configurationFile) {

        // Force initialize Objects map
        Map<GameObjectType, List<IGameObject>> objectsMap = new HashMap<GameObjectType, List<IGameObject>>();
        setObjectProperty(OBJECTS, objectsMap);

        // Force initialize Rules map
        Map<String, IGameRule> rulesMap = new HashMap<String, IGameRule>();
        setObjectProperty(RULES, rulesMap);

        // Force initialize Events map
        List<IGameEvent> eventsList = new CopyOnWriteArrayList<IGameEvent>();
        setObjectProperty(EVENTS, eventsList);

        // Force initialize End Conditions map
        List<IGameEndCondition> endConditions = new CopyOnWriteArrayList<IGameEndCondition>();
        setObjectProperty(END_CONDITIONS, endConditions);

        if (IOUtils.getInputStream(configurationFile) != null) {
            GameContextXMLHandler contextHandler = new GameContextXMLHandler(this);
            contextHandler.parseResource(configurationFile);
            getMap().getMapGenerator().generateMapRepresentation(getMap(), getObjectsList());

            // Get renderer configuration and load renderer definitions:
            GameRendererXMLHandler rendererHandler = new GameRendererXMLHandler(this);
            rendererHandler.parseResource(getProperty(RENDERER_CONFIGURATION));
        }
    }

    /**
     * Creates game context.
     *
     * @param configurationFile Configuration File
     */
    public static void createGameContext(String configurationFile) {
        instance = new GameContext(configurationFile);
    }

    /**
     *
     * @return the instance of the context
     */
    public static synchronized GameContext getInstance() {
        return instance;
    }

    /**
     *
     * @return objects
     */
    public Map<GameObjectType, List<IGameObject>> getObjectsMap() {
        return (Map<GameObjectType, List<IGameObject>>) get(OBJECTS);
    }

    /**
     * @return objects
     */
    public List<IGameObject> getObjectsList() {
        return getObjectsList(null);
    }

    /**
     * @param typeFilter Filter type. Can be null to return all objects
     * @return objects
     */
    public List<IGameObject> getObjectsList(GameObjectType typeFilter) {
        List<IGameObject> rto = new ArrayList<IGameObject>();
        Map<GameObjectType, List<IGameObject>> m = (Map<GameObjectType, List<IGameObject>>) get(OBJECTS);
        for (List<IGameObject> listGameObject : m.values()) {
            rto.addAll(listGameObject);
        }

        if (typeFilter != null) {
            rto = rto.stream().filter(g -> g.getType().equals(typeFilter)).collect(Collectors.toList());
        }
        return rto;
    }

    /**
     * @return List of players.
     */
    public List<IGameObject> getGamePlayers() {
        return getObjectsList(GameObjectType.PLAYER);
    }

    /**
     * @return Current game player.
     *         TODO CHECK HOW TO HANDLE THIS
     */
    public IGameObject getCurrentGamePlayer() {
        return getObjectsList(GameObjectType.PLAYER).get(0);
    }

    /**
     * @param objectNameFilter Object Name filter. Can be null to return all
     *        objects
     * @return objects
     */
    public List<IGameObject> getObjectsListByName(String objectNameFilter) {
        List<IGameObject> rto = new ArrayList<IGameObject>();
        Map<GameObjectType, List<IGameObject>> m = (Map<GameObjectType, List<IGameObject>>) get(OBJECTS);
        for (List<IGameObject> listGameObject : m.values()) {
            rto.addAll(listGameObject);
        }

        if (objectNameFilter != null) {
            rto = rto.stream().filter(g -> g.getName().trim().equalsIgnoreCase(objectNameFilter))
                    .collect(Collectors.toList());
        }
        return rto;
    }

    /**
     *
     * @param object Object to be added
     */
    public void addObject(IGameObject object) {
        List<IGameObject> currentList = getObjectsMap().get(object.getType());
        if (currentList == null) {
            currentList = new ArrayList<IGameObject>();
        }
        currentList.add(object);
        getObjectsMap().put(object.getType(), currentList);
    }

    /**
     *
     * @return the Map
     */
    public IGameMap getMap() {
        return (IGameMap) get(MAP);
    }

    /**
     *
     * @param map Map to set
     */
    public void setMap(IGameMap map) {
        setObjectProperty(MAP, map);
    }

    /**
     *
     * @return the rules
     */
    public Map<String, IGameRule> getRules() {
        return (Map<String, IGameRule>) get(RULES);
    }

    /**
     *
     * @param rule Rule to be added
     */
    public void addRule(IGameRule rule) {
        getRules().put(rule.getRuleName(), rule);
    }

    /**
     *
     * @return the Physics
     */
    public Map<String, IGamePhysic> getPhysics() {
        return (Map<String, IGamePhysic>) get(PHYSICS);
    }

    /**
     *
     * @param physics Physics to set
     */
    public void setPhysics(Map<String, IGamePhysic> physics) {
        setObjectProperty(PHYSICS, physics);
    }

    /**
     *
     * @return the actions
     */
    public Map<String, IGameAction> getActions() {
        return (Map<String, IGameAction>) get(ACTIONS);
    }

    /**
     *
     * @param actions Actions to set
     */
    public void setActions(Map<String, IGameAction> actions) {
        setObjectProperty(ACTIONS, actions);
    }

    /**
     *
     * @param gameRenderer Game Renderer
     */
    public void setRenderer(IGameRenderer gameRenderer) {
        setObjectProperty(RENDERER, gameRenderer);
    }

    /**
     *
     * @return Game Renderer
     */
    public IGameRenderer getRenderer() {
        return (IGameRenderer) getObjectProperty(RENDERER);
    }

    /**
     *
     * @param x X Coordinate
     * @param y Y Coordinate
     * @param z Z Coordinate
     * @return Returns the object at the specified position. Null if no object
     *         present
     */
    public IGameObject getObjectAt(int x, int y, int z) {
        IGameObject rto = null;

        for (IGameObject go : getObjectsList()) {
            if (go.getX() == x && go.getY() == y && go.getZ() == 0) {
                rto = go;
                break;
            }
        }
        return rto;
    }

    /**
     * Removes object from game context.
     *
     * @param gameObject Game object to be removed.
     */
    public void removeGameObject(IGameObject gameObject) {
        getObjectsMap().get(gameObject.getType()).removeIf(s -> s.getId().equals(gameObject.getId()));
    }

    /**
     *
     * @param timeout Game Timeout in milliseconds to set
     */
    public void setTimeout(long timeout) {
        put(TIMEOUT, timeout);
    }

    /**
     *
     * @return Game Timeout
     */
    public long getTimeout() {
        return getLongValue(TIMEOUT, -1);
    }

    /**
    *
    * @param Player AI enabled to set
    */
   public void setPlayerAiEnabled(boolean playerAiEnabled) {
       put(PLAYER_AI_ENABLED, playerAiEnabled);
   }

   /**
    *
    * @return Game Timeout
    */
   public boolean isPlayerAiEnabled() {
       return getBooleanValue(PLAYER_AI_ENABLED, false);
   }
    /**
     *
     * @param startTime Game starttime in milliseconds to set
     */
    public void setStartTime(long startTime) {
        put(START_TIME, startTime);
    }

    /**
     *
     * @return Game Timeout
     */
    public long getStartTime() {
        return getLongValue(START_TIME, 0);
    }

    /**
     *
     * @param event Game Event
     */
    public void addEvent(IGameEvent event) {
        List<IGameEvent> events = (List<IGameEvent>) get(EVENTS);
        events.add(event);
    }

    /**
     *
     * @param event Game Event
     */
    public void eventProcessed(IGameEvent event) {
        List<IGameEvent> events = (List<IGameEvent>) get(EVENTS);
        events.remove(event);
    }

    /**
     *
     * @return Game Events
     */
    public List<IGameEvent> getEvents() {

        List<IGameEvent> eventListCopy = (List<IGameEvent>) get(EVENTS);
        Comparator<IGameEvent> byTimeStamp = (e1, e2) -> Long.compare(e1.getTimeStamp(), e2.getTimeStamp());
        return eventListCopy.stream().sorted(byTimeStamp).collect(Collectors.toList());
    }

    /**
     * Adds a new End Condition
     * @param endCondition End condition to add
     */
    public void addEndCondition(IGameEndCondition endCondition) {
        List<IGameEndCondition> endConditions = (List<IGameEndCondition>) get(END_CONDITIONS);
        endConditions.add(endCondition);
    }

    /**
    *
    * @return Game End Conditions
    */
   public List<IGameEndCondition> getEndConditions() {

       List<IGameEndCondition> endConditionsListCopy = (List<IGameEndCondition>) get(END_CONDITIONS);
       return endConditionsListCopy.stream().collect(Collectors.toList());
   }
}
