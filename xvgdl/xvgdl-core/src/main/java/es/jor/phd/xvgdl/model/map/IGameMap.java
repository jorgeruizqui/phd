package es.jor.phd.xvgdl.model.map;

import java.util.List;

import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Game Map
 */
public interface IGameMap {

    /**
     *
     * @return complete Map
     */
    IGameObject[][][] getMapRepresentation();

    /**
     *
     * @return Map type
     */
    GameMapType getMapType();

    /**
     *
     * @return X Size of the map
     */
    int getSizeX();

    /**
     *
     * @return Y Size of the map
     */
    int getSizeY();

    /**
     *
     * @return Z Size of the map in case of 3D maps
     */
    int getSizeZ();

    /**
     *
     * @return flag indicating if its toroidal
     */
    boolean isToroidal();

    /**
     *
     * @return Map generator
     */
    IGameMapGenerator getMapGenerator();

    /**
     *
     * @return Map File name
     */
    String getMapFile();

    /**
     * Resize Map to new values.
     * @param x X Size
     * @param y Y Size
     * @param z Z Size
     */
    void resize(int x, int y, int z);

    /**
     * Generates map representation.
     * @param objectList Object list
     */
    void generateMap(List<IGameObject> objectList);
}
