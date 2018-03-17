package es.jor.phd.xvgdl.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import es.indra.eplatform.context.Context;
import es.indra.eplatform.util.IOUtils;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.xml.GameContextXMLHandler;
import es.jor.phd.xvgdl.context.xml.GameRendererXMLHandler;
import es.jor.phd.xvgdl.model.actions.IGameAction;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.model.event.IGameEvent;
import es.jor.phd.xvgdl.model.map.IGameMap;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.GamePlayer;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.model.objectives.IGameObjective;
import es.jor.phd.xvgdl.model.physics.IGamePhysic;
import es.jor.phd.xvgdl.model.rules.IGameRule;
import es.jor.phd.xvgdl.renderer.IGameRenderer;
import es.jor.phd.xvgdl.util.GameConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Game context
 *
 * @author jrquinones
 *
 */
@Getter
@ToString
public final class GameContext extends Context implements Comparable<GameContext> {

    /** Renderer configuration key. */
    private static final String RENDERER_CONFIGURATION = "rendererConfiguration";

    /** Objects map. */
    private EnumMap<GameObjectType, List<IGameObject>> objectsMap = new EnumMap<>(GameObjectType.class);

    /** Game Events. */
    private Collection<IGameEvent> gameEvents = new CopyOnWriteArrayList<>();

    /** Game End Conditions. */
    private Collection<IGameEndCondition> gameEndConditions = new CopyOnWriteArrayList<>();

    /** Game Action. */
    private Collection<IGameAction> gameActions = new CopyOnWriteArrayList<>();

    /** Game Map. */
    @Setter
    private IGameMap gameMap;

    /** Game Rules. */
    private Collection<IGameRule> gameRules = new CopyOnWriteArrayList<>();

    /** Game Objectives. */
    private Collection<IGameObjective> gameObjectives = new CopyOnWriteArrayList<>();

    /** Game Startup time. */
    @Setter
    private Long startTime;

    /** Game End time. */
    @Setter
    private Long endTime;

    /** Timeout configuration key. Set to -1 for no timeout. */
    @Setter
    private Long timeout = -1L;

    /** Game Renderer. */
    @Setter
    private IGameRenderer gameRenderer;

    /** Physics key. */
    private Collection<IGamePhysic> gamePhysics = new CopyOnWriteArrayList<>();

    /** Player AI Enabled. */
    @Setter
    private boolean playerAiEnabled = false;

    /** Game Paused flag. */
    @Setter
    private boolean gamePaused = false;

    /** Game Turns. */
    private int turns = 0;

    /** Singleton instance. */
    private static GameContext instance;

    /** Game Context Fitness function score. */
    @Setter
    private Double fitnessScore = 0.0d;

    /**
     * Constructor.
     *
     * @param configurationFile
     *        Configuration file
     */
    private GameContext(GameContext gc, String configurationFile) {

        if (configurationFile != null) {
            try (InputStream f = IOUtils.getInputStream(configurationFile)) {

                // Read and parse configuration file. This will create all
                // elements
                // needed
                GameContextXMLHandler contextHandler = new GameContextXMLHandler(this);
                contextHandler.parseResource(configurationFile);

                // Generate map position for all elements
                this.gameMap.getMapGenerator().generateMapRepresentation(this.gameMap, getObjectsAsList());

                // Get renderer configuration and load renderer definitions:
                GameRendererXMLHandler rendererHandler = new GameRendererXMLHandler(this);
                rendererHandler.parseResource(getProperty(RENDERER_CONFIGURATION));
            } catch (IOException e) {
                ELogger.error(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                        "Game context configuration file not found: " + configurationFile);
            }
        }

        if (gc != null) {
            this.addObjectProperties(gc.entrySet());
            this.addEndConditions(gc.getEndConditions());
            this.addEvents(gc.getGameEvents());
            this.addRules(gc.getGameRules());
            this.addObjects(gc.getObjectsAsList());
        }

    }

    /**
     * Creates game context.
     *
     * @param configurationFile
     *        Configuration File
     */
    public static GameContext createGameContext(String configurationFile) {
        instance = new GameContext(null, configurationFile);
        return instance;
    }

    /**
     * Creates game context.
     *
     * @param configurationFile
     *        Configuration File
     */
    public static void createGameContext(GameContext gc, String configurationFile) {
        instance = new GameContext(gc, configurationFile);
    }

    /**
     * Creates game context.
     *
     * @param configurationFile
     *        Configuration File
     */
    public static void createEmptyGameContext() {
        instance = new GameContext(null, null);
    }

    /**
     *
     * @return the instance of the context
     */
    public static synchronized GameContext getInstance() {
        return instance;
    }

    /**
     * @return objects
     */
    public List<IGameObject> getObjectsAsList() {
        return getObjectsListByType(null);
    }

    /**
     * @param typeFilter
     *        Filter type. Can be null to return all objects
     * @return objects
     */
    public List<IGameObject> getObjectsListByType(GameObjectType typeFilter) {
        List<IGameObject> rto = new ArrayList<>();

        for (List<IGameObject> listGameObject : objectsMap.values()) {
            rto.addAll(listGameObject);
        }

        if (typeFilter != null) {
            rto = rto.stream().filter(g -> g.getObjectType().equals(typeFilter)).collect(Collectors.toList());
        }
        return rto;
    }

    /**
     * @return List of players.
     */
    public List<IGameObject> getGamePlayers() {
        return getObjectsListByType(GameObjectType.PLAYER);
    }

    /**
     * @return Current game player. TODO CHECK HOW TO HANDLE THIS. At the
     *         moment, just one player is available for simulations
     */
    public GamePlayer getCurrentGamePlayer() {
        return (GamePlayer) getObjectsListByType(GameObjectType.PLAYER).get(0);
    }

    /**
     * @param objectNameFilter
     *        Object Name filter. Can be null to return all objects
     * @return objects
     */
    public List<IGameObject> getObjectsListByName(String objectNameFilter) {
        List<IGameObject> rto = new ArrayList<>();

        for (List<IGameObject> listGameObject : this.objectsMap.values()) {
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
     * @param object
     *        Object to be added
     */
    public void addObject(IGameObject object) {
        List<IGameObject> currentList = getObjectsMap().get(object.getObjectType());
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(object);
        getObjectsMap().put(object.getObjectType(), currentList);
    }

    /**
     *
     * @param object
     *        Object to be added
     */
    public void addObjects(Collection<IGameObject> objects) {
        objects.stream().forEach(this::addObject);
    }

    /**
     *
     * @param rule
     *        Rule to be added
     */
    public void addRule(IGameRule rule) {
        getGameRules().add(rule);
    }

    /**
     *
     * @param rules
     *        Rules to be added
     */
    public void addRules(Collection<IGameRule> rules) {
        getGameRules().addAll(rules);
    }

    /**
     *
     * @param x
     *        X Coordinate
     * @param y
     *        Y Coordinate
     * @param z
     *        Z Coordinate
     * @return Returns the object at the specified position. Null if no object
     *         present
     */
    public IGameObject getObjectAt(int x, int y, int z) {
        IGameObject rto = null;

        for (IGameObject go : getObjectsAsList()) {
            if (go.getX() == x && go.getY() == y && go.getZ() == z) {
                rto = go;
                break;
            }
        }
        return rto;
    }

    /**
     * Removes object from game context.
     *
     * @param gameObject
     *        Game object to be removed.
     */
    public void removeGameObject(IGameObject gameObject) {
        getObjectsMap().get(gameObject.getObjectType()).removeIf(s -> s.getId().equals(gameObject.getId()));
    }

    /**
     *
     * @param turns update to next turn
     */
    public void nextTurn() {
        this.turns++;
    }

    /**
     *
     * @param event Game Event
     */
    public void addEvent(IGameEvent event) {
        this.gameEvents.add(event);
    }

    /**
     *
     * @param events
     *        Game Event collections
     */
    public void addEvents(Collection<IGameEvent> events) {
        events.addAll(events);
    }

    /**
     *
     * @param event
     *        Game Event
     */
    public void eventProcessed(IGameEvent event) {
        this.gameEvents.remove(event);
    }

    /**
     *
     * @return Game Events sorted by timestamp
     */
    public Collection<IGameEvent> getGameSortedEvents() {
        Comparator<IGameEvent> byTimeStamp = (e1, e2) -> Long.compare(e1.getTimeStamp(), e2.getTimeStamp());
        return this.gameEvents.stream().sorted(byTimeStamp).collect(Collectors.toList());
    }

    /**
     * Adds a new End Condition
     *
     * @param endCondition
     *        End condition to add
     */
    public void addEndCondition(IGameEndCondition endCondition) {
        this.gameEndConditions.add(endCondition);
    }

    /**
     * Adds a collection of End Conditions
     *
     * @param endConditions
     *        List of End conditions to add
     */
    public void addEndConditions(Collection<IGameEndCondition> endConditions) {
        endConditions.addAll(endConditions);
    }

    /**
     *
     * @return Game End Conditions
     */
    public Collection<IGameEndCondition> getEndConditions() {
        return this.gameEndConditions;
    }

    /**
     *
     * @return Game Objectives
     */
    public Collection<IGameObjective> getGameObjectives() {
        return this.gameObjectives;
    }

    /**
     *
     * @param go Game Objective
     */
    public void addGameObjective(IGameObjective go) {
        this.gameObjectives.add(go);
    }

    /**
     *
     * @return The total time played
     */
    public long getTimePlayed() {
        return getEndTime() != null ? getEndTime() - getStartTime() : System.currentTimeMillis() - getStartTime();
    }

    @Override
    public int compareTo(GameContext o) {
        return getFitnessScore().compareTo(o.getFitnessScore());
    }

}
