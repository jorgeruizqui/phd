package com.jrq.xvgdl.spaceinvaders.model.ai;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.location.DirectionVector;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.model.object.ai.IGameObjectAI;

import java.util.Random;

/**
 * Basic Artificial Intelligence applies to an object chasing always the Player
 *
 * @author jrquinones
 *
 */
public class EnemySpaceshipAI implements IGameObjectAI {

    private Random random = new Random();

    @Override
    public void applyAIonObject(GameContext gameContext, IGameObject object) {
        // Moves the spaceship to the left
        if (object.getPosition().getX() % 2 == 0) {
            if (object.getPosition().getY() >= 2) {
                    object.moveTo(object.getPosition().getX(), object.getPosition().getY() - 1, object.getPosition().getZ());
            } else {
                object.moveTo(object.getPosition().getX() - 1, object.getPosition().getY(), object.getPosition().getZ());
            }
        } else {
            if (object.getPosition().getY() < gameContext.getGameMap().getSizeY() - 1) {
                object.moveTo(object.getPosition().getX(), object.getPosition().getY() + 1, object.getPosition().getZ());
            } else {
                object.moveTo(object.getPosition().getX() - 1, object.getPosition().getY(), object.getPosition().getZ());
            }
        }

        // With an small probability, an enemy spaceship can fire a projectile
        if (random.nextDouble() > 0.98) {
            createEnemyProjectile(object, gameContext);
        }
    }

    private void createEnemyProjectile(IGameObject object, GameContext context) {
        GameObject projectile = new GameObject();
        projectile.moveTo(object.getPosition().getX() - 1, object.getPosition().getY(), object.getPosition().getZ());
        projectile.setName("fireEnemy");
        projectile.setInstance(new Random().nextInt());
        projectile.setIsDynamic(true);
        projectile.setIsVolatile(true);
        projectile.setSizeX(1);
        projectile.setSizeY(1);
        projectile.setSizeZ(1);
        projectile.setIntendedPosition(object.getPosition().getX() - 1, object.getPosition().getY(), object.getPosition().getZ());
        projectile.setObjectType(GameObjectType.PROJECTILE);
        projectile.setSpeedFactor(5.0d);

        projectile.setDirection(new DirectionVector(new Position(-1, 0, 0)));

        // Add to game context
        context.addObject(projectile);
    }
}
