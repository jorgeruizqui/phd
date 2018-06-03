package es.jor.phd.xvgdl.model.event;

import java.util.List;
import java.util.Random;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Spawn item event implementation
 *
 */
public class SpawnItemEvent extends AGameEvent {

    /** XML Object reference tag. */
    public static final String XML_OBJECT_NAME = "objectName";

    @Getter
    @Setter
    private String objectName;

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
    
    @Override
    protected void updateDefinitionFields() {
        setObjectName(getGameEventDefinition().getStringValue(XML_OBJECT_NAME));
    }

    /**
     * Spawn Item executor
     * 
     * @author Jor
     *
     */
    public class SpawnItemExecutor implements IGameEventExecutor {

        @Override
        public void executeEvent(IGameEvent event, GameContext context) {
            // Create the game object
            List<IGameObject> list = context.getObjectsListByName(getObjectName());
            if (list != null && !list.isEmpty()) {
                IGameObject go = list.get(0);
                IGameObject goCloned = go.copy();

                // Set the cloned object in a random situation
                Random r = new Random();
                goCloned.moveTo(r.nextInt(context.getGameMap().getSizeX()), r.nextInt(context.getGameMap().getSizeY()),
                        0);
                // Add to game context
                context.addObject(goCloned);
            }
        }
    }
}
