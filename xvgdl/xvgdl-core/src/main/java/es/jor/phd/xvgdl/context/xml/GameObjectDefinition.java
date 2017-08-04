package es.jor.phd.xvgdl.context.xml;

import java.util.UUID;

import es.indra.eplatform.properties.Properties;
import es.jor.phd.xvgdl.model.object.GameObject;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Game Map XML element Definition
 *
 * @author jrquinones
 *
 */
public class GameObjectDefinition extends Properties {

    /** XML main tag. */
    public static final String XMLTAG = "object";

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

    /** XML Attribute. Renderer. */
    public static final String XMLATTR_RENDERER = "renderer";

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
     * @return Game Object completely initialize
     */
    public static IGameObject convert(GameObjectDefinition objectDefinition) {
        GameObject gameObject = new GameObject();
        gameObject.setId(UUID.randomUUID().toString());
        gameObject.setDynamic(objectDefinition.getBooleanValue(XMLATTR_DYNAMIC, false));
        gameObject.setVolatile(objectDefinition.getBooleanValue(XMLATTR_VOLATILE, false));
        gameObject.setX(objectDefinition.getIntegerValue(XMLATTR_POSITION_X, 0));
        gameObject.setY(objectDefinition.getIntegerValue(XMLATTR_POSITION_Y, 0));
        gameObject.setZ(objectDefinition.getIntegerValue(XMLATTR_POSITION_Z, 0));
        gameObject.setSizeX(objectDefinition.getIntegerValue(XMLATTR_SIZE_X, 0));
        gameObject.setSizeY(objectDefinition.getIntegerValue(XMLATTR_SIZE_Y, 0));
        gameObject.setSizeZ(objectDefinition.getIntegerValue(XMLATTR_SIZE_Z, 0));
        gameObject.setIntendedX(gameObject.getX());
        gameObject.setIntendedY(gameObject.getY());
        gameObject.setIntendedZ(gameObject.getZ());
        gameObject.setObjectType(GameObjectType.fromString(objectDefinition.getProperty(XMLATTR_TYPE)));
        return gameObject;
    }
}
