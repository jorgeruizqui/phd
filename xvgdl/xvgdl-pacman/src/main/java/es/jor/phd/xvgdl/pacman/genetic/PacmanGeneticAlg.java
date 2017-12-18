package es.jor.phd.xvgdl.pacman.genetic;

import java.util.List;

import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.app.GameApp;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.engine.GameEngine;
import es.jor.phd.xvgdl.pacman.context.PacmanSimulatorContextGenerator;

public class PacmanGeneticAlg {
    
    private GameContext currentGameContext;
    private GameEngine currentGameEngine;
    
    private GameContext betterSolution;
    private double currentBestSolution;
    
    private int numberOfIterations;
    
    public PacmanGeneticAlg(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }
    
    public void startSimulation( ) {
        
        generateBaseContext();
        
        // Genetic algorithm main loop
        int iterations = 0;
        while (iterations++ < this.numberOfIterations) {
            ELogger.debug(this, "GAME_SIMULATION", "Starting iteration " + iterations);
            play();
            evaluate();
            evolution();
            mutation();
        }
        
        results();
    }

    private void results() {
        ELogger.info(this, "GAME_SIMULATION_RESULT", 
                "Simulation Ended after " + this.numberOfIterations + " executions."
                + "Fitness Score: " + this.currentBestSolution
                + "Game Context Solution: " + this.betterSolution);
    }

    private void mutation() {
        
    }

    private void evolution() {
        
    }

    private void evaluate() {
        double fitness = 0;
        
        // Just determine number of turns executed
        fitness = 0.8d * this.currentGameEngine.getTurns();
        
        ELogger.debug(this, "GAME_SIMULATION", "End of simulation with score" + fitness);
        if (fitness > currentBestSolution) {
            currentBestSolution = fitness;
            betterSolution = currentGameContext;
            ELogger.debug(this, "GAME_SIMULATION", "New best score found!!");
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
