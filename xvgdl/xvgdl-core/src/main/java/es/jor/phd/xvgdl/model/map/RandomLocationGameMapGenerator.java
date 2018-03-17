package es.jor.phd.xvgdl.model.map;

import java.util.List;
import java.util.Random;

import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Game Map Generator basic implementations
 *
 * @author jrquinones
 *
 */
public class RandomLocationGameMapGenerator implements IGameMapGenerator {

    @Override
    public void generateMapRepresentation(IGameMap map, List<IGameObject> objects) {

        Random r = new Random();

        for (IGameObject iGameObject : objects) {
            int i = r.nextInt(map.getSizeX());
            int j = r.nextInt(map.getSizeY());
            iGameObject.moveTo(i, j, 0);
        }
    }

}
