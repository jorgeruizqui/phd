package com.jrq.xvgdl.model.object;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;
import java.util.Random;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Game Object
 *
 * @author jrquinones
 */
@Data
@Slf4j
public class GameObject implements IGameObject {

    private String name;
    private String spriteName;
    private Integer instance;
    private Position position = new Position(-1, -1, -1);
    private Position lastPosition = new Position(-1, -1, -1);
    private Position intendedPosition = new Position(-1, -1, -1);
    private Position initialPosition = new Position(-1, -1, -1);
    private Integer sizeX;
    private Integer sizeY;
    private Integer sizeZ;
    private GameObjectType objectType;
    private double speedFactor = 1.0;
    private double initialSpeedFactor = 1.0;
    private DirectionVector direction = new DirectionVector(new Position(0, 0, 0));
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
    public void resetMove(IGameObject collision) {
        intendedPosition.setX(getPosition().getX());
        intendedPosition.setY(getPosition().getY());
        intendedPosition.setZ(getPosition().getZ());
    }

    public void setPosition(int x, int y, int z) {
        lastPosition.setX(position.getX());
        lastPosition.setY(position.getY());
        lastPosition.setZ(position.getZ());
        position.setX(x);
        position.setY(y);
        position.setZ(z);
        setIntendedPosition(getX(), getY(), getZ());
    }

    private Integer getX() {
        return position.getX();
    }

    private Integer getY() {
        return position.getY();
    }

    private Integer getZ() {
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

            if (updatedAt > 0) {
                applyAI(gameContext);
            }

            moveBasedOnIntendedPosition(gameContext);
            moveBaseOnDirectionVector(gameContext);
            setUpdatedAt(gameContext.getLoopTime());
        }
    }

    private void moveBaseOnDirectionVector(GameContext gameContext) {
        if (canMoveToDirectionVectorPosition(gameContext)) {
            if (direction.notZero()) {
                setPosition(
                    getX() + direction.getPosition().getX(),
                    getY() + direction.getPosition().getY(),
                    getZ() + direction.getPosition().getZ());
            }
        }
    }

    private void moveBasedOnIntendedPosition(GameContext gameContext) {
        if (canMoveToIntendedPosition(gameContext)) {
            setPosition(
                getIntendedPosition().getX(),
                getIntendedPosition().getY(),
                getIntendedPosition().getZ());
        }
    }

    private boolean canMoveToDirectionVectorPosition(GameContext gameContext) {
        return canMoveToPosition(gameContext, getDirection().getPosition());
    }

    private boolean canMoveToIntendedPosition(GameContext gameContext) {
        return canMoveToPosition(gameContext, getIntendedPosition());
    }

    private boolean canMoveToPosition(GameContext gameContext, Position position) {
        boolean canMove = true;

        IGameObject objectAtPosition = gameContext.getObjectAt(
            position.getX(),
            position.getY(),
            position.getZ());
        if (objectAtPosition != null && objectAtPosition.getObjectType().equals(GameObjectType.WALL)) {
            canMove = false;
        }
        return canMove;
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
        cloned.setFrozen(false);

        clonePositions(cloned);

        cloned.setName(getName());
        cloned.setSizeX(getSizeX());
        cloned.setSizeY(getSizeY());
        cloned.setSizeZ(getSizeZ());
        cloned.setObjectType(getObjectType());
        cloned.setIsVolatile(getIsVolatile());
        cloned.setSpeedFactor(getSpeedFactor());
        cloned.setInitialSpeedFactor(Double.valueOf(getInitialSpeedFactor()));
        cloned.setSpriteName(getSpriteName());
        return cloned;
    }

    private void clonePositions(GameObject cloned) {
        cloned.setIntendedPosition(
            getIntendedPosition().getX(),
            getIntendedPosition().getY(),
            getIntendedPosition().getZ());
        cloned.setX(getPosition().getX());
        cloned.setY(getPosition().getY());
        cloned.setZ(getPosition().getZ());
        cloned.setLastPosition(
            new Position(
                getLastPosition().getX(),
                getLastPosition().getY(),
                getLastPosition().getZ()));
        cloned.setInitialPosition(
            getInitialPosition().getX(),
            getInitialPosition().getY(),
            getInitialPosition().getZ());
    }

    public void setIntendedPosition(int x, int y, int z) {
        intendedPosition.setX(x);
        intendedPosition.setY(y);
        intendedPosition.setZ(z);
    }

    public void setInitialPosition(int x, int y, int z) {
        initialPosition.setX(x);
        initialPosition.setY(y);
        initialPosition.setZ(z);
    }

    protected void applyAI(GameContext gameContext) {
        if (ai != null) {
            ai.applyAIonObject(gameContext, this);
        }
    }

    protected void setX(int x) {
        position.setX(x);
    }

    protected void setY(int y) {
        position.setY(y);
    }

    protected void setZ(int z) {
        position.setZ(z);
    }

    private double getMySpeedFactorInMilliseconds() {
        return GameEngine.GAME_ENGINE_SPEED_FACTOR / getSpeedFactor();
    }
}
