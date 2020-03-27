package com.jrq.xvgdl.model.map;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;

/**
 * Map generator interface
 *
 * @author jrquinones
 */
public interface IGameMapGenerator {

    /**
     * Generates the representation of the map including objects in list.
     *
     * @param map Game map to be modified
     * @param gc  Game Context
     */
    void generateMapRepresentation(GameContext gc, IGameMap map) throws XvgdlException;
}
