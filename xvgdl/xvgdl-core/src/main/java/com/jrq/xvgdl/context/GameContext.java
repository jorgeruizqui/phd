package com.jrq.xvgdl.context;

import com.jrq.xvgdl.context.xml.GameContextXMLHandler;
import com.jrq.xvgdl.context.xml.GameElementBaseDefinition;
import com.jrq.xvgdl.context.xml.GameRendererXMLHandler;
import com.jrq.xvgdl.model.actions.IGameAction;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.map.IGameMap;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.GamePlayer;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.objectives.IGameObjective;
import com.jrq.xvgdl.model.physics.IGamePhysic;
import com.jrq.xvgdl.model.rules.IGameRule;
import com.jrq.xvgdl.renderer.IGameRenderer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Game context
 *
 * @author jrquinones
 */
@Getter
@ToString
@Slf4j
public final class GameContext extends GameElementBaseDefinition implements Comparable<GameContext> {

    /**
     * Renderer configuration key.
     */
    private static final String RENDERER_CONFIGURATION = "rendererConfiguration";

    /**
     * Objects map.
     */
    private EnumMap<GameObjectType, List<IGameObject>> objectsMap = new EnumMap<>(GameObjectType.class);

    /**
     * Game Events.
     */
    private Collection<IGameEvent> gameEvents = new CopyOnWriteArrayList<>();

    /**
     * Game End Conditions.
     */
    private Collection<IGameEndCondition> gameEndConditions = new CopyOnWriteArrayList<>();

    /**
     * Game Action.
     */
    private Collection<IGameAction> gameActions = new CopyOnWriteArrayList<>();

    /**
     * Game Map.
     */
    @Setter
    private IGameMap gameMap;

    /**
     * Game Rules.
     */
    private Collection<IGameRule> gameRules = new CopyOnWriteArrayList<>();

    /**
     * Game Objectives.
     */
    private Collection<IGameObjective> gameObjectives = new CopyOnWriteArrayList<>();

    /**
     * Game Startup time.
     */
    @Setter
    private Long startTime;

    /**
     * Game End time.
     */
    @Setter
    private Long endTime;

    /**
     * Timeout configuration key. Set to -1 for no timeout.
     */
    @Setter
    private Long timeout = -1L;

    /**
     * Game Renderer.
     */
    @Setter
    private IGameRenderer gameRenderer;

    /**
     * Physics key.
     */
    private Collection<IGamePhysic> gamePhysics = new CopyOnWriteArrayList<>();

    /**
     * Player AI Enabled.
     */
    @Setter
    private boolean playerAiEnabled = false;

    /**
     * Game Paused flag.
     */
    @Setter
    private boolean gamePaused = false;

    /**
     * Game Turns.
     */
    private int turns = 0;

    /**
     * Singleton instance.
     */
    private static GameContext instance;

    /**
     * Game Context Fitness function score.
     */
    @Setter
    private Double fitnessScore = 0.0d;

    @Getter
    private Map<Object, Object> objectProperties = new HashMap<>();

    public GameContext() {
        // Empty constructor to be called by XML Mapper
    }

    /**
     * Constructor.
     *
     * @param configurationFile Configuration file
     */
    private GameContext(GameContext gc, String configurationFile) {

        if (configurationFile != null) {
            try (InputStream f = new FileInputStream(configurationFile)) {

                // Read and parse configuration file. This will create all
                // elements
                // needed
                GameContextXMLHandler contextHandler = new GameContextXMLHandler(this);
                contextHandler.parseResource(configurationFile);

                // Generate map position for all elements
                this.gameMap.generateMap(this);

                // Get renderer configuration and load renderer definitions:
                GameRendererXMLHandler rendererHandler = new GameRendererXMLHandler(this);
                rendererHandler.parseResource(getProperty(RENDERER_CONFIGURATION));
            } catch (IOException e) {
                log.error("Game context configuration file not found: " + configurationFile);
            }
        }

        if (gc != null) {
            this.addObjectProperties(gc.entrySet());
            this.addGameEndConditions(gc.getEndConditions());
            this.addEvents(gc.getGameEvents());
            this.addRules(gc.getGameRules());
            this.addObjects(gc.getObjectsAsList());
        }

    }

    private void addObjectProperties(Set<Map.Entry<Object, Object>> entrySet) {
        entrySet.forEach(entry -> this.objectProperties.put(entry.getKey(), entry.getValue()));
    }

    /**
     * Creates game context.
     *
     * @param configurationFile Configuration File
     */
    public static GameContext createGameContext(String configurationFile) {
        instance = new GameContext(null, configurationFile);
        return instance;
    }

    /**
     * Creates game context.
     *
     * @param configurationFile Configuration File
     */
    public static void createGameContext(GameContext gc, String configurationFile) {
        instance = new GameContext(gc, configurationFile);
    }

    /**
     * Creates an empty game context.
     */
    public static void createEmptyGameContext() {
        instance = new GameContext(null, null);
    }

    /**
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
     * @param typeFilter Filter type. Can be null to return all objects
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
     * moment, just one player is available for simulations
     */
    public GamePlayer getCurrentGamePlayer() {
        return (GamePlayer) getObjectsListByType(GameObjectType.PLAYER).get(0);
    }

    /**
     * @param objectNameFilter Object Name filter. Can be null to return all objects
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
     * @param object Object to be added
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
     * @param objects Objects to be added
     */
    public void addObjects(Collection<IGameObject> objects) {
        objects.stream().forEach(this::addObject);
    }

    /**
     * @param rule Rule to be added
     */
    public void addRule(IGameRule rule) {
        getGameRules().add(rule);
    }

    /**
     * @param rules Rules to be added
     */
    public void addRules(Collection<IGameRule> rules) {
        getGameRules().addAll(rules);
    }

    /**
     * @param x X Coordinate
     * @param y Y Coordinate
     * @param z Z Coordinate
     * @return Returns the object at the specified position. Null if no object
     * present
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
     * @param gameObject Game object to be removed.
     */
    public void removeGameObject(IGameObject gameObject) {
        getObjectsMap().get(gameObject.getObjectType()).removeIf(s -> s.getId().equals(gameObject.getId()));
    }

    /**
     * Update to next turn
     */
    public void nextTurn() {
        this.turns++;
    }

    /**
     * @param event Game Event
     */
    public void addEvent(IGameEvent event) {
        this.gameEvents.add(event);
    }

    /**
     * @param events Game Event collections
     */
    public void addEvents(Collection<IGameEvent> events) {
        events.addAll(events);
    }

    /**
     * @param event Game Event
     */
    public void eventProcessed(IGameEvent event) {
        this.gameEvents.remove(event);
    }

    /**
     * @return Game Events sorted by timestamp
     */
    public Collection<IGameEvent> getGameSortedEvents() {
        Comparator<IGameEvent> byTimeStamp = (e1, e2) -> Long.compare(e1.getTimeStamp(), e2.getTimeStamp());
        return this.gameEvents.stream().sorted(byTimeStamp).collect(Collectors.toList());
    }

    /**
     * Adds a new End Condition
     *
     * @param endCondition End condition to add
     */
    public void addEndCondition(IGameEndCondition endCondition) {
        this.gameEndConditions.add(endCondition);
    }

    /**
     * Adds a collection of End Conditions
     *
     * @param gameEndConditions List of End conditions to add
     */
    public void addGameEndConditions(Collection<IGameEndCondition> gameEndConditions) {
        this.gameEndConditions.addAll(gameEndConditions);
    }

    /**
     * @return Game End Conditions
     */
    public Collection<IGameEndCondition> getEndConditions() {
        return this.gameEndConditions;
    }

    /**
     * @return Game Objectives
     */
    public Collection<IGameObjective> getGameObjectives() {
        return this.gameObjectives;
    }

    /**
     * @param go Game Objective
     */
    public void addGameObjective(IGameObjective go) {
        this.gameObjectives.add(go);
    }

    /**
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
