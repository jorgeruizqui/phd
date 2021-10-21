package com.jrq.xvgdl.pacman.experiment.genetic;

import com.jrq.xvgdl.app.XvgdlGameApp;
import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameDefinitionXMLMapper;
import com.jrq.xvgdl.context.xml.GameRuleDefinition;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import com.jrq.xvgdl.model.objectives.IGameObjective;
import com.jrq.xvgdl.model.rules.GameRule;
import com.jrq.xvgdl.model.rules.IGameRule;
import com.jrq.xvgdl.pacman.context.PacmanSimulatorContextGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
public class PacmanGeneticAlg {

    private static final int DEFAULT_POPULATION_NUMBER = 1;
    private static final int DEFAULT_DISCARDED_POPULATION_NUMBER = 5;
    private static final int DEFAULT_ITERATIONS = 100;

    private List<GameContext> currentPopulation = new ArrayList<>();
    private GameEngine gameEngine;
    private GameContext betterSolution;
    private double currentBestSolution;
    private int numberOfIterations;

    /**
     * Number of contexts actives for each generation.
     */
    private int populationNumber;

    /**
     * Number of discarded contexts for each generation.
     */
    private int numberOfDiscardedPopulation;

    private List<IGameRule> rulesDataBase;

    public PacmanGeneticAlg(int numberOfIterations, int numberOfContext) {
        this(numberOfIterations, numberOfContext, DEFAULT_DISCARDED_POPULATION_NUMBER);
    }

    public PacmanGeneticAlg(int numberOfIterations) {
        this(numberOfIterations, DEFAULT_POPULATION_NUMBER, DEFAULT_DISCARDED_POPULATION_NUMBER);
    }

    public PacmanGeneticAlg() {
        this(DEFAULT_ITERATIONS, DEFAULT_POPULATION_NUMBER, DEFAULT_DISCARDED_POPULATION_NUMBER);
    }

    public PacmanGeneticAlg(int numberOfIterations, int numberOfContext, int numberOfDiscardedElements) {
        this.numberOfIterations = numberOfIterations;
        this.populationNumber = numberOfContext;
        this.numberOfDiscardedPopulation = numberOfDiscardedElements;
    }

    public void startGeneticAlgorithm() throws XvgdlException {

        long initTime = System.currentTimeMillis();

        generateInitialPopulation();

        log.info("Starting simulation with generated base " + this.populationNumber + " Game Contexts");

        // Genetic algorithm main loop
        int iterations = 0;
        while (iterations++ < this.numberOfIterations) {
            log.debug("Starting iteration " + iterations);
            playPopulationGames();
            orderedResults();
            selectionCrossEvolutionMutation();
        }

        results();

        log.info("End of simulation with DATA: TIME " + ((System.currentTimeMillis() - initTime) / 1000d) + " seconds." +
                " ITERATIONS: " + this.numberOfIterations +
                " POPULATION: " + this.populationNumber);
    }

    private void selectionCrossEvolutionMutation() {
        discardWorstContexts();
        cross();
        evolution();
        mutation();
    }

    private void cross() {
        // The number of current population has decreased. We have to duplicate it crossing the elements
        for (int i = 0; i+1 < this.currentPopulation.size(); i+=2) {
            cross(this.currentPopulation.get(i), this.currentPopulation.get(i+1));
        }
    }

    private void cross(GameContext gameContext1, GameContext gameContext2) {
        int numberOfRules1 = gameContext1.getGameRules().size();
        int numberOfRules2 = gameContext2.getGameRules().size();

        int generatedValue1 = ThreadLocalRandom.current().nextInt(numberOfRules1);
        IGameRule randomRuleContext1 = (IGameRule) gameContext1.getGameRules().stream().toArray()[generatedValue1];
        int generatedValue2 = ThreadLocalRandom.current().nextInt(numberOfRules2);
        IGameRule randomRuleContext2 = (IGameRule) gameContext1.getGameRules().stream().toArray()[generatedValue2];

        gameContext1.getGameRules().add(randomRuleContext2);
        gameContext1.getGameRules().remove(randomRuleContext1);

        gameContext2.getGameRules().add(randomRuleContext1);
        gameContext2.getGameRules().remove(randomRuleContext2);
    }

    private void playPopulationGames() {
        this.currentPopulation.forEach(this::play);
    }

    private void orderedResults() {
        Collections.sort(this.currentPopulation, Collections.reverseOrder());
    }

    private void mutation() {
        this.currentPopulation.forEach(this::mutation);
    }

    private void evolution() {
        this.currentPopulation.forEach(this::evolution);
    }

    /**
     * Generate elements from the current list of context once the worst elements have been removed
     */
    private void createNewContexts() throws XvgdlException {
        int currentNumber = this.currentPopulation.size();
        PacmanSimulatorContextGenerator generator = new PacmanSimulatorContextGenerator();

        while (currentNumber++ < this.populationNumber) {
            this.currentPopulation.add(generator.generateContext(
                    "/context/genetic/pacmanXvgdlGenetic.xml"));
        }
    }

    private void discardWorstContexts() {
        int j = 0;
        while (j++ < this.numberOfDiscardedPopulation) {
            this.currentPopulation.remove(this.currentPopulation.size() - 1);
        }
    }

    private void results() {
        log.info("Simulation Ended after " + this.numberOfIterations + " executions. "
                + "Fitness Score: " + this.currentBestSolution
                + ". Game Context Solution: " + this.betterSolution);
    }

    private void mutation(GameContext c) {
        mutateMap(c);
        mutateRules(c);
    }

    /**
     * Evolve current population
     *
     * @param c
     */
    private void evolution(GameContext c) {
        evolveMap(c);
        evolvePlayer(c);
        evolveEndConditions(c);
        evolveRules(c);
    }

    private void evolveEndConditions(GameContext c) {
        c.getGameEndConditions().stream().forEach(this::evolveEndCondition);
    }

    private void evolveEndCondition(IGameEndCondition endCondition) {
        double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
        if (generatedValue >= 0.95d) {
            endCondition.evolution();
        }
    }

    private void evolveRules(GameContext c) {
        c.getGameRules().stream().forEach(this::evolveRule);
    }

    private void evolveRule(IGameRule gameRule) {
        double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
        if (generatedValue >= 0.95d) {
            gameRule.evolution();
        }
    }

    private void evolveMap(GameContext c) {
        int x = c.getGameMap().getSizeX();
        double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
        if (generatedValue >= 0.95d) {
            x++;
        } else if (generatedValue <= 0.05d) {
            x--;
        }

        int y = c.getGameMap().getSizeY();
        generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);
        if (generatedValue >= 0.95d) {
            y++;
        } else if (generatedValue <= 0.05d) {
            y--;
        }

        int z = c.getGameMap().getSizeZ();
        generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);
        if (generatedValue >= 0.95d) {
            z++;
        } else if (generatedValue <= 0.05d) {
            z--;
        }

        c.getGameMap().resize(x, y, z);
    }

    private void evolvePlayer(GameContext c) {
        double generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);

        int initialLives = c.getCurrentGamePlayer().getInitialLives();
        if (generatedValue <= 0.05) {
            initialLives--;
        }
        if (generatedValue >= 0.95) {
            initialLives++;
        }
        c.getCurrentGamePlayer().setInitialLives(initialLives);
    }

    private void evaluate(GameContext c) {
        double fitness = 0;

        for (IGameObjective go : this.gameEngine.getGameContext().getGameObjectives()) {
            fitness += go.checkObjective(this.gameEngine.getGameContext());
        }

        log.debug("End of simulation with score" + fitness);
        if (fitness > currentBestSolution) {
            currentBestSolution = fitness;
            betterSolution = c;
            log.debug("New best score found for this context: " + betterSolution.toString());
        }

        // Set the fitness score to the Game Context
        c.setFitnessScore(fitness);
    }

    private void play(GameContext c) {
        // Execute game simulation for this context
        try {
            this.gameEngine = XvgdlGameApp.launchGameApp(c, "/engine/simul/pacmanSimulatorEngineConfiguration.xml");
            this.gameEngine.start();
            // Evaluate the context and store its solution
            evaluate(c);
        } catch (XvgdlException e) {
            log.error("Error executing a game context.", e);
        }
    }

    private void generateInitialPopulation() throws XvgdlException {
        PacmanSimulatorContextGenerator generator = new PacmanSimulatorContextGenerator();

        for (int i = 0; i < this.populationNumber; i++) {
            // Generating context from a base XML configuration
            this.currentPopulation.add(generator.generateContext(
                    "/context/simul/pacmanSimulatorContextConfiguration.xml"));
        }

        // TODO Include the generated rules
        evolution();
        mutation();
    }

    private void mutateMap(GameContext c) {
        int x = c.getGameMap().getSizeX();
        double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
        if (generatedValue >= 0.995d) {
            x += 20;
        } else if (generatedValue <= 0.005d) {
            x -= 20;
        }

        int y = c.getGameMap().getSizeY();
        generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);
        if (generatedValue >= 0.995d) {
            y += 20;
        } else if (generatedValue <= 0.005d) {
            y -= 20;
        }

        int z = c.getGameMap().getSizeZ();
        generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);
        if (generatedValue >= 0.995d) {
            z += 20;
        } else if (generatedValue <= 0.005d) {
            z -= 20;
        }

        c.getGameMap().resize(x, y, z);
    }

    private void mutateRules(GameContext c) {

        c.getGameRules().forEach(gameRule -> {
            double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
            if (generatedValue >= 0.995d) {
                IGameRule rule = new GameRule();
                //rule.set
                c.addRule(rule);
            }
        });
    }

    private void getContextFileDataBase(int numberOfRules) {
        try {
            GameDefinitionXMLMapper mapper = new GameDefinitionXMLMapper();
            this.rulesDataBase = mapper.parse("/context/rules/gameRulesGeneratorStore.xml")
                    .getRules().stream()
                    .map(GameRuleDefinition::toModel).collect(Collectors.toList());
        } catch (XvgdlException e) {
            log.error("Error parsing the Rules Store");
            System.exit(-1);
        }
    }

}
