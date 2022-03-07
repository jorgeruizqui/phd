package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class GameObjectDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String spriteName;
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
    @JacksonXmlProperty(isAttribute = true)
    private Double speedFactor;

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
            gameObject.setSpriteName(this.getSpriteName());
            gameObject.setInstance(instance);
            gameObject.setIsDynamic(this.getIsDynamic());
            gameObject.setIsVolatile(this.getIsVolatile());
            gameObject.setPosition(
                    Optional.ofNullable(this.getPositionX()).orElse(-1),
                    Optional.ofNullable(this.getPositionY()).orElse(-1),
                    Optional.ofNullable(this.getPositionZ()).orElse(-1));
            gameObject.setSizeX(this.getSizeX());
            gameObject.setSizeY(this.getSizeY());
            gameObject.setSizeZ(this.getSizeZ());
            gameObject.setIntendedPosition(gameObject.getPosition().getX(),
                    gameObject.getPosition().getY(),
                    gameObject.getPosition().getZ());
            gameObject.setInitialPosition(gameObject.getPosition().getX(),
                    gameObject.getPosition().getY(),
                    gameObject.getPosition().getZ());
            gameObject.setObjectType(GameObjectType.fromString(this.getType()));
            gameObject.setSpeedFactor(Optional.ofNullable(this.getSpeedFactor()).orElse(1.0));
            gameObject.setInitialSpeedFactor(Optional.ofNullable(this.getSpeedFactor()).orElse(1.0));

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
