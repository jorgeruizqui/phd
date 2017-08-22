package es.jor.phd.xvgdl.model.object;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.ai.IGameObjectAI;

/**
 * Game Object
 *
 * @author jrquinones
 *
 */
public class GameObject implements IGameObject {

    /** Name of the game object. */
    private String name;
    /** Instance. */
    private int instance;
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

    /**
     * Artificial Intelligence associated with this object.
     */
    private IGameObjectAI objectAI;

    @Override
    public String getId() {
        return getName() + "-" + getInstance();
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getInstance() {
        return instance;
    }

    /**
     *
     * @param instance Instance
     */
    public void setInstance(int instance) {
        this.instance = instance;
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

    @Override
    public void moveTo(int x, int y, int z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /**
     *
     * @param gameObjectAI Object Artificial Intelligence
     */
    public void setAI(IGameObjectAI gameObjectAI) {
        this.objectAI = gameObjectAI;
    }

    @Override
    public void applyAI(GameContext gameContext) {
        if (objectAI != null) {
            objectAI.applyAIonObject(gameContext, this);
        }

    }

    @Override
    public String toString() {
        return "GameObject [id=" + getId() + ", name=" + name + ", instance=" + instance + ", x=" + x + ", y=" + y
                + ", z=" + z + ", intendedX=" + intendedX + ", intendedY=" + intendedY + ", intendedZ=" + intendedZ
                + ", sizeX=" + sizeX + ", sizeY=" + sizeY + ", sizeZ=" + sizeZ + ", objectType=" + objectType
                + ", isDynamic=" + isDynamic + ", isVolatile=" + isVolatile + "]";
    }

}
