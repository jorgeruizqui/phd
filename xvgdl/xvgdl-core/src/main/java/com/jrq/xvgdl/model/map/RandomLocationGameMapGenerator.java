package com.jrq.xvgdl.model.map;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;

import java.util.Random;

/**
 * Game Map Generator basic implementations
 *
 * @author jrquinones
 */
public class RandomLocationGameMapGenerator implements IGameMapGenerator {

    @Override
    public void generateMapRepresentation(GameContext gc, IGameMap map) {

        Random r = new Random();

        for (IGameObject iGameObject : gc.getObjectsAsList()) {
            int i = r.nextInt(map.getSizeX());
            int j = r.nextInt(map.getSizeY());
            iGameObject.moveTo(i, j, 0);
        }
    }

}
