package es.jor.phd.xvgdl.model.map;

import java.util.Random;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Game Map Generator basic implementations
 *
 * @author jrquinones
 *
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
