package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.IIdentificableObject;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.model.map.GameMap;
import es.jor.phd.xvgdl.model.map.GameMapType;
import es.jor.phd.xvgdl.model.map.IGameMap;
import es.jor.phd.xvgdl.model.map.IGameMapGenerator;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Map XML element Definition
 *
 * @author jrquinones
 *
 */
public class GameMapDefinition extends Properties implements IIdentificableObject {

    /** XML main tag. */
    public static final String XMLTAG = "map";

    /** XML Attribute. Type. */
    public static final String XMLATTR_TYPE = "type";

    /** XML Attribute. Size X. */
    public static final String XMLATTR_SIZE_X = "sizeX";

    /** XML Attribute. Size Y. */
    public static final String XMLATTR_SIZE_Y = "sizeY";

    /** XML Attribute. Size Z. */
    public static final String XMLATTR_SIZE_Z = "sizeZ";

    /** XML Attribute. Toroidal. */
    public static final String XMLATTR_TOROIDAL = "toroidal";

    /** XML Attribute. Generator. */
    public static final String XMLATTR_GENERATOR = "generator";

    @Override
    public String getId() {
        return getProperty(XMLATTR_TYPE);
    }

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }

    /**
     * Convert to IGameMap interface
     * @param definition Game Map Definition
     * @return Game Map converted from XML to Object
     */
    public static IGameMap convert(GameMapDefinition definition) {
        GameMap gameMap = new GameMap();
        try {
            gameMap.setSizeX(definition.getIntegerValue(XMLATTR_SIZE_X, 0));
            gameMap.setSizeY(definition.getIntegerValue(XMLATTR_SIZE_Y, 0));
            gameMap.setSizeZ(definition.getIntegerValue(XMLATTR_SIZE_Z, 0));
            gameMap.setGenerator((IGameMapGenerator) Class.forName(
                    definition.getProperty(XMLATTR_GENERATOR)).newInstance());
            gameMap.setMapRepresentation(
                    new IGameObject[gameMap.getSizeX()][gameMap.getSizeY()][gameMap.getSizeZ()]);
            gameMap.setMapType(GameMapType.fromString(definition.getProperty(XMLATTR_TYPE)));
            gameMap.setToroidal(definition.getBooleanValue(XMLATTR_TOROIDAL));
        } catch (Exception e) {
            ELogger.error(GameMapDefinition.class, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Exception converting GameMapDefinition to GameMap: " + e.getMessage(), e);
            gameMap = null;
        }

        return gameMap;
    }
}
