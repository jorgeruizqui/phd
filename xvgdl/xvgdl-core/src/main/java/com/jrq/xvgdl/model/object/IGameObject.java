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

    /**
     * @return Object unique indentifier
     */
    String getId();

    /**
     * @return Object Name
     */
    String getName();

    /**
     * @return Game object instance.
     */
    Integer getInstance();

    /**
     * @return position
     */
    Position getPosition();

    /**
     * @return the X position
     */
    Integer getX();

    /**
     * @return the Y position
     */
    Integer getY();

    /**
     * @return the Z position
     */
    Integer getZ();

    /**
     * @return intended position
     */
    Position getIntendedPosition();

    /**
     * @return Game Type
     */
    GameObjectType getObjectType();

    /**
     * @return X size
     */
    Integer getSizeX();

    /**
     * @return Y size
     */
    Integer getSizeY();

    /**
     * @return Z size
     */
    Integer getSizeZ();

    /**
     * Is object volatile (can appear/disappear)?
     *
     * @return Is volatile flag
     */
    Boolean getIsVolatile();

    /**
     * Is object dynamic (can move) or fixed?
     *
     * @return Is dynamic flag
     */
    Boolean getIsDynamic();

    /**
     * Moves the object to the specified coordinates
     *
     * @param x X Coordinate
     * @param y Y Coordinate
     * @param z Z Coordinate
     */
    void moveTo(int x, int y, int z);

    /**
     * Reset the intended values and restore to current values, so the element
     * can't move.
     */
    void resetMove();

    /**
     * Applies the Artificial Intelligence configured for this game object
     *
     * @param gameContext Game Context
     */
    void applyAI(GameContext gameContext);

    /**
     * Updates intended positions to consolided positions.
     */
    void update();

    /**
     * Clones a game object
     *
     * @return Game Object Cloned.
     */
    IGameObject copy();

    /**
     * Sets the frozen flag
     *
     * @param frozen
     */
    void setIsFrozen(Boolean frozen);

    /**
     * Is frozen flag
     *
     * @return
     */
    Boolean getIsFrozen();

    /**
     * @return <code>true</code> if object is located anywhere in map
     * (coordintes 0 or bigger)
     */
    Boolean isLocatedAnyWhereInMap();

    /**
     * @param value the new speed factor for an object.
     */
    void setSpeedFactor(Double value);

    DirectionVector getDirection();

    IGameObjectAI getAi();

    void setObjectType(GameObjectType objectType);
}
