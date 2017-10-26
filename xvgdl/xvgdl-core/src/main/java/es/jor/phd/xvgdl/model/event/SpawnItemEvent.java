package es.jor.phd.xvgdl.model.event;

import java.util.List;
import java.util.Random;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Spawn item event implementation
 *
 */
public class SpawnItemEvent extends AGameEvent {

    /** XML Object reference tag. */
    public static final String XML_OBJECT_NAME = "objectName";

    /**
     * Spawn Item executor
     * @author Jor
     *
     */
    public class SpawnItemExecutor implements IGameEventExecutor {

        @Override
        public void executeEvent(IGameEvent event, GameContext context) {
            // Create the game object
            String objectName = event.getGameEventDefinition().getStringValue(XML_OBJECT_NAME);
            List<IGameObject> list = context.getObjectsListByName(objectName);
            if (list != null && list.size() > 0) {
                IGameObject go = list.get(0);
                IGameObject goCloned = go.clone();

                // Set the cloned object in a random situation
                Random r = new Random();
                goCloned.moveTo(r.nextInt(context.getMap().getSizeX()),
                        r.nextInt(context.getMap().getSizeY()),
                        0);
                // Add to game context
                context.addObject(goCloned);
            }

        }

    }

    /**
     * Constructor.
     */
    public SpawnItemEvent() {
        this.executor = new SpawnItemExecutor();
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

}
