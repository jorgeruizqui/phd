package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.model.objectives.IGameObjective;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game End Condition XML element Definition
 *
 * @author jrquinones
 *
 */
public class GameObjectiveDefinition extends Properties {

    /** XML main tag. */
    public static final String XMLTAG = "objective";

    /** XML Attribute. Class Name. */
    public static final String XMLATTR_CHECKER_CLASS = "objectiveCheckerClass";

    /** XML Attribute. Weight. */
    public static final String XMLATTR_WEIGHT = "weight";

    /** XML Attribute. Score. */
    public static final String XMLATTR_SCORE = "score";

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }

    /**
     *
     * @param gameObjectiveDefinition Object definition
     * @return Game Objective initialized
     */
    public static IGameObjective convert(GameObjectiveDefinition gameObjectiveDefinition) {

    	IGameObjective gameObjective = null;

        try {
        	gameObjective = (IGameObjective) Class.forName(
                    gameObjectiveDefinition.getProperty(XMLATTR_CHECKER_CLASS)).newInstance();

        	gameObjective.setScore(gameObjectiveDefinition.getDoubleValue(XMLATTR_SCORE, 0d));
        	gameObjective.setWeight(gameObjectiveDefinition.getDoubleValue(XMLATTR_WEIGHT, 1d));

        } catch (Exception e) {
            ELogger.error(GameObjectiveDefinition.class, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Exception converting GameObjectiveDefinition to IGameObjective: " + e.getMessage(), e);
            gameObjective = null;
        }

        return gameObjective;
    }
}
