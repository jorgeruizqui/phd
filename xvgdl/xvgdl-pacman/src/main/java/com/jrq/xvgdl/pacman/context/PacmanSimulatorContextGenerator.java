package com.jrq.xvgdl.pacman.context;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.generator.IGameContextGenerator;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import com.jrq.xvgdl.model.endcondition.TimeoutGameEndCondition;
import com.jrq.xvgdl.model.endcondition.TurnsGameEndCondition;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class PacmanSimulatorContextGenerator implements IGameContextGenerator {

    // Game Engine properties
    private static final long MIN_TIMEOUT = 30000;
    private static final long MAX_TIMEOUT = 300000;
    private static final int MAX_TURNS = 100;

    @Override
    public GameContext generateContext(String contextConfigFile) throws XvgdlException {

        GameContext gc = new GameContext();
        gc.loadGameContext(contextConfigFile);

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
        IGameEndCondition timeoutEndCondition = new TimeoutGameEndCondition();
        gc.addEndCondition(timeoutEndCondition);

        Integer maxTurns = ThreadLocalRandom.current().nextInt(MAX_TURNS);
        IGameEndCondition turnsEndCondition = new TurnsGameEndCondition(maxTurns);
        log.info("Generated game max number of turns for " + maxTurns + ".");
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
        log.info("Generated game timout for " + to + "ms.");
    }

}
