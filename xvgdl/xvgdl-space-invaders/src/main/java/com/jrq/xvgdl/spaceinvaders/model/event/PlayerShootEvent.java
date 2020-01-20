package com.jrq.xvgdl.spaceinvaders.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameEventDefinition;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.event.IGameEventExecutor;
import com.jrq.xvgdl.model.event.KeyboardGameEvent;
import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

/**
 * Spawn item event implementation
 *
 */
public class PlayerShootEvent extends KeyboardGameEvent {

    /** XML Object reference tag. */
    public static final String XML_OBJECT_NAME = "objectName";

    @Getter
    @Setter
    private String objectName;

    /**
     * Constructor.
     */
    public PlayerShootEvent() {
        this.executor = new PlayerShootExecutor();
    }

    @Override
    public boolean isConsumable() {
        return true;
    }
    
    @Override
    protected void updateDefinitionFields() {
        setObjectName(getGameEventDefinition().getObjectName());
    }

    /**
     * Spawn Item executor
     * 
     * @author Jor
     *
     */
    public class PlayerShootExecutor implements IGameEventExecutor {
        
        private boolean firstTime = true;

        @Override
        public void executeEvent(IGameEvent event, GameContext context) {
            
             GameEventDefinition definition = getGameEventDefinition();
            
            // Create the game object
            IGameObject player = context.getCurrentGamePlayer();
            if (player != null && !firstTime) {
                GameObject projectile = new GameObject();
                projectile.moveTo(player.getX(), player.getY(), player.getZ());
                projectile.setName(definition.getObjectName());
                projectile.setInstance(new Random().nextInt());
                projectile.setDynamic(true);
                projectile.setVolatile(true);
                projectile.setSizeX(1);
                projectile.setSizeY(1);
                projectile.setSizeZ(1);
                projectile.setIntendedPosition(player.getX(), player.getY(), player.getZ());
                projectile.setObjectType(GameObjectType.PROJECTILE);

                projectile.setDirection(new DirectionVector(0, 1, 0));

                // Add to game context
                context.addObject(projectile);
            }
            firstTime = false;
        }
    }
}
