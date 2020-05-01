package com.jrq.xvgdl.spaceinvaders.model.event;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.context.xml.GameEventDefinition;
import com.jrq.xvgdl.model.event.IGameEvent;
import com.jrq.xvgdl.model.event.IGameEventExecutor;
import com.jrq.xvgdl.model.event.KeyboardGameEvent;
import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

/**
 * Player shoot event.
 * Creates a new object of type PROJECTILE
 *
 */
public class PlayerShootEvent extends KeyboardGameEvent {

    @Getter
    @Setter
    private String objectName;

    private static final int MAX_NUMBER_OF_SHOOTS = 4;

    public PlayerShootEvent() {
        this.executor = new PlayerShootExecutor();
    }

    @Override
    public Boolean isConsumable() {
        return true;
    }
    
    @Override
    protected void updateDefinitionFields() {
        setObjectName(getGameEventDefinition().getObjectName());
    }

    private boolean isAllowedToShoot(GameContext gameContext) {
        return gameContext.getObjectsListByName(getObjectName()).size() < MAX_NUMBER_OF_SHOOTS;
    }

    public class PlayerShootExecutor implements IGameEventExecutor {

        private boolean firstTime = true;

        @Override
        public void executeEvent(IGameEvent event, GameContext context) {
            
             GameEventDefinition definition = getGameEventDefinition();
            
            // Create the game object
            IGameObject player = context.getCurrentGamePlayer();
            context.getObjectsListByName("firePlayer");
            if (player != null && !firstTime && isAllowedToShoot(context)) {
                GameObject projectile = new GameObject();
                projectile.moveTo(player.getX(), player.getY(), player.getZ());
                projectile.setName(definition.getObjectName());
                projectile.setInstance(new Random().nextInt());
                projectile.setIsDynamic(true);
                projectile.setIsVolatile(true);
                projectile.setSizeX(1);
                projectile.setSizeY(1);
                projectile.setSizeZ(1);
                projectile.setIntendedPosition(player.getX(), player.getY(), player.getZ());
                projectile.setObjectType(GameObjectType.PROJECTILE);
                projectile.setSpeedFactor(5.0d);

                projectile.setDirection(new DirectionVector(new Position(1, 0, 0)));

                // Add to game context
                context.addObject(projectile);
            }
            firstTime = false;
        }
    }

}
