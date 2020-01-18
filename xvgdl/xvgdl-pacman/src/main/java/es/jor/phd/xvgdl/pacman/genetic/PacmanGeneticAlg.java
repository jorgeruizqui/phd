package es.jor.phd.xvgdl.pacman.genetic;

import es.jor.phd.xvgdl.app.GameApp;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.engine.GameEngine;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.model.objectives.IGameObjective;
import es.jor.phd.xvgdl.model.rules.GameRule;
import es.jor.phd.xvgdl.model.rules.IGameRule;
import es.jor.phd.xvgdl.pacman.context.PacmanSimulatorContextGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class PacmanGeneticAlg {

    private static final String GAME_SIMULATION_CATEGORY = "GAME_GENETIC_ALGORITHM_SIMULATION";

	private static final int DEFAULT_POPULATION_NUMBER = 1;

	private static final int DEFAULT_DISCARDED_POPULATION_NUMBER = 5;

	private static final int DEFAULT_ITERATIONS = 100;

	private List<GameContext> currentGameContexts = new ArrayList<>();
    private GameEngine currentGameEngine;

    private GameContext betterSolution;
    private double currentBestSolution;

    private int numberOfIterations;

    /** Number of contexts actives for each generation. */
    private int numberOfCurrentContext;
    /** Number of discarded contexts for each generation. */
    private int numberOfDiscardedContext;

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
        this.numberOfCurrentContext = numberOfContext;
        this.numberOfDiscardedContext = numberOfDiscardedElements;
    }

    public void startSimulation() {

    	long initTime = System.currentTimeMillis();

    	// Generate base context from configuration
    	// It will allow to generate NC context with a first evolution
        loadBaseContexts();

        // Force a first evolution of the contexts
        this.currentGameContexts.forEach(this::evolution);

        log.info("Starting simulation with generated base " + this.numberOfCurrentContext + " Game Contexts");

        // Genetic algorithm main loop
        int iterations = 0;
        while (iterations++ < this.numberOfIterations) {
            log.debug("Starting iteration " + iterations);
            this.currentGameContexts.forEach(this::play);
            // Order the results, bigger scores first
            Collections.sort(this.currentGameContexts, Collections.reverseOrder());

            // Discard worst population and generate new contexts
            discardWorstContexts();
            createNewContexts();

            this.currentGameContexts.forEach(this::evolution);
            this.currentGameContexts.forEach(this::mutation);
        }

        results();

        log.info("End of simulation with DATA: TIME " + ((System.currentTimeMillis() - initTime) / 1000d) + " seconds." +
         " ITERATIONS: " + this.numberOfIterations +
         " POPULATION: " + this.numberOfCurrentContext);
    }

    /**
     * Generate elements from the current list of context once the worst elements have been removed
     */
    private void createNewContexts() {
    	int currentNumber = this.currentGameContexts.size();
        PacmanSimulatorContextGenerator generator = new PacmanSimulatorContextGenerator();

    	while (currentNumber++ < this.numberOfCurrentContext) {
    		this.currentGameContexts.add(generator.generateContext(
	                "/context/simul/pacmanSimulatorContextConfiguration.xml"));
    	}
	}

	private void discardWorstContexts() {
		int j = 0;
		while (j++ < this.numberOfDiscardedContext) {
			this.currentGameContexts.remove(this.currentGameContexts.size() - 1);
		}
	}

	private void results() {
        log.info("Simulation Ended after " + this.numberOfIterations + " executions. "
                + "Fitness Score: " + this.currentBestSolution
                + ". Game Context Solution: " + this.betterSolution);
    }

    private void mutation(GameContext c) {
    	mutateMap(c);
    }

    /**
     * Evolve current population
     * @param c
     */
    private void evolution(GameContext c) {
    	evolveMap(c);
    	evolvePlayer(c);
    	evolveEndConditions(c);
    	evolveRules(c);
    }

    private void evolveEndConditions(GameContext c) {
    	c.getEndConditions().stream().forEach(this::evolveEndCondition);
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

        for (IGameObjective go : this.currentGameEngine.getGameContext().getGameObjectives()) {
        	fitness += go.checkObjective(this.currentGameEngine.getGameContext());
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
        this.currentGameEngine = GameApp.launchGameApp(c, "/engine/simul/pacmanSimulatorEngineConfiguration.xml");
        this.currentGameEngine.start();

        // Evaluate the context and store its solution
        evaluate(c);
    }

    private void loadBaseContexts() {
        PacmanSimulatorContextGenerator generator = new PacmanSimulatorContextGenerator();

        for (int i = 0; i < this.numberOfCurrentContext; i++){
	        // Generating context from a base XML configuration
	        this.currentGameContexts.add(generator.generateContext(
	                "/context/simul/pacmanSimulatorContextConfiguration.xml"));
        }

    }

    private void mutateMap(GameContext c) {
    	int x = c.getGameMap().getSizeX();
		double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
    	if (generatedValue >= 0.995d) {
    		x+=20;
    	} else if (generatedValue <= 0.005d) {
    		x-=20;
    	}

		int y = c.getGameMap().getSizeY();
		generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);
    	if (generatedValue >= 0.995d) {
    		y+=20;
    	} else if (generatedValue <= 0.005d) {
    		y-=20;
    	}

    	int z = c.getGameMap().getSizeZ();
		generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);
    	if (generatedValue >= 0.995d) {
    		z+=20;
    	} else if (generatedValue <= 0.005d) {
    		z-=20;
    	}

    	c.getGameMap().resize(x, y, z);
    }

    private void mutateRules(GameContext c) {
		double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
    	if (generatedValue >= 0.995d) {
    		IGameRule rule = new GameRule();
    		//rule.set
			c.addRule(rule);
    	}
    }
}
