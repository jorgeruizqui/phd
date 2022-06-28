package com.jrq.xvgdl.pacman.experiment.optimization;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.pacman.experiment.PacmanExperimentConfiguration;
import com.jrq.xvgdl.pacman.experiment.PacmanExperimentContextBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is in charge of launching the optimization algorithm for Pacman game.
 *
 * The chromosome is composed as:
 *
 * ____________________________________________________________________________________________
 * | NUMBER OF GHOSTS | DOTS PERCENTAGE | NUMBER OF BIG DOTS | NUMBER OF LIVES | GAME TIMEOUT |
 * | int              | int             | int                | int             | int          |
 * ____________________________________________________________________________________________
 *
 * The objective function will take into account:
 * - Passed Level: boolean -> true better than false
 * - Number of items left: integer -> Minimize
 * - Number of lives left: integer -> Minimize not being zero
 * - Gamplay time: long (milliseconds) -> Minimize difference with game timeout
 */
@Slf4j
public class PacmanOptimization {

    private static final String BASE_CONFIGURATION = "/context/optimization/pacmanXvgdlOptimization.xml";
    private static final int MAP_SIZE_X = 15;
    private static final int MAP_SIZE_Y = 28;
    private static final int MAXIMUM_EVALUATIONS = 1;

    private PacmanExperimentContextBuilder pacmanExperimentContextBuilder;
    private PacmanOptimizationSolution bestSolution;
    private PacmanExperimentConfiguration bestConfiguration;
    private int bestEvaluationNumber = 0;

    /**
     * From an initial configuration, a first execution is done
     * Then, 10 neighbours are generated and played.
     * We keep the best neighbour, discarding the rest
     * Start again from the best neighbour.
     *
     * If no neighbourg is better than the current best solution, it's a local maximum
     * If a number of executions has been done, we're finished
     * Else move to another initial point to make sure a number of executions have been done.
     */
    public void configureOptimizationAndRun() {
        PacmanExperimentConfiguration configuration = PacmanExperimentConfiguration.generateInitialPoint();
        boolean finished = false;
        AtomicInteger evaluations = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();
        int numberOfJumps = 1;
        int bestJump = numberOfJumps;

        try (FileWriter fileWriter = new FileWriter("/tmp/pacman_sol_" + System.currentTimeMillis() + ".csv");) {

            while (!finished) {
                AtomicBoolean betterSolutionFoundInIteration = new AtomicBoolean(false);

                List<PacmanExperimentConfiguration> configurationAndNeighbours = configuration.generateNeighbours();

                for (PacmanExperimentConfiguration conf: configurationAndNeighbours) {
                    int currentEvaluation = evaluations.incrementAndGet();
                    StringBuffer writeLine = new StringBuffer(conf.toCsv(currentEvaluation));
                    log.info("Evaluation [" + currentEvaluation + "]: " + conf);
                    PacmanOptimizationSolution aSolution = runOptimization(
                            conf.getGamePlayTimeout(),
                            conf.getGhosts(),
                            conf.getBigDots(),
                            conf.getLives(),
                            conf.getDotsPercentage());
                    writeLine.append(aSolution.toCsv() + "\n");
                    fileWriter.write(writeLine.toString());

                    log.info("****** Solution Found: " + aSolution + " with score: " + aSolution.getScore());
                    if (aSolution.isBetterSolutionThan(bestSolution)) {
                        log.info("We have a new best solution.");
                        bestSolution = aSolution;
                        bestConfiguration = conf;
                        betterSolutionFoundInIteration.set(true);
                        bestEvaluationNumber = currentEvaluation;
                        bestJump = numberOfJumps;
                    }
                    if (evaluations.get() >= MAXIMUM_EVALUATIONS) {
                        finished = true;
                        break;
                    }
                }

                if (!finished && !betterSolutionFoundInIteration.get()) {
                    numberOfJumps++;
                    configuration = PacmanExperimentConfiguration.generateInitialPoint();
                }
            }
        } catch (IOException ex) {
            log.error("Cannot open file for writing.", ex);
        }

        logResults(evaluations.get(), startTime, bestEvaluationNumber, numberOfJumps, bestJump);
    }

    private void logResults(int evaluations, long startTime, int bestEvaluationNumber, int numberOfJumps, int bestJump) {
        log.info("****  Hill Climbing has ended with result  **** ");
        log.info("Total evaluations: " + evaluations);
        log.info("Total number of hills explored: " + numberOfJumps);
        log.info("Total execution time: " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds.");
        log.info("Best evaluation Number is : " + bestEvaluationNumber);
        log.info("Best hill number is : " + bestJump);
        log.info("********************************************");
        log.info("Best configuration is : " + bestConfiguration);
        log.info("Total gameplay time: " + bestSolution.getGamePlayTime());
        log.info("Total items not caught: " + bestSolution.getDotsPresent());
        log.info("Winning Game: " + bestSolution.isWinningGame());
        log.info("Best Solution Player Lives: " + bestSolution.getLives());
        log.info("Solution Score: " + bestSolution.getScore());
        log.info("Best evaluation Number is : " + bestEvaluationNumber);
    }

    public PacmanOptimizationSolution runOptimization(int timeout, int numberOfGhosts, int numberOfBigDots, int numberOfLives, int percentageOfDots) {
        this.pacmanExperimentContextBuilder = PacmanExperimentContextBuilder.builder()
                .mapSizeX(MAP_SIZE_X)
                .mapSizeY(MAP_SIZE_Y)
                .numberOfBigDots(numberOfBigDots)
                .numberOfGhosts(numberOfGhosts)
                .numberOfLives(numberOfLives)
                .percentageOfDots(percentageOfDots)
                .timeout(timeout)
                .build();

        GameContext gameContext = pacmanExperimentContextBuilder.buildContext();

        try {
            GameEngine gameEngine = GameEngine.createGameEngine(gameContext, BASE_CONFIGURATION);
            gameEngine.start();
            return PacmanOptimizationSolution.buildFromContext(gameEngine.getGameContext());
        } catch (XvgdlException ex) {
            log.error("Error executing game: " + ex.getMessage(), ex);
        }
        return null;
    }
}
