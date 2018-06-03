package es.jor.phd.xvgdl.model.event;

import java.util.List;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;
import lombok.Getter;
import lombok.Setter;


public class Speedup extends AGameEvent {

    /** XML Object reference tag. */
    public static final String XML_OBJECT_NAME = "objectName";
    private static final String XML_VALUE = "value";

    @Getter
    @Setter
    private String objectName;
    
    @Getter
    @Setter
    private Double value;

    /**
     * Constructor.
     */
    public Speedup() {
        this.executor = new SpeedupExecutor();
    }
    
    @Override
    protected void updateDefinitionFields() {
        setObjectName(getGameEventDefinition().getStringValue(XML_OBJECT_NAME));
        setValue(getGameEventDefinition().getDoubleValue(XML_VALUE, 1.0));
    }

    @Override
    public boolean isConsumable() {
        return false;
    }

    /**
     * Speedup executor
     * 
     * @author Jor
     *
     */
    public class SpeedupExecutor implements IGameEventExecutor {

        @Override
        public void executeEvent(IGameEvent event, GameContext context) {
            // Create the game object
            List<IGameObject> list = context.getObjectsListByName(getObjectName());
            if (list != null && !list.isEmpty()) {
                IGameObject go = list.get(0);
                go.setSpeedFactor(getValue());
            }
        }
    }
}
