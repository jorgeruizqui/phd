package com.jrq.xvgdl.pacman.experiment.geneticprogramming;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.generator.utils.RuleDefinitionGeneratorUtils;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.rules.IGameRule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class PacmanSteadyContextGeneticProgramming {

    public static final int POPULATION_SIZE = 3;
    public static final int MAX_EVALUATIONS = 15;
    public static final String CONFIGURATION_FILE = "/context/geneticprogramming/pacmanXvgdlGeneticProgramming.xml";
    private static final int MAX_NUMBER_GENERATED_RULES = 6;

    private List<IGameRule> rulesDataStore = new ArrayList<>();
    private List<GameContext> population = new ArrayList<>();
    private Map<String, PacmanGeneticSolution> solutions = new HashMap<>();
    private Random random = new Random();

    private PacmanGeneticSolution bestSolution;
    private PacmanGeneticSolution worstSolution;
    //private GameContext worstIndividual;
    private int bestEvaluationNumber = 0;
    private int numberOfEvaluations = 0;
    private long startTime;

    public void configureGeneticAlgorithmAndRun() {

        generateGameRulesDataStore();

        startTime = System.currentTimeMillis();

        generateInitialPopulation();

        playPopulation();

        while (numberOfEvaluations < MAX_EVALUATIONS) {
            GameContext newIndividual = generateNewPopulationSteadyContextApproach();
            playIndividual(newIndividual);
        }
        logResults();
    }

    private void playPopulation() {
        population.forEach(this::playIndividual);
    }

    private void playIndividual(GameContext gameContext) {
        PacmanGeneticSolution aSolution = runIndividual(gameContext);
        if (aSolution != null) {
            solutions.put(gameContext.getId(), aSolution);

            if (bestSolution == null || aSolution.getScore() > bestSolution.getScore()) {
                log.info("****** New best Solution Found: " + aSolution + " with score: " + aSolution.getScore());
                bestSolution = aSolution;
                bestEvaluationNumber = numberOfEvaluations;
            } else {
                log.info("****** Solution Found: " + aSolution + " with score: " + aSolution.getScore());
            }

            if (worstSolution == null || aSolution.getScore() < worstSolution.getScore()) {
                log.info("****** New worst Solution Found: " + aSolution + " with score: " + aSolution.getScore());
                worstSolution = aSolution;
                //worstIndividual = gameContext;
            }
        }
    }

    private GameContext generateNewPopulationSteadyContextApproach() {
        List<PacmanGeneticSolution> orderedSolutions = solutions.values().stream().sorted().collect(Collectors.toList());

        PacmanGeneticSolution worstIndividual = orderedSolutions.get(0);
        population.remove(worstIndividual.getInitialContext());
        solutions.remove(worstIndividual.getInitialContext().getId());

        Pair<PacmanGeneticSolution, PacmanGeneticSolution> parents = selection();
        List<IGameRule> rulesCrossed = cross(parents.getLeft(), parents.getRight());
        List<IGameRule> rulesMutated = mutation(rulesCrossed);

        // Add the new member
        GameContext newIndividual = generateIndividual(rulesMutated);
        population.add(newIndividual);
        return newIndividual;
    }

    private List<IGameRule> mutation(List<IGameRule> rulesCrossed) {
        double prob = 1.0d / MAX_NUMBER_GENERATED_RULES;
        for (int i = 0; i < rulesCrossed.size(); i++) {
            double randomValue = random.nextDouble();
            if (randomValue < prob) {
                int newRuleIndex = random.nextInt(rulesDataStore.size());
                rulesCrossed.remove(i);
                rulesCrossed.add(rulesDataStore.get(newRuleIndex));
                break;
            }
        }
        return rulesCrossed;
    }

    private List<IGameRule> cross(PacmanGeneticSolution parent1, PacmanGeneticSolution parent2) {
        List<IGameRule> crossedRules = new ArrayList<>();
        List<IGameRule> parent1Rules = parent1.getInitialContext().getGameRules().stream().filter(r -> !r.getFixed())
            .collect(
                Collectors.toList());
        List<IGameRule> parent2Rules = parent2.getInitialContext().getGameRules().stream().filter(r -> !r.getFixed())
            .collect(
                Collectors.toList());

        int nRules = random.nextInt(MAX_NUMBER_GENERATED_RULES);
        nRules = nRules > 1 ? nRules : 2;

        Set<Integer> setRulesFromParent1 = new HashSet<>();
        Set<Integer> setRulesFromParent2 = new HashSet<>();

        while (crossedRules.size() < nRules &&
            (!parent1Rules.isEmpty() || !parent2Rules.isEmpty())) {

            // Select from parent 1:
            boolean selected = false;
            while (!selected) {
                if (parent1Rules.size() > 0) {
                    if (parent1Rules.size() == 1) {
                        crossedRules.add(parent1Rules.get(0));
                        parent1Rules.remove(0);
                        selected = true;
                    } else {
                        int intRuleParent1 = random.nextInt(parent1Rules.size());
                        if (!setRulesFromParent1.contains(intRuleParent1)) {
                            crossedRules.add(parent1Rules.get(intRuleParent1));
                            setRulesFromParent1.add(intRuleParent1);
                            parent1Rules.remove(intRuleParent1);
                            selected = true;
                        }
                    }
                } else {
                    selected = true;
                }
            }
            // Select from parent 2:
            selected = false;
            while (!selected && crossedRules.size() < nRules) {
                if (parent2Rules.size() > 0) {
                    if (parent2Rules.size() == 1) {
                        crossedRules.add(parent2Rules.get(0));
                        parent2Rules.remove(0);
                        selected = true;
                    } else {
                        int intRuleParent2 = random.nextInt(parent2Rules.size());
                        if (!setRulesFromParent2.contains(intRuleParent2)) {
                            crossedRules.add(parent2Rules.get(intRuleParent2));
                            setRulesFromParent2.add(intRuleParent2);
                            parent2Rules.remove(intRuleParent2);
                            selected = true;
                        }
                    }
                } else {
                    selected = true;
                }
            }
        }
        return crossedRules;
    }

    private Pair<PacmanGeneticSolution, PacmanGeneticSolution> selection() {

        List<String> keysAsArray = new ArrayList<>(solutions.keySet());
        final String key1 = keysAsArray.get(random.nextInt(keysAsArray.size()));
        String key2 = keysAsArray.get(random.nextInt(keysAsArray.size()));
        while (key1.equals(key2)) {
            key2 = keysAsArray.get(random.nextInt(keysAsArray.size()));
        }

        PacmanGeneticSolution parent1 = solutions.get(key1);
        PacmanGeneticSolution parent2 = solutions.get(key2);

        return Pair.of(parent1, parent2);
    }

    private void generateGameRulesDataStore() {
        RuleDefinitionGeneratorUtils.generateGameDefinition(1000).getRules().forEach(gameRuleDefinition -> {
            this.rulesDataStore.add(gameRuleDefinition.toModel());
        });
    }

    private void generateInitialPopulation() {
        IntStream.range(0, POPULATION_SIZE).forEach(
            i -> {
                GameContext gameContext = new GameContext();
                try {
                    gameContext.loadGameContext(CONFIGURATION_FILE);
                    addDynamicRules(gameContext);

                    log.info(String.format("Generated context with the following rules: %s",
                        gameContext.getGameRules().stream().filter(gameRule -> !gameRule.getFixed())
                            .map(Objects::toString).collect(
                            Collectors.toList())));

                    allocatePlayerRandomly(gameContext);
                    population.add(gameContext);
                } catch (XvgdlException e) {
                    log.error("Error loading game context.");
                }
            }
        );
    }

    private GameContext generateIndividual(List<IGameRule> rules) {

        GameContext gameContext = new GameContext();
        try {
            gameContext.loadGameContext(CONFIGURATION_FILE);
            gameContext.addRules(rules);

            allocatePlayerRandomly(gameContext);
        } catch (XvgdlException e) {
            log.error("Error loading game context.");
        }
        return gameContext;
    }

    private void allocatePlayerRandomly(GameContext gameContext) {
        IGameObject player = gameContext.getGamePlayers().get(0);
        boolean located = false;
        while (!located) {
            int x = (new Random()).nextInt(gameContext.getGameMap().getSizeX());
            int y = (new Random()).nextInt(gameContext.getGameMap().getSizeY());

            IGameObject objectAt = gameContext.getObjectAt(x, y, 0);

            if (objectAt == null
                || (!GameObjectType.ENEMY.equals(objectAt.getObjectType())
                && !GameObjectType.WALL.equals(objectAt.getObjectType()))) {
                player.setInitialPosition(x, y, 0);
                player.setPosition(x, y, 0);
                located = true;
            }
        }
    }

    private void addDynamicRules(GameContext gameContext) {
        int numberOfDynamicRules = (new Random()).nextInt(MAX_NUMBER_GENERATED_RULES);
        if (numberOfDynamicRules == 0) {
            numberOfDynamicRules = 1;
        }

        IntStream.range(0, numberOfDynamicRules).forEach(i ->
            gameContext.addRule(this.rulesDataStore.get((new Random()).nextInt(this.rulesDataStore.size())))
        );
    }

    private void logResults() {
        log.info("****  Genetic Programming Generational Approach has ended with result  **** ");
        log.info("Total evaluations: " + numberOfEvaluations);
        log.info("Total execution time: " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds.");
        log.info("Best evaluation Number is : " + bestEvaluationNumber);
        log.info("********************************************");
        log.info("Best rule set is : " + bestSolution.getInitialContext().getGameRules());
        log.info("Total gameplay time: " + bestSolution.getGamePlayTime());
        log.info("Total items not caught: " + bestSolution.getDotsPresent());
        log.info("Winning Game: " + bestSolution.isWinningGame());
        log.info("Best Solution Player Lives: " + bestSolution.getLives());
        log.info("Solution Score: " + bestSolution.getScore());
    }

    public PacmanGeneticSolution runIndividual(GameContext gameContext) {

        try {
            GameEngine gameEngine = GameEngine.createGameEngine(gameContext, null);
            gameEngine.start();
            this.numberOfEvaluations++;
            return PacmanGeneticSolution.buildFromContext(gameContext, gameEngine.getGameContext());
        } catch (XvgdlException ex) {
            log.error("Error executing game: " + ex.getMessage(), ex);
        }
        return null;
    }
}
