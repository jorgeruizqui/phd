package com.jrq.xvgdl.pacman.experiment.challenging;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.GameObjectType;
import lombok.Builder;
import lombok.Data;

/**
 * 3. Multi Objective Function
 *     1. Level not passed (Not winning game).
 *     2. Number of dots still present -> Minimize
 *     3. Number of lives -> Minimize (may be not winning game because of timeout)
 *     4. Game play time -> Maximize
 */
@Data
@Builder
public class PacmanChallengingSolution implements Comparable<PacmanChallengingSolution> {

    private boolean winningGame;
    private int dotsPresent;
    private int lives;
    private long gamePlayTime;
    private long configuredTimeout;

    public static PacmanChallengingSolution buildFromContext(GameContext gameContext) {
        return PacmanChallengingSolution.builder().winningGame(gameContext.isWinningGame())
            .dotsPresent(gameContext.getObjectsListByType(GameObjectType.ITEM).size())
            .lives(gameContext.getCurrentGamePlayer().getLives())
            .gamePlayTime(gameContext.getTimePlayed())
                .configuredTimeout(gameContext.getTimeout()).build();
    }

    public boolean isBetterSolutionThan(PacmanChallengingSolution anotherSolution) {
        return this.compareTo(anotherSolution) > 0;
    }

    @Override
    public int compareTo(PacmanChallengingSolution anotherSolution) {
        // Any solution is greater than null by definition
        if (anotherSolution == null) return 1;

        // Not winning game is greater than winning game
        if (this.isWinningGame() != anotherSolution.isWinningGame()) {
            return Boolean.compare(anotherSolution.isWinningGame(), this.isWinningGame());
        }

        // Less dots present is greater than more dots presents
        if (this.getDotsPresent() != anotherSolution.getDotsPresent()) {
            return -1 * Integer.compare(this.getDotsPresent(), anotherSolution.getDotsPresent());
        }

        // Less lives is greater than more lives
        if (this.getLives() != anotherSolution.getLives()) {
            return -1 * Integer.compare(this.getLives(), anotherSolution.getLives());
        }

        // Minimimum distance between the timeout and the time played
        return -1 * Long.compare(
                this.getConfiguredTimeout() - this.getGamePlayTime(),
                anotherSolution.getConfiguredTimeout() - anotherSolution.getGamePlayTime());
    }

    public int getScore() {
        int score = 0;

        if (!this.isWinningGame()) score += 1000;

        // Score for dots depends on how many points are left
        // Max dots possible is 190. 190 * 10 each -> 1900
        // 10 points are discounted for every dot present
        score += 1900 - (getDotsPresent() * 10);

        // We want to minimize lives ... MAX for lives is 600
        score += 600 - (getLives() * 100);

        // As we want to minimize the difference between configured timeout and game play time...
        // Max score for timeout is 90000 and we discount the difference
        score += (120000 - (getConfiguredTimeout() - getGamePlayTime())) / 100;

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
