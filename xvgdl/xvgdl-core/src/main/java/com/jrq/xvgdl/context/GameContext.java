package com.jrq.xvgdl.context;

import com.jrq.xvgdl.context.xml.GameDefinition;
import com.jrq.xvgdl.context.xml.GameDefinitionXMLMapper;
import com.jrq.xvgdl.exception.XvgdlException;
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
import com.jrq.xvgdl.util.GameBaseProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Game context
 *
 * @author jrquinones
 */
@NoArgsConstructor
@Getter
@ToString
@Slf4j
public final class GameContext implements Comparable<GameContext> {

    private GameDefinition gameDefinition = new GameDefinition();

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
     * Game Context Fitness function score.
     */
    @Setter
    private Double fitnessScore = 0.0d;

    public void loadGameContext(String configurationFile) throws XvgdlException {
        loadGameContext(null, configurationFile);
    }

    /**
     *
     * @param configurationFile Configuration file
     */
    public void loadGameContext(GameContext gc, String configurationFile) throws XvgdlException {

        if (configurationFile != null) {
            try {

                GameDefinitionXMLMapper parser = new GameDefinitionXMLMapper();
                this.gameDefinition = parser.parse(configurationFile);

                rollup();

            } catch (Exception e) {
                log.error("Game context configuration error with: " + configurationFile);
                throw new XvgdlException("Error loading Game Context: " + e.getMessage(), e);
            }
        }

        if (gc != null) {
            this.addProperties(gc.getGameDefinition().getProperties());
            this.addGameEndConditions(gc.getGameEndConditions());
            this.addEvents(gc.getGameEvents());
            this.addRules(gc.getGameRules());
            this.addObjects(gc.getObjectsAsList());
            this.addGameObjectives(gc.getGameObjectives());
        }

    }

    private void rollup() throws XvgdlException {

        // Game Map
        this.gameMap = gameDefinition.getMap().toModel();

        // Objects
        gameDefinition.getObjects().forEach(o -> IntStream.range(0, o.getInstances()).forEach(
                i -> addObject(o.toModel(i))));

        // Players
        gameDefinition.getPlayers().forEach(p -> addObject(p.toModel()));

        // Generate map and add objecs and players
        this.gameMap.generateMap(this);

        // Rules
        gameDefinition.getRules().forEach(r -> addRule(r.toModel()));

        // Events
        gameDefinition.getEvents().forEach(e -> addEvent(e.toModel()));

        // End Conditions
        gameDefinition.getEndConditions().forEach(ec -> addEndCondition(ec.toModel()));

        // Objectives
        gameDefinition.getObjectives().forEach(o -> addObjective(o.toModel()));
    }

    private void addProperties(GameBaseProperties properties) {
        this.gameDefinition.getProperties().putAll(properties);
    }

    public String getProperty(String key) {
        return this.gameDefinition.getProperties().getProperty(key);
    }

    public void setProperty(String key, String value) {
        this.gameDefinition.getProperties().setProperty(key, value);
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
        if (object != null) {
            List<IGameObject> currentList = getObjectsMap().get(object.getObjectType());
            if (currentList == null) {
                currentList = new ArrayList<>();
            }
            currentList.add(object);
            getObjectsMap().put(object.getObjectType(), currentList);
        } else {
            log.error("Error trying to add a null game object to game map. Check object definition");
        }
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
    public IGameObject getObjectAt(Integer x, Integer y, Integer z) {

        List<IGameObject> listByPosition = getObjectsAsList().stream().filter(
                go -> go.getX().equals(x) &&
                        go.getY().equals(y) &&
                        go.getZ().equals(z)).collect(Collectors.toList());

        return !listByPosition.isEmpty() ? listByPosition.get(0) : null;
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
        this.gameEvents.addAll(events);
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
    public Collection<IGameEvent> getGameSortedEventsByTime() {
        Comparator<IGameEvent> byTimeStamp = Comparator.comparingLong(IGameEvent::getTimeStamp);
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
     * @param go Game Objective
     */
    public void addObjective(IGameObjective go) {
        this.gameObjectives.add(go);
    }

    /**
     * @param gameObjectives Game Objectives
     */
    public void addGameObjectives(Collection<IGameObjective> gameObjectives) {
        this.gameObjectives.addAll(gameObjectives);
    }
    /**
     * @return The total time played
     */
    public long getTimePlayed() {
        return getEndTime() != null ? getEndTime() - getStartTime() : System.currentTimeMillis() - getStartTime();
    }

    // TODO Create a fitness function comparator
    @Override
    public int compareTo(GameContext o) {
        return getFitnessScore().compareTo(o.getFitnessScore());
    }

}
