package es.jor.phd.xvgdl.model.map;

import java.util.List;

import es.jor.phd.xvgdl.model.object.IGameObject;
import lombok.Data;

/**
 * Game Map implementation
 *
 * @author jrquinones
 *
 */
@Data
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
    private IGameMapGenerator mapGenerator;

    /** File-based map. */
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
    public void generateMap(List<IGameObject> objectList) {
        if (this.mapGenerator != null) {
            this.mapGenerator.generateMapRepresentation(this, objectList);
        }
    }
}
