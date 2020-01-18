package es.jor.phd.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import es.jor.phd.xvgdl.model.location.DirectionVector;
import es.jor.phd.xvgdl.model.object.GameObject;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.ai.IGameObjectAI;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Game Object XML element Definition
 *
 * @author jrquinones
 *
 */
@Slf4j
@Data
public class GameObjectDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private Integer sizeX;
    @JacksonXmlProperty(isAttribute = true)
    private Integer sizeY;
    @JacksonXmlProperty(isAttribute = true)
    private Integer sizeZ;
    @JacksonXmlProperty(isAttribute = true)
    private Integer positionX;
    @JacksonXmlProperty(isAttribute = true)
    private Integer positionY;
    @JacksonXmlProperty(isAttribute = true)
    private Integer positionZ;
    @JacksonXmlProperty(isAttribute = true)
    private Integer instances;
    @JacksonXmlProperty(isAttribute = true)
    private String ai;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean isVolatile;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean isDynamic;
    @JacksonXmlProperty(isAttribute = true)
    private String direction;

    /**
     *
     * @param objectDefinition Object definition
     * @param instance Instance of the object
     * @return Game Object completely initialize
     */
    public static GameObject convert(GameObjectDefinition objectDefinition, int instance) {
        return convert(GameObject.class, objectDefinition, instance);
    }
    /**
     *
     * @param objectDefinition Object definition
     * @param instance Instance of the object
     * @return Game Object completely initialize
     */
    public static GameObject convert(Class clazz, GameObjectDefinition objectDefinition, int instance) {

        try {
            GameObject gameObject = (GameObject) clazz.getDeclaredConstructor().newInstance();
            gameObject.setName(objectDefinition.getName());
            gameObject.setInstance(instance);
            gameObject.setDynamic(objectDefinition.getIsDynamic());
            gameObject.setVolatile(objectDefinition.getIsVolatile());
            gameObject.setPosition(objectDefinition.getPositionX(),
                    objectDefinition.getPositionY(),
                    objectDefinition.getPositionZ());
            gameObject.setSizeX(objectDefinition.getSizeX());
            gameObject.setSizeY(objectDefinition.getSizeY());
            gameObject.setSizeZ(objectDefinition.getSizeZ());
            gameObject.setIntendedPosition(gameObject.getX(), gameObject.getY(), gameObject.getZ());
            gameObject.setObjectType(GameObjectType.fromString(objectDefinition.getType()));

            if (StringUtils.isNotEmpty(objectDefinition.getAi())) {
                gameObject.setObjectAI(
                        (IGameObjectAI) Class.forName(objectDefinition.getAi()).getDeclaredConstructor().newInstance());
            }
            
            if (StringUtils.isNotEmpty(objectDefinition.getDirection())) {
                gameObject.setDirection(DirectionVector.parseFromString(objectDefinition.getDirection()));
            }
            return gameObject;
        } catch (Exception e) {
            log.error("Exception converting GameObjectDefinition to GameObject: " + e.getMessage(), e);
            return  null;
        }
    }
}
