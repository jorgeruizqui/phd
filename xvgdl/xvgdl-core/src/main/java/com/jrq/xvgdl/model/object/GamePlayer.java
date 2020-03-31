package com.jrq.xvgdl.model.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Game player object
 *
 * @author jrquinones
 */
@Getter
@Setter
@ToString
public class GamePlayer extends GameObject {

    /**
     * Initial number of lives.
     */
    private Integer initialLives;
    /**
     * Current number of lives.
     */
    private Integer lives;
    /**
     * Current live percentage.
     */
    private Integer livePercentage;
    /**
     * Current Player score.
     */
    private Double score;

    private boolean currentPlayer;

    /**
     * Increment player lives.
     */
    public void livesUp() {
        this.lives++;
    }

    /**
     * Decrement player lives.
     */
    public void livesDown() {
        this.lives--;
    }

    /**
     * Reset to initial lives.
     */
    public void liveReset() {
        this.lives = this.initialLives;
    }

    /**
     * Set score to a concrete value
     *
     * @param score
     */
    public void scoreSetTo(double score) {
        this.score = score;
    }

    /**
     * Scores up
     *
     * @param score
     */
    public void scoreUp(double score) {
        this.score += score;
    }

    /**
     * Scores down
     *
     * @param score
     */
    public void scoreDown(double score) {
        this.score -= score;
    }

}
