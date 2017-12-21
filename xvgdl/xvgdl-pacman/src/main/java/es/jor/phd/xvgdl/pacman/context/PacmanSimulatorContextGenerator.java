package es.jor.phd.xvgdl.pacman.context;

import java.util.concurrent.ThreadLocalRandom;

import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.context.generator.IGameContextGenerator;
import es.jor.phd.xvgdl.model.endcondition.GameEndCondition;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import es.jor.phd.xvgdl.model.endcondition.LivesZeroGameEndCondition;
import es.jor.phd.xvgdl.model.endcondition.TimeoutGameEndCondition;
import es.jor.phd.xvgdl.model.endcondition.TurnsGameEndCondition;
import es.jor.phd.xvgdl.util.GameConstants;

public class PacmanSimulatorContextGenerator implements IGameContextGenerator {

    // Game Engine properties
    private static final long MIN_TIMEOUT = 30000;
    private static final long MAX_TIMEOUT = 300000;
    private static final int MAX_TURNS = 100;

    @Override
    public GameContext generateContext(String contextConfigFile) {

        GameContext gc = GameContext.createGameContext(contextConfigFile);

        generateContextProperties(gc);
        generateMap(gc);
        generateObjects(gc);
        generateRules(gc);
        generatePhysics(gc);
        generateRules(gc);
        generateEvents(gc);
        generateEndConditions(gc);

        return gc;
    }

    private void generateEndConditions(GameContext gc) {
        // Timeout condition
        IGameEndCondition timeoutEndCondition = new GameEndCondition();
        timeoutEndCondition.setGameEndConditionChecker(new TimeoutGameEndCondition());
        gc.addEndCondition(timeoutEndCondition);

        IGameEndCondition turnsEndCondition = new GameEndCondition();
        Integer maxTurns = ThreadLocalRandom.current().nextInt(MAX_TURNS);
        ELogger.info(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                "Generated game max number of turns for " + maxTurns + ".");
        turnsEndCondition.setGameEndConditionChecker(new TurnsGameEndCondition(maxTurns));
        gc.addEndCondition(turnsEndCondition);
    }

    private void generateMap(GameContext gc) {

    }

    private void generateEvents(GameContext gc) {

    }

    private void generatePhysics(GameContext gc) {

    }

    private void generateRules(GameContext gc) {

    }

    private void generateObjects(GameContext gc) {

    }

    private void generateContextProperties(GameContext gc) {
        Long to = ThreadLocalRandom.current().nextLong(MIN_TIMEOUT, MAX_TIMEOUT);
        gc.setTimeout(to);
        ELogger.info(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY, "Generated game timout for " + to + "ms.");
    }

}
