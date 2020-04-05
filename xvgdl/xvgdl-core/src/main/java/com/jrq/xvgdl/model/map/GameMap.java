package com.jrq.xvgdl.model.map;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.Data;

/**
 * Game Map implementation
 *
 * @author jrquinones
 */
@Data
public class GameMap implements IGameMap {

    /**
     * Map Type.
     */
    private GameMapType mapType;

    /**
     * Size X
     */
    private Integer sizeX;

    /**
     * Size Y
     */
    private Integer sizeY;

    /**
     * Size Z
     */
    private Integer sizeZ;

    /**
     * Toroidal.
     */
    private Boolean isToroidal;

    /**
     * Generator.
     */
    private IGameMapGenerator mapGenerator;

    /**
     * File-based map.
     */
    private String mapFile;

    /**
     * Constructor.
     */
    public GameMap() {
        // Default will be MAP_2D
        this.mapType = GameMapType.MAP_2D;
    }

    @Override
    public void resize(int x, int y, int z) {
        setSizeX(x);
        setSizeY(y);
        setSizeZ(z);
    }

    @Override
    public void generateMap(GameContext gc) throws XvgdlException {
        if (this.mapGenerator != null) {
            this.mapGenerator.generateMapRepresentation(gc, this);
        }
    }
}
