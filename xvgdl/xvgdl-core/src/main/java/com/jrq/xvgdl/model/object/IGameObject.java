package com.jrq.xvgdl.model.object;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;

/**
 * Game Object basic interface
 *
 * @author jrquinones
 */
public interface IGameObject {

    String getId();

    String getName();

    Integer getInstance();

    void setPosition(Position position);
    void setInitialPosition(int x, int y, int z);
    void setIntendedPosition(int x, int y, int z);
    Position getPosition();
    Position getLastPosition();
    Position getInitialPosition();

    Position getIntendedPosition();

    GameObjectType getObjectType();

    Integer getSizeX();

    Integer getSizeY();

    Integer getSizeZ();

    Boolean getIsVolatile();

    Boolean getIsDynamic();

    void moveTo(int x, int y, int z);

    void resetMove();

    void updateState(GameContext gameContext);

    IGameObject copy();

    void setFrozen(boolean frozen);

    boolean isFrozen();

    Boolean isLocatedAnyWhereInMap();

    DirectionVector getDirection();

    IGameObjectAI getAi();

    void setObjectType(GameObjectType objectType);

    void setSpeedFactor(Double value);
    void increaseSpeedFactor(Double factorToIncrease);
    void decreaseSpeedFactor(Double factorToDecrease);
    void resetSpeedFactor();

}
