package es.jor.phd.xvgdl.model.map;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Map generator interface
 * 
 * @author jrquinones
 *
 */
public interface IGameMapGenerator {

    /**
     * Generates the representation of the map including objects in list.
     * 
     * @param map Game map to be modified
     * @param gc Game Context 
     */
    void generateMapRepresentation(GameContext gc, IGameMap map);
}
