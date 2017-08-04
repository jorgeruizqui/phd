package es.jor.phd.xvgdl.model.object;

/**
 * Game Object basic interface
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
     * @return Is volatile flag
     */
    boolean isVolatile();

    /**
     * Is object dynamic (can move) or fixed?
     * @return Is dynamic flag
     */
    boolean isDynamic();

}
