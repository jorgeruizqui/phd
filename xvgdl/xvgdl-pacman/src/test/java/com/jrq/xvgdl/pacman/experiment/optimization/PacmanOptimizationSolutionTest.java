package com.jrq.xvgdl.pacman.experiment.optimization;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.GamePlayer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PacmanOptimizationSolutionTest {

    private static final long START_TIME = 10000L;
    private static final long END_TIME = 15000L;
    private static final long CONFIGURED_TIMEOUT = 20000L;

    @Before
    public void setUp() {
    }

    @Test
    public void testBuildFromEngine() {
        GameContext mockGameContext = mockGameContext();
        PacmanOptimizationSolution pacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(mockGameContext);

        Assert.assertEquals(END_TIME - START_TIME, pacmanOptimizationSolution.getGamePlayTime());
        Assert.assertEquals(0, pacmanOptimizationSolution.getDotsPresent());
        Assert.assertEquals(1, pacmanOptimizationSolution.getLives());
        Assert.assertTrue(pacmanOptimizationSolution.isWinningGame());
        Assert.assertEquals(50000L, pacmanOptimizationSolution.getConfiguredTimeout());
    }

    @Test
    public void checkWinningGameIsGreaterThanNotWinningGame() {
        GameContext mockGameContext = mockGameContext();
        mockGameContext.setWinningGame(true);
        GameContext anotherGameContext = mockGameContext();
        anotherGameContext.setWinningGame(false);

        PacmanOptimizationSolution pacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(mockGameContext);
        PacmanOptimizationSolution anotherPacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(anotherGameContext);

        Assert.assertEquals(1, pacmanOptimizationSolution.compareTo(anotherPacmanOptimizationSolution));
    }

    @Test
    public void checkLessDotsPresentIsGreaterThanMoreDotsPresents() {
        GameContext mockGameContext = mockGameContext();
        GameContext anotherGameContext = mockGameContext();
        GameObject dot1 = getGameItem();
        GameObject dot2 = getGameItem();
        GameObject dot3 = getGameItem();
        mockGameContext.addObject(dot1);
        anotherGameContext.addObject(dot2);
        anotherGameContext.addObject(dot3);

        PacmanOptimizationSolution pacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(mockGameContext);
        PacmanOptimizationSolution anotherPacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(anotherGameContext);

        Assert.assertEquals(1, pacmanOptimizationSolution.compareTo(anotherPacmanOptimizationSolution));
    }

    @Test
    public void checkLessLivesIsGreaterThanMoreLives() {
        GameContext mockGameContext = mockGameContext();
        mockGameContext.getCurrentGamePlayer().setLives(1);
        GameContext anotherGameContext = mockGameContext();
        anotherGameContext.getCurrentGamePlayer().setLives(2);

        PacmanOptimizationSolution pacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(mockGameContext);
        PacmanOptimizationSolution anotherPacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(anotherGameContext);

        Assert.assertEquals(1, pacmanOptimizationSolution.compareTo(anotherPacmanOptimizationSolution));
    }

    @Test
    public void checkTheLessDifferenceBetweenPlaytimeAndConfiguredTimeoutIsGreater() {
        GameContext mockGameContext = mockGameContext();
        mockGameContext.setStartTime(START_TIME);
        mockGameContext.setEndTime(END_TIME + 1000);
        mockGameContext.setTimeout(CONFIGURED_TIMEOUT);

        GameContext anotherGameContext = mockGameContext();
        anotherGameContext.setStartTime(START_TIME);
        anotherGameContext.setEndTime(END_TIME);
        anotherGameContext.setTimeout(CONFIGURED_TIMEOUT);

        PacmanOptimizationSolution pacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(mockGameContext);
        PacmanOptimizationSolution anotherPacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(anotherGameContext);

        Assert.assertEquals(1, pacmanOptimizationSolution.compareTo(anotherPacmanOptimizationSolution));
    }

    @Test
    public void anySolutionIsGreaterThanNull() {
        GameContext mockGameContext = mockGameContext();
        PacmanOptimizationSolution pacmanOptimizationSolution = PacmanOptimizationSolution.buildFromContext(mockGameContext);

        Assert.assertEquals(1, pacmanOptimizationSolution.compareTo(null));
    }

    @Test
    public void testScoreNotWinningGame() {
        // winning false: 0 points
        // Timeout differnce 20000 -> 90000 - 20000 = 70000 / 100 = 700
        // Dots present 10 -> 1900 - 100 = 1800
        // Lives 0 -> -100 Points
        // Total 2500
        PacmanOptimizationSolution aSolution = PacmanOptimizationSolution.builder().
                configuredTimeout(70000).
                dotsPresent(10).
                gamePlayTime(50000).
                lives(0).
                winningGame(false).build();

        Assert.assertEquals(2400, aSolution.getScore());
    }

    @Test
    public void testScoreWinningGame() {
        // winning true: 100 points
        // Timeout differnce 20000 -> 90000 - 20000 = 70000 / 100 = 700
        // Dots present 0 -> 1900 - 0 = 1900
        // Lives 1 -> 500 Points
        // Total 3200
        PacmanOptimizationSolution aSolution = PacmanOptimizationSolution.builder().
                configuredTimeout(70000).
                dotsPresent(0).
                gamePlayTime(50000).
                lives(1).
                winningGame(true).build();

        Assert.assertEquals(3200, aSolution.getScore());
    }

    @Test
    public void testSpecificData1() {
        // There's an specific test data resulted from one real executed game to be tested:
        // winning false
        // Timeout differnce  : (57577 - 308432)
        // Dots present 73
        // Lives 1
        PacmanOptimizationSolution aSolution = PacmanOptimizationSolution.builder().
                configuredTimeout(57577).
                dotsPresent(73).
                gamePlayTime(308432).
                lives(1).
                winningGame(false).build();

        Assert.assertEquals(5078, aSolution.getScore());
    }


    private GameContext mockGameContext() {
        GameContext mockGameContext = new GameContext();
        mockGameContext.setStartTime(START_TIME);
        mockGameContext.setEndTime(END_TIME);
        mockGameContext.setTimeout(50000L);
        GamePlayer player = new GamePlayer();
        player.setCurrentPlayer(true);
        player.setObjectType(GameObjectType.PLAYER);
        player.setLives(1);
        mockGameContext.addObject(player);
        mockGameContext.setWinningGame(true);
        return mockGameContext;
    }


    private GameObject getGameItem() {
        GameObject dot = new GameObject();
        dot.setObjectType(GameObjectType.ITEM);
        return dot;
    }
}
