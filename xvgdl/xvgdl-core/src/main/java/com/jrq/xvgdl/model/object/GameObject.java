package com.jrq.xvgdl.model.object;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;
import lombok.Data;
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
@Data
@Slf4j
public class GameObject implements IGameObject {

    private String name;
    private Integer instance;
    private Position position = new Position(-1, -1, -1);
    private Position intendedPosition = new Position(-1, -1, -1);
    private Integer sizeX;
    private Integer sizeY;
    private Integer sizeZ;
    private GameObjectType objectType;
    private Double speedFactor = 1.0;
    private Double initialSpeedFactor = 1.0;
    private DirectionVector direction = new DirectionVector(0, 0, 0);
    private Boolean isDynamic;
    private Boolean isVolatile;
    private boolean frozen;
    private IGameObjectAI ai;
    private long updatedAt = 0;

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

    @Override
    public Boolean isLocatedAnyWhereInMap() {
        return (getX() >= 0 && getY() >= 0 && getZ() >= 0) || (getIntendedPosition().getX() >= 0
                && getIntendedPosition().getY() >= 0 && getIntendedPosition().getZ() >= 0);
    }

    @Override
    public void updateState(GameContext gameContext) {
        if ((gameContext.getLoopTime() - updatedAt) >= getMySpeedFactorInMilliseconds()) {
            if (updatedAt > 0) applyAI(gameContext);

            setX(getIntendedPosition().getX());
            setY(getIntendedPosition().getY());
            setZ(getIntendedPosition().getZ());

            // If direction vector is informed, object is moving in that direction according the speed factor
            if (direction.notZero()) {
                setX(getX() + (direction.getX()));
                setY(getY() + (direction.getY()));
                setZ(getZ() + (direction.getZ()));
                setIntendedPosition(getX(), getY(), getZ());
            }
            setUpdatedAt(gameContext.getLoopTime());
        }
    }

    @Override
    public void increaseSpeedFactor(Double factorToIncrease) {
        this.speedFactor += factorToIncrease;
    }

    @Override
    public void decreaseSpeedFactor(Double factorToDecrease) {
        this.speedFactor -= factorToDecrease;
    }

    @Override
    public void resetSpeedFactor() {
        this.speedFactor = this.initialSpeedFactor;
    }

    @Override
    public GameObject copy() {
        GameObject cloned = new GameObject();
        try {
            if (this.ai != null) {
                cloned.setAi(this.ai.getClass().getConstructor().newInstance());
            }
        } catch (Exception e) {
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

    public void setIntendedPosition(Integer x, Integer y, Integer z) {
        intendedPosition.setX(x);
        intendedPosition.setY(y);
        intendedPosition.setZ(z);
    }

    private void applyAI(GameContext gameContext) {
        if (ai != null) {
            ai.applyAIonObject(gameContext, this);
        }
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

    private double getMySpeedFactorInMilliseconds() {
        return GameEngine.GAME_ENGINE_SPEED_FACTOR / getSpeedFactor();
    }
}
