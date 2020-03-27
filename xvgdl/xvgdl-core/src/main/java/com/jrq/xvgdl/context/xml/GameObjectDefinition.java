package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Game Object XML element Definition
 *
 * @author jrquinones
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
    private Integer instances = 1;
    @JacksonXmlProperty(isAttribute = true)
    private String ai;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean isVolatile;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean isDynamic;
    @JacksonXmlProperty(isAttribute = true)
    private String direction;

    /**
     * @param instance         Instance of the object
     * @return Game Object model from Definition
     */
    public GameObject toModel(int instance) {
        return toModel(GameObject.class, instance);
    }

    /**
     * @param instance        Instance of the object
     * @return Game Object completely initialize
     */
    public GameObject toModel(Class clazz, int instance) {

        try {
            GameObject gameObject = (GameObject) clazz.getDeclaredConstructor().newInstance();
            gameObject.setName(this.getName());
            gameObject.setInstance(instance);
            gameObject.setIsDynamic(this.getIsDynamic());
            gameObject.setIsVolatile(this.getIsVolatile());
            gameObject.setPosition(
                    Optional.ofNullable(this.getPositionX()).orElse(0),
                    Optional.ofNullable(this.getPositionY()).orElse(0),
                    Optional.ofNullable(this.getPositionZ()).orElse(0));
            gameObject.setSizeX(this.getSizeX());
            gameObject.setSizeY(this.getSizeY());
            gameObject.setSizeZ(this.getSizeZ());
            gameObject.setIntendedPosition(gameObject.getX(), gameObject.getY(), gameObject.getZ());
            gameObject.setObjectType(GameObjectType.fromString(this.getType()));

            if (StringUtils.isNotEmpty(this.getAi())) {
                gameObject.setAi(
                        (IGameObjectAI) Class.forName(this.getAi()).getDeclaredConstructor().newInstance());
            }

            if (StringUtils.isNotEmpty(this.getDirection())) {
                gameObject.setDirection(DirectionVector.parseFromString(this.getDirection()));
            }
            return gameObject;
        } catch (Exception e) {
            log.error("Exception converting GameObjectDefinition to GameObject: " + e.getMessage(), e);
            return null;
        }
    }
}
