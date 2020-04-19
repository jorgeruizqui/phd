package com.jrq.xvgdl.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;


public class Speedup extends AGameEvent {

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
        setObjectName(getGameEventDefinition().getObjectName());
        setValue(Optional.of(getGameEventDefinition().getValue()).orElse(0d));
    }

    @Override
    public Boolean isConsumable() {
        return false;
    }

    /**
     * Speedup executor
     *
     * @author Jor
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
