package com.jrq.xvgdl.model.object;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Game Object
 *
 * @author jrquinones
 */
@Getter
@Setter
@ToString
@Slf4j
public class GameObject implements IGameObject {

    /**
     * Name of the game object.
     */
    private String name;
    /**
     * Instance.
     */
    private Integer instance;
    /**
     * Position.
     */
    @Setter(lombok.AccessLevel.NONE)
    private Position position = new Position(-1, -1, -1);
    /**
     * Intended position.
     */
    @Setter(lombok.AccessLevel.NONE)
    private Position intendedPosition = new Position(-1, -1, -1);
    /**
     * Size x .
     */
    private Integer sizeX;
    /**
     * Size y.
     */
    private Integer sizeY;
    /**
     * Size z.
     */
    private Integer sizeZ;
    /**
     * Game Object Type.
     */
    private GameObjectType objectType;
    /**
     * Speed factor.
     */
    private Double speedFactor = 1d;
    /**
     * Direction Vector. If it's 0,0,0 means that the object is stopped.
     */
    private DirectionVector direction = new DirectionVector(0, 0, 0);

    /**
     * Is dynamic flag. Indicates if object can move during game or is fixed
     * forever.
     */
    private Boolean isDynamic;
    /**
     * Is volatile flag. Indicates if object can appear/disappear during game or
     * is present forever.
     */
    private Boolean isVolatile;

    /**
     * Flag indicating if the object is frozen.
     */
    private boolean frozen;

    /**
     * Artificial Intelligence associated with this object.
     */
    private IGameObjectAI ai;

    @Override
    public String getId() {
        return getName() + "-" + getInstance();
    }

    @Override
    public void moveTo(int x, int y, int z) {
        if (!isFrozen()) {
            intendedPosition.setX(x);
            intendedPosition.setY(y);
            intendedPosition.setZ(z);
        }
    }

    @Override
    public void resetMove() {
        intendedPosition.setX(getPosition().getX());
        intendedPosition.setY(getPosition().getY());
        intendedPosition.setZ(getPosition().getZ());
    }

    @Override
    public void applyAI(GameContext gameContext) {
        if (ai != null) {
            ai.applyAIonObject(gameContext, this);
        }
    }

    public void setPosition(Integer x, Integer y, Integer z) {
        position.setX(x);
        position.setY(y);
        position.setZ(z);
    }

    @Override
    public Integer getX() {
        return position.getX();
    }

    @Override
    public Integer getY() {
        return position.getY();
    }

    @Override
    public Integer getZ() {
        return position.getZ();
    }

    public void setIntendedPosition(Integer x, Integer y, Integer z) {
        intendedPosition.setX(x);
        intendedPosition.setY(y);
        intendedPosition.setZ(z);
    }

    @Override
    public void update() {
        setX(getIntendedPosition().getX());
        setY(getIntendedPosition().getY());
        setZ(getIntendedPosition().getZ());

        // If direction vector is informed, object is moving in that direction according the speed factor
        if (direction.notZero()) {
            setX((int) (getX() + (speedFactor * direction.getX())));
            setY((int) (getY() + (speedFactor * direction.getY())));
            setZ((int) (getZ() + (speedFactor * direction.getZ())));
            setIntendedPosition(getX(), getY(), getZ());
        }
    }

    @Override
    public GameObject copy() {
        GameObject cloned = new GameObject();
        try {
            if (this.ai != null) {
                cloned.setAi(this.ai.getClass().newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Exception setting object class AI: ", e);
        }
        cloned.setIsDynamic(getIsDynamic());
        cloned.setInstance((new Random()).nextInt());
        cloned.setIntendedPosition(getIntendedPosition().getX(), getIntendedPosition().getY(),
                getIntendedPosition().getZ());
        cloned.setFrozen(false);
        cloned.setX(getPosition().getX());
        cloned.setY(getPosition().getY());
        cloned.setZ(getPosition().getZ());
        cloned.setName(getName());
        cloned.setSizeX(getSizeX());
        cloned.setSizeY(getSizeY());
        cloned.setSizeZ(getSizeZ());
        cloned.setObjectType(getObjectType());
        cloned.setIsVolatile(getIsVolatile());
        return cloned;
    }

    private void setX(int x) {
        position.setX(x);
    }

    private void setY(int y) {
        position.setY(y);
    }

    private void setZ(int z) {
        position.setZ(z);
    }

    @Override
    public Boolean isLocatedAnyWhereInMap() {
        return (getX() >= 0 && getY() >= 0 && getZ() >= 0) || (getIntendedPosition().getX() >= 0
                && getIntendedPosition().getY() >= 0 && getIntendedPosition().getZ() >= 0);
    }
}
