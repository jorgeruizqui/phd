package es.jor.phd.xvgdl.model.map;

import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Game Map implementation
 * @author jrquinones
 *
 */
public class GameMap implements IGameMap {

    /** Game map instance. */
    private IGameObject[][] map2DRepresentation;

//    /** Type. */
//    public String XMLATTR_TYPE = "type";
//
//    /** Size. */
//    public int sizeX = "size";
//
//    /** Toroidal. */
//    public boolean toroidal = "toroidal";

    /** Generator. */
    public static final String XMLATTR_GENERATOR = "generator";

    @Override
    public IGameObject[][] getCompleteMap() {
        return map2DRepresentation;
    }

}
