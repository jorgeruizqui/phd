package com.jrq.xvgdl.model.map;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;

/**
 * Game Map
 */
public interface IGameMap {

    /**
     * @return Map type
     */
    GameMapType getMapType();

    /**
     * @return X Size of the map
     */
    Integer getSizeX();

    /**
     * @return Y Size of the map
     */
    Integer getSizeY();

    /**
     * @return Z Size of the map in case of 3D maps
     */
    Integer getSizeZ();

    /**
     * @return flag indicating if its toroidal
     */
    Boolean getIsToroidal();

    /**
     * @return Map File name
     */
    String getMapFile();

    /**
     * Resize Map to new values.
     *
     * @param x X Size
     * @param y Y Size
     * @param z Z Size
     */
    void resize(int x, int y, int z);

    /**
     * Generates map representation.
     *
     * @param gameContext Game Context
     */
    void generateMap(GameContext gameContext) throws XvgdlException;
}
