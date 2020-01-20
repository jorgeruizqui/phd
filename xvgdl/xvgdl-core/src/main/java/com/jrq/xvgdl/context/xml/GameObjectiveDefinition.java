package com.jrq.xvgdl.context.xml;

import com.jrq.xvgdl.model.objectives.IGameObjective;
import lombok.extern.slf4j.Slf4j;

/**
 * Game End Condition XML element Definition
 *
 * @author jrquinones
 */
@Slf4j
public class GameObjectiveDefinition extends GameElementBaseDefinition {

    /**
     * XML main tag.
     */
    public static final String XMLTAG = "objective";

    /**
     * XML Attribute. Class Name.
     */
    public static final String XMLATTR_CHECKER_CLASS = "objectiveCheckerClass";

    /**
     * XML Attribute. Weight.
     */
    public static final String XMLATTR_WEIGHT = "weight";

    /**
     * XML Attribute. Score.
     */
    public static final String XMLATTR_SCORE = "score";

    /**
     * @param gameObjectiveDefinition Object definition
     * @return Game Objective initialized
     */
    public static IGameObjective convert(GameObjectiveDefinition gameObjectiveDefinition) {

        IGameObjective gameObjective = null;

        try {
            gameObjective = (IGameObjective) Class.forName(gameObjectiveDefinition.getProperty(XMLATTR_CHECKER_CLASS))
                    .newInstance();

            gameObjective.setScore(gameObjectiveDefinition.getDoubleValue(XMLATTR_SCORE, 0d));
            gameObjective.setWeight(gameObjectiveDefinition.getDoubleValue(XMLATTR_WEIGHT, 1d));

        } catch (Exception e) {
            log.error("Exception converting GameObjectiveDefinition to IGameObjective: " + e.getMessage(), e);
            gameObjective = null;
        }

        return gameObjective;
    }
}
