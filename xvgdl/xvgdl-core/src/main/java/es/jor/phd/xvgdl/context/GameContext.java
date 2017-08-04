package es.jor.phd.xvgdl.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.indra.eplatform.context.Context;
import es.indra.eplatform.util.IOUtils;
import es.jor.phd.xvgdl.context.xml.GameContextXMLHandler;
import es.jor.phd.xvgdl.model.actions.IGameAction;
import es.jor.phd.xvgdl.model.map.IGameMap;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.model.physics.IGamePhysic;
import es.jor.phd.xvgdl.model.rules.IGameRule;

/**
 * Game context
 * @author jrquinones
 *
 *   List of all objects
 *   Map<String, List<IGameObject>> objects;
 *
 *   Game map. Will be initialize when map will be generated/loaded.
 *   IGameMap map;
 *
 *   Game Rules.
 *   Map<String, IGameRule> rules;
 *
 *   Game Physics.
 *   Map<String, IGamePhysic> physics;
 *
 *   Game Actions.
 *   private Map<String, IGameAction> actions;
 */
public final class GameContext extends Context {

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

    /** Singleton instance. */
    private static GameContext instance;

    /**
     * Constructor.
     * @param configurationFile Configuration file
     */
    private GameContext(String configurationFile) {

        // Force initialize Objects map
        Map<GameObjectType, List<IGameObject>> objectsMap = new HashMap<GameObjectType, List<IGameObject>>();
        setObjectProperty(OBJECTS, objectsMap);

        if (IOUtils.getInputStream(configurationFile) != null) {
            GameContextXMLHandler handler = new GameContextXMLHandler(this);
            handler.parseResource(configurationFile);
        }
    }

    /**
     * Creates game context.
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
    public Map<GameObjectType, List<IGameObject>> getObjects() {
        return (Map<GameObjectType, List<IGameObject>>) get(OBJECTS);
    }

    /**
     *
     * @param object Object to be added
     */
    public void addObject(IGameObject object) {
        List<IGameObject> currentList = getObjects().get(object.getType());
        if (currentList == null) {
            currentList = new ArrayList<IGameObject>();
        }
        currentList.add(object);
        getObjects().put(object.getType(), currentList);
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
     * @param rules Rules to set
     */
    public void setRules(Map<String, IGameRule> rules) {
        setObjectProperty(RULES, rules);
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


}
