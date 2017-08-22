package es.jor.phd.xvgdl.model.map;

import java.util.Arrays;

import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Game Map implementation
 *
 * @author jrquinones
 *
 */
public class GameMap implements IGameMap {

    /** Game map instance. */
    private IGameObject[][][] mapRepresentation;

    /** Map Type. */
    private GameMapType mapType;

    /** Size X */
    private int sizeX;

    /** Size Y */
    private int sizeY;

    /** Size Z */
    private int sizeZ;

    /** Toroidal. */
    private boolean toroidal;

    /** Generator. */
    private IGameMapGenerator generator;

    /**
     * Constructor.
     */
    public GameMap() {
        // Default will be MAP_2D
        this.mapType = GameMapType.MAP_2D;
    }

    @Override
    public IGameObject[][][] getMapRepresentation() {
        return mapRepresentation;
    }

    /**
     *
     * @param mapRepresentation Map representation
     */
    public void setMapRepresentation(IGameObject[][][] mapRepresentation) {
        this.mapRepresentation = mapRepresentation;
    }

    @Override
    public GameMapType getMapType() {
        return mapType;
    }

    /**
     *
     * @param mapType Map Type
     */
    public void setMapType(GameMapType mapType) {
        this.mapType = mapType;
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
     * @param sizeZ Z Size
     */
    public void setSizeZ(int sizeZ) {
        this.sizeZ = sizeZ;
    }

    @Override
    public boolean isToroidal() {
        return toroidal;
    }

    /**
     *
     * @param toroidal Toroidal flag
     */
    public void setToroidal(boolean toroidal) {
        this.toroidal = toroidal;
    }

    @Override
    public IGameMapGenerator getMapGenerator() {
        return generator;
    }

    /**
     *
     * @param generator Map generator
     */
    public void setGenerator(IGameMapGenerator generator) {
        this.generator = generator;
    }

    @Override
    public String toString() {
        return "GameMap [mapRepresentation=" + Arrays.toString(mapRepresentation) + ", mapType=" + mapType + ", sizeX="
                + sizeX + ", sizeY=" + sizeY + ", sizeZ=" + sizeZ + ", toroidal=" + toroidal + ", generator="
                + generator + "]";
    }


}
