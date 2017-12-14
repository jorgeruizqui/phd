package es.jor.phd.xvgdl.model.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Game player object
 *
 * @author jrquinones
 *
 */
@Getter
@Setter
@ToString
public class GamePlayer extends GameObject {

    /** Initial number of lives. */
    private int initialLives;
    /** Current number of lives. */
    private int lives;
    /** Current live percentage. */
    private int livePercentage;
    /** Current Player score. */
    private double score;

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

}
