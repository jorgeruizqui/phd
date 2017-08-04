package es.jor.phd.xvgdl.model.map;

import java.util.List;

import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Map generator interface
 * @author jrquinones
 *
 */
public interface IGameMapGenerator {

    /**
     * Generates the representation of the map including objects in list.
     * @param map Game map to be modified
     * @param objects Game objects to allocate
     */
    void generateMapRepresentation(IGameMap map, List<IGameObject> objects);
}
