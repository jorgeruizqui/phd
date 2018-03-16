package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.model.object.GameObject;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.model.object.ai.IGameObjectAI;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Map XML element Definition
 *
 * @author jrquinones
 *
 */
public class GameObjectDefinition extends Properties {

    /** XML main tag. */
    public static final String XMLTAG = "object";

    /** XML Attribute. Name. */
    public static final String XMLATTR_NAME = "name";

    /** XML Attribute. Type. */
    public static final String XMLATTR_TYPE = "type";

    /** XML Attribute. Size X. */
    public static final String XMLATTR_SIZE_X = "sizeX";

    /** XML Attribute. Size Y. */
    public static final String XMLATTR_SIZE_Y = "sizeY";

    /** XML Attribute. Size Z. */
    public static final String XMLATTR_SIZE_Z = "sizeZ";

    /** XML Attribute. Position X. */
    public static final String XMLATTR_POSITION_X = "x";

    /** XML Attribute. Position Y. */
    public static final String XMLATTR_POSITION_Y = "y";

    /** XML Attribute. Position Z. */
    public static final String XMLATTR_POSITION_Z = "z";

    /** XML Attribute. Instances. */
    public static final String XMLATTR_INSTANCES = "instances";

    /** XML Attribute. Artificial Intelligence. */
    public static final String XMLATTR_AI = "ai";

    /** XML Attribute. Volatile. */
    public static final String XMLATTR_VOLATILE = "volatile";

    /** XML Attribute. Dynamic. */
    public static final String XMLATTR_DYNAMIC = "dynamic";

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }

    /**
     *
     * @param objectDefinition Object definition
     * @param instance Instance of the object
     * @return Game Object completely initialize
     */
    public static IGameObject convert(GameObjectDefinition objectDefinition, int instance) {

        GameObject gameObject = null;

        try {
            gameObject = new GameObject();
            gameObject.setName(objectDefinition.getProperty(XMLATTR_NAME));
            gameObject.setInstance(instance);
            gameObject.setDynamic(objectDefinition.getBooleanValue(XMLATTR_DYNAMIC, false));
            gameObject.setVolatile(objectDefinition.getBooleanValue(XMLATTR_VOLATILE, false));
			gameObject.setPosition(objectDefinition.getIntegerValue(XMLATTR_POSITION_X, -1),
					objectDefinition.getIntegerValue(XMLATTR_POSITION_Y, -1),
					objectDefinition.getIntegerValue(XMLATTR_POSITION_Z, -1));
            gameObject.setSizeX(objectDefinition.getIntegerValue(XMLATTR_SIZE_X, 0));
            gameObject.setSizeY(objectDefinition.getIntegerValue(XMLATTR_SIZE_Y, 0));
            gameObject.setSizeZ(objectDefinition.getIntegerValue(XMLATTR_SIZE_Z, 0));
            gameObject.setIntendedPosition(gameObject.getX(), gameObject.getY(), gameObject.getZ());
            gameObject.setObjectType(GameObjectType.fromString(objectDefinition.getProperty(XMLATTR_TYPE)));

            if (objectDefinition.getProperty(XMLATTR_AI) != null
                    && !objectDefinition.getProperty(XMLATTR_AI).trim().equals("")) {
                gameObject.setObjectAI((IGameObjectAI) Class.forName(objectDefinition.getProperty(XMLATTR_AI)).newInstance());
            }
        } catch (Exception e) {
            ELogger.error(GameMapDefinition.class, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Exception converting GameObjectDefinition to GameObject: " + e.getMessage(), e);
            gameObject = null;
        }

        return gameObject;
    }
}
