package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.GamePlayer;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Map XML element Definition
 *
 * @author jrquinones
 *
 */
public class GamePlayerDefinition extends GameObjectDefinition {

    /** XML main tag. */
    public static final String XMLTAG = "player";

    /** XML Attribute. lives. */
    public static final String XMLATTR_LIVES = "lives";

    /** XML Attribute. Live Percentage. */
    public static final String XMLATTR_LIVE_PERCENTAGE = "livePercentage";

    /** XML Attribute. Score. */
    public static final String XMLATTR_SCORE = "score";

    /** 100 Percentage value. */
    private static final int N_100 = 100;

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }

    /**
     *
     * @param playerDefinition Object definition
     * @return Game Object completely initialize
     */
    public static IGameObject convert(GamePlayerDefinition playerDefinition) {

        GamePlayer gameObject = null;

        try {
            gameObject = new GamePlayer();
            gameObject.setName(playerDefinition.getProperty(XMLATTR_NAME));
            gameObject.setInstance(1);
            gameObject.setDynamic(true);
            gameObject.setVolatile(true);
            gameObject.setX(playerDefinition.getIntegerValue(XMLATTR_POSITION_X, 0));
            gameObject.setY(playerDefinition.getIntegerValue(XMLATTR_POSITION_Y, 0));
            gameObject.setZ(playerDefinition.getIntegerValue(XMLATTR_POSITION_Z, 0));
            gameObject.setSizeX(playerDefinition.getIntegerValue(XMLATTR_SIZE_X, 0));
            gameObject.setSizeY(playerDefinition.getIntegerValue(XMLATTR_SIZE_Y, 0));
            gameObject.setSizeZ(playerDefinition.getIntegerValue(XMLATTR_SIZE_Z, 0));
            gameObject.setIntendedX(gameObject.getX());
            gameObject.setIntendedY(gameObject.getY());
            gameObject.setIntendedZ(gameObject.getZ());
            gameObject.setObjectType(GameObjectType.PLAYER);

            gameObject.setLives(playerDefinition.getIntegerValue(XMLATTR_LIVES, 1));
            gameObject.setLivePercentage(playerDefinition.getIntegerValue(XMLATTR_LIVE_PERCENTAGE, N_100));
            gameObject.setScore(playerDefinition.getIntegerValue(XMLATTR_SCORE, 0));

        } catch (Exception e) {
            ELogger.error(GameMapDefinition.class, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Exception converting GameObjectDefinition to GameObject: " + e.getMessage(), e);
            gameObject = null;
        }

        return gameObject;
    }
}
