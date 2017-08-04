package es.jor.phd.xvgdl.model.object;

/**
 * Game Object
 *
 * @author jrquinones
 *
 */
public class GameObject implements IGameObject {

    /** Unique identifier of the game object. */
    private String id;
    /** x position. */
    private int x;
    /** y position. */
    private int y;
    /** z position. */
    private int z;
    /** x intended position. */
    private int intendedX;
    /** y intended position. */
    private int intendedY;
    /** z intended position. */
    private int intendedZ;
    /** Size x . */
    private int sizeX;
    /** Size y. */
    private int sizeY;
    /** Size z. */
    private int sizeZ;
    /** Game Object Type. */
    private GameObjectType objectType;
    /**
     * Is dynamic flag. Indicates if object can move during game or is fixed
     * forever.
     */
    private boolean isDynamic;
    /**
     * Is volatile flag. Indicates if object can appear/disappear during game or
     * is present forever.
     */
    private boolean isVolatile;

    @Override
    public String getId() {
        return id;
    }

    /**
     *
     * @param id Id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getX() {
        return x;
    }

    /**
     *
     * @param x X position
     */
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    /**
     *
     * @param y Y position
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getZ() {
        return z;
    }

    /**
     *
     * @param z Z position
     */
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public int getIntendedX() {
        return intendedX;
    }

    /**
     *
     * @param intendedX Intended X position
     */
    public void setIntendedX(int intendedX) {
        this.intendedX = intendedX;
    }

    @Override
    public int getIntendedY() {
        return intendedY;
    }

    /**
     *
     * @param intendedY Intended Y position
     */
    public void setIntendedY(int intendedY) {
        this.intendedY = intendedY;
    }

    @Override
    public int getIntendedZ() {
        return intendedZ;
    }

    /**
     *
     * @param intendedZ Intended Z position
     */
    public void setIntendedZ(int intendedZ) {
        this.intendedZ = intendedZ;
    }

    @Override
    public GameObjectType getType() {
        return objectType;
    }

    /**
     *
     * @param objectType Object Type
     */
    public void setObjectType(GameObjectType objectType) {
        this.objectType = objectType;
    }

    @Override
    public int getSizeX() {
        return sizeX;
    }

    /**
     *
     * @param sizeX X size
     */
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    @Override
    public int getSizeY() {
        return sizeY;
    }

    /**
     *
     * @param sizeY Y size
     */
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    @Override
    public int getSizeZ() {
        return sizeZ;
    }

    /**
     *
     * @param sizeZ Z size
     */
    public void setSizeZ(int sizeZ) {
        this.sizeZ = sizeZ;
    }

    @Override
    public boolean isVolatile() {
        return isVolatile;
    }

    /**
     *
     * @param isVolatile Volatile flag
     */
    public void setVolatile(boolean isVolatile) {
        this.isVolatile = isVolatile;
    }

    @Override
    public boolean isDynamic() {
        return isDynamic;
    }

    /**
     *
     * @param isDynamic Dynamic flag
     */
    public void setDynamic(boolean isDynamic) {
        this.isDynamic = isDynamic;
    }

}
