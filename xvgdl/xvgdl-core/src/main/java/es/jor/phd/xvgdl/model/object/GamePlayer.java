package es.jor.phd.xvgdl.model.object;

/**
 * Game player object
 *
 * @author jrquinones
 *
 */
public class GamePlayer extends GameObject {

    /** Current number of lives. */
    private int lives;
    /** Current live percentage. */
    private int livePercentage;
    /** Current Player score. */
    private double score;

    /**
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * @param lives the lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * @return the livePercentage
     */
    public int getLivePercentage() {
        return livePercentage;
    }

    /**
     * @param livePercentage the livePercentage to set
     */
    public void setLivePercentage(int livePercentage) {
        this.livePercentage = livePercentage;
    }

    /**
     * @return the score
     */
    public double getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(double score) {
        this.score = score;
    }

}
