package es.jor.phd.xvgdl.model.objectives;

import es.jor.phd.xvgdl.context.GameContext;

public interface IGameObjective {

    /**
     * Checks the objective and returns its value for the fitness function
     * 
     * @return
     */
    double checkObjective(GameContext c);

    /**
     *
     * @return the score
     */
    double getScore();

    /**
     *
     * @return the weight
     */
    double getWeight();

    /**
     *
     * @param score The score to set
     */
    void setScore(double score);

    /**
     *
     * @param weight The weight to set
     */
    void setWeight(double weight);

}
