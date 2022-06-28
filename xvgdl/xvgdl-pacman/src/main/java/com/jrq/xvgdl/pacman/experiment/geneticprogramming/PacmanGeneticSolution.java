package com.jrq.xvgdl.pacman.experiment.geneticprogramming;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.GameObjectType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * 3. Multi Objective Function
 *     1. Level passed (winning game).
 *     2. Number of dots still present -> Minimize
 *     3. Number of lives present -> Minimize
 *     4. Game play time -> Maximize
 */
@Data
@Builder
public class PacmanGeneticSolution implements Comparable<PacmanGeneticSolution> {

    @ToString.Exclude
    private GameContext initialContext;

    private boolean winningGame;
    private int dotsPresent;
    private int lives;
    private long gamePlayTime;
    private long configuredTimeout;

    public static PacmanGeneticSolution buildFromContext(GameContext originalGameContext, GameContext finalContext) {
        return PacmanGeneticSolution.builder()
            .initialContext(originalGameContext)
            .winningGame(finalContext.isWinningGame())
            .dotsPresent(finalContext.getObjectsListByType(GameObjectType.ITEM).size())
            .lives(finalContext.getCurrentGamePlayer().getLives())
            .gamePlayTime(finalContext.getTimePlayed())
                .configuredTimeout(finalContext.getTimeout()).build();
    }

    public boolean isBetterSolutionThan(PacmanGeneticSolution anotherSolution) {
        return this.compareTo(anotherSolution) > 0;
    }

    @Override
    public int compareTo(PacmanGeneticSolution anotherSolution) {
        // Any solution is greater than null by definition
        if (anotherSolution == null) return 1;

        // Winning game is greater than not winning game
        if (this.isWinningGame() != anotherSolution.isWinningGame()) {
            return Boolean.compare(this.isWinningGame(), anotherSolution.isWinningGame());
        }

        // Less dots present is greater than more dots presents
        if (this.getDotsPresent() != anotherSolution.getDotsPresent()) {
            return -1 * Integer.compare(this.getDotsPresent(), anotherSolution.getDotsPresent());
        }

        // Less lives is greater than more lives
        if (this.getLives() != anotherSolution.getLives()) {
            return -1 * Integer.compare(this.getLives(), anotherSolution.getLives());
        }

        // Minimum distance between the timeout and the time played
        return -1 * Long.compare(
                this.getConfiguredTimeout() - this.getGamePlayTime(),
                anotherSolution.getConfiguredTimeout() - anotherSolution.getGamePlayTime());
    }

    public int getScore() {
        int score = 0;

        if (this.isWinningGame()) score += 1000;

        // Score for dots depends on how many points are left
        // Max dots possible is 190. 190 * 10 each -> 1900
        // 10 points are discounted for every dot present
        score += 1900 - (getDotsPresent() * 10);

        // As we want to minimize lives (not reaching 0)... MAX for lives is 600
        if (getLives() > 0) {
            score += 600 - (getLives() * 100);
        } else {
            score -= 100;
        }

        // As we want to minimize the difference between configured timeout and game play time...
        // Max score for timeout is 90000 and we discount the difference
        score += (40000 - getGamePlayTime()) / 100;

        return score;
    }

    public String toCsv() {
        return this.isWinningGame() + "$"
                + this.getDotsPresent() + "$"
                + this.getLives() + "$"
                + this.getGamePlayTime() + "$"
                + this.getScore();
    }
}
