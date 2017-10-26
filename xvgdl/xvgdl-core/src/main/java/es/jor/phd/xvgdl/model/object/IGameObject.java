package es.jor.phd.xvgdl.model.object;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Game Object basic interface
 *
 * @author jrquinones
 *
 */
public interface IGameObject {

    /**
     *
     * @return Object unique indentifier
     */
    String getId();

    /**
     *
     * @return Object Name
     */
    String getName();

    /**
     *
     * @return Game object instance.
     */
    int getInstance();

    /**
     *
     * @return X position
     */
    int getX();

    /**
     *
     * @return Y position
     */
    int getY();

    /**
     *
     * @return Z position
     */
    int getZ();

    /**
     *
     * @return X intended position
     */
    int getIntendedX();

    /**
     *
     * @return Y intended position
     */
    int getIntendedY();

    /**
     *
     * @return Z intended position
     */
    int getIntendedZ();

    /**
     *
     * @return Game Type
     */
    GameObjectType getType();

    /**
     *
     * @return X size
     */
    int getSizeX();

    /**
     *
     * @return Y size
     */
    int getSizeY();

    /**
     *
     * @return Z size
     */
    int getSizeZ();

    /**
     * Is object volatile (can appear/disappear)?
     *
     * @return Is volatile flag
     */
    boolean isVolatile();

    /**
     * Is object dynamic (can move) or fixed?
     *
     * @return Is dynamic flag
     */
    boolean isDynamic();

    /**
     * Moves the object to the specified coordinates
     *
     * @param x X Coordinate
     * @param y Y Coordinate
     * @param z Z Coordinate
     */
    void moveTo(int x, int y, int z);

    /**
     * Applies the Artificial Intelligence configured for this game object
     *
     * @param gameContext Game Context
     */
    void applyAI(GameContext gameContext);

    /**
     * Clones a game object
     * @return Game Object Cloned.
     */
    IGameObject clone();

}
