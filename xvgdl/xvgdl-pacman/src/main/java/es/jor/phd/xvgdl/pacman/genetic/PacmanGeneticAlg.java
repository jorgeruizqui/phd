package es.jor.phd.xvgdl.pacman.genetic;

import java.util.concurrent.ThreadLocalRandom;

import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.app.GameApp;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.engine.GameEngine;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.model.objectives.IGameObjective;
import es.jor.phd.xvgdl.model.rules.IGameRule;
import es.jor.phd.xvgdl.pacman.context.PacmanSimulatorContextGenerator;
import es.jor.phd.xvgdl.util.GameConstants;

public class PacmanGeneticAlg {

    private static final String GAME_SIMULATION_CATEGORY = "GAME_SIMULATION";
	private GameContext currentGameContext;
    private GameEngine currentGameEngine;

    private GameContext betterSolution;
    private double currentBestSolution;

    private int numberOfIterations;

    public PacmanGeneticAlg(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public void startSimulation( ) {

    	// Generate base context from configuration
        generateBaseContext();

        // Force a first evolution of the context
        evolution();

        ELogger.info(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY, "Starting simulation with a generated base context:" + this.currentGameContext.toString());

        // Genetic algorithm main loop
        int iterations = 0;
        while (iterations++ < this.numberOfIterations) {
            ELogger.debug(this, GAME_SIMULATION_CATEGORY, "Starting iteration " + iterations);
            play();
            evaluate();
            evolution();
            mutation();
        }

        results();
    }

    private void results() {
        ELogger.info(this, "GAME_SIMULATION_RESULT",
                "Simulation Ended after " + this.numberOfIterations + " executions. "
                + "Fitness Score: " + this.currentBestSolution
                + ". Game Context Solution: " + this.betterSolution);
    }

    private void mutation() {

    }

    private void evolution() {
    	evolveMap();
    	evolvePlayer();
    	evolveEndConditions();
    	evolveRules();
    }

    private void evolveEndConditions() {
    	this.currentGameContext.getEndConditions().stream().forEach(this::evolveEndCondition);
    }

    private void evolveEndCondition(IGameEndCondition endCondition) {
		double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
    	if (generatedValue >= 0.95d) {
    		endCondition.evolution();
    	}
    }

    private void evolveRules() {
    	this.currentGameContext.getGameRules().stream().forEach(this::evolveRule);
    }

    private void evolveRule(IGameRule gameRule) {
    	double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
    	if (generatedValue >= 0.95d) {
    		gameRule.evolution();
    	}
    }

    private void evolveMap() {
    	int x = this.currentGameContext.getGameMap().getSizeX();
		double generatedValue = ThreadLocalRandom.current().nextDouble(1.0d);
    	if (generatedValue >= 0.95d) {
    		x++;
    	} else if (generatedValue <= 0.05d) {
    		x--;
    	}

		int y = this.currentGameContext.getGameMap().getSizeY();
		generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);
    	if (generatedValue >= 0.95d) {
    		y++;
    	} else if (generatedValue <= 0.05d) {
    		y--;
    	}

    	int z = this.currentGameContext.getGameMap().getSizeZ();
		generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);
    	if (generatedValue >= 0.95d) {
    		z++;
    	} else if (generatedValue <= 0.05d) {
    		z--;
    	}

    	this.currentGameContext.getGameMap().resize(x, y, z);
    }

	private void evolvePlayer() {
		double generatedValue = ThreadLocalRandom.current().nextDouble(1.1d);

		int initialLives = this.currentGameContext.getCurrentGamePlayer().getInitialLives();
		if (generatedValue <= 0.05) {
			initialLives--;
		}
		if (generatedValue >= 0.95) {
			initialLives++;
		}
		this.currentGameContext.getCurrentGamePlayer().setInitialLives(initialLives);
	}

    private void evaluate() {
        double fitness = 0;

        for (IGameObjective go : this.currentGameEngine.getGameContext().getGameObjectives()) {
        	fitness += go.checkObjective(this.currentGameEngine.getGameContext());
        }

        ELogger.debug(this, GAME_SIMULATION_CATEGORY, "End of simulation with score" + fitness);
        if (fitness > currentBestSolution) {
            currentBestSolution = fitness;
            betterSolution = currentGameContext;
            ELogger.debug(this, GAME_SIMULATION_CATEGORY, "New best score found!!");
        }
    }

    private void play() {
        this.currentGameEngine = GameApp.launchGameApp(this.currentGameContext, "/engine/simul/pacmanSimulatorEngineConfiguration.xml");
        this.currentGameEngine.start();
    }

    private void generateBaseContext() {
        PacmanSimulatorContextGenerator generator = new PacmanSimulatorContextGenerator();

        // Generating context from a base XML configuration
        this.currentGameContext = generator.generateContext(
                "/context/simul/pacmanSimulatorContextConfiguration.xml");

    }
}
