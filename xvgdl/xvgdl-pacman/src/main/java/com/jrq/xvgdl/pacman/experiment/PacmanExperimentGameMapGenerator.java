package com.jrq.xvgdl.pacman.experiment;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.map.IGameMap;
import com.jrq.xvgdl.model.map.IGameMapGenerator;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Game Map Generator basic implementations
 *
 * @author jrquinones
 *
 */
@Slf4j
public class PacmanExperimentGameMapGenerator implements IGameMapGenerator {

    private Random random = new Random();

    @Override
    public void generateMapRepresentation(GameContext gc, IGameMap map) throws XvgdlException {
        allocateObjectsInMap(gc);
    }

    private void allocateObjectsInMap(GameContext gc) {
        // Allocate Items
        gc.getObjectsListByType(GameObjectType.ITEM).forEach(o -> allocateObject(gc, o));
        // Allocate Ghosts
        gc.getObjectsListByType(GameObjectType.ENEMY).forEach(o -> allocateObject(gc, o));
        // Allocate Player
        IGameObject player = gc.getCurrentGamePlayer();
        allocateObject(gc, player);
    }

    private void allocateObject(GameContext gameContext, IGameObject object) {

        boolean isAllocated = false;
        while (!isAllocated) {
            int x = random.nextInt(gameContext.getGameMap().getSizeX());
            int y = random.nextInt(gameContext.getGameMap().getSizeY());

            if (gameContext.getObjectAt(x, y, 0) == null) {
                object.setPosition(Position.builder().x(x).y(y).z(0).build());
                object.setInitialPosition(x, y, 0);
                object.setIntendedPosition(x, y, 0);
                isAllocated = true;
            }
        }
    }
}
