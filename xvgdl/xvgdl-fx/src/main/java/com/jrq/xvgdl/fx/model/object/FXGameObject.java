package com.jrq.xvgdl.fx.model.object;

import com.jrq.xvgdl.fx.context.FXGameContext;
import com.jrq.xvgdl.model.location.Position;
import com.jrq.xvgdl.model.object.GameObject;
import com.jrq.xvgdl.model.object.IGameObject;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FXGameObject extends GameObject {

    @Getter
    @Setter
    private Sprite sprite;

    private double velocityX = 0d;
    private double velocityY = 0d;

    public static FXGameObject fromGameObject(FXGameContext fxGameContext, IGameObject gameObject) {
        FXGameObject fxGameObject = new FXGameObject();
        fxGameObject.setAi(gameObject.getAi());
        fxGameObject.setIsDynamic(gameObject.getIsDynamic());
        fxGameObject.setInstance(gameObject.getInstance());
        fxGameObject.setFrozen(gameObject.isFrozen());
        fxGameObject.setName(gameObject.getName());
        fxGameObject.setObjectType(gameObject.getObjectType());
        fxGameObject.setIsVolatile(gameObject.getIsVolatile());
        fxGameObject.setInitialSpeedFactor(gameObject.getInitialSpeedFactor());
        initializePositions(fxGameObject, gameObject);

        if (gameObject.getSpriteName() != null) {
            initializeSprite(fxGameContext, gameObject.getSpriteName(), fxGameObject);
        }

        return fxGameObject;
    }

    public void updateVelocityX(double factor) {
        velocityX = factor;
    }

    public void updateVelocityY(double factor) {
        velocityY = factor;
    }

    public void updateState(FXGameContext fxGameContext) {
        if (sprite != null) {
            sprite.update(velocityX * getSpeedFactor(), velocityY * getSpeedFactor());
            setPosition((int) (sprite.getPositionX() / fxGameContext.getSpriteFactors()),
                (int) (sprite.getPositionY() / fxGameContext.getSpriteFactors()), 0);
        }
        if (getUpdatedAt() > 0) {
            applyAI(fxGameContext);
        }
        setUpdatedAt(fxGameContext.getLoopTime());
    }

    public void resetSpeed() {
        velocityX = velocityY = 0;
    }

    @Override
    public void moveTo(int x, int y, int z) {
        if (x > getIntendedPosition().getX()) {
            velocityX += 1;
        }
        if (x < getIntendedPosition().getX()) {
            velocityX -= 1;
        }
        if (y > getIntendedPosition().getY()) {
            velocityY += 1;
        }
        if (y < getIntendedPosition().getY()) {
            velocityY -= 1;
        }
        super.moveTo(x, y, z);
    }

    @Override
    public void resetMove(IGameObject collisioning) {

        if (collisioningInX(collisioning) && velocityX != 0) {
            sprite.setPositionX(sprite.getLastPositionX());
//            velocityX = 0;
        }

        if (collisioningInY(collisioning) && velocityY != 0) {
            sprite.setPositionY(sprite.getLastPositionY());
//            velocityY = 0;
        }

        setPosition(getLastPosition().getX(), getLastPosition().getY(), getLastPosition().getZ());
    }

    private boolean collisioningInX(IGameObject collisioning) {
        return
            ((FXGameObject) collisioning).getSprite().getBoundary().getMaxX() > this.getSprite().getBoundary().getMinX()
                && ((FXGameObject) collisioning).getSprite().getBoundary().getMinX() < this.getSprite().getBoundary()
                .getMaxX();
    }

    private boolean collisioningInY(IGameObject collisioning) {
        return
            ((FXGameObject) collisioning).getSprite().getBoundary().getMaxY() > this.getSprite().getBoundary().getMinY()
                && ((FXGameObject) collisioning).getSprite().getBoundary().getMinY() < this.getSprite().getBoundary()
                .getMaxY();
    }

    public void resetMovePlayerPlays() {

        if (velocityX > 0) {
            sprite.setPositionX(sprite.getPositionX() - velocityX * getSpeedFactor());
        } else if (velocityX < 0) {
            sprite.setPositionX(sprite.getPositionX() + velocityX * getSpeedFactor());
        }

        if (velocityY > 0) {
            sprite.setPositionY(sprite.getPositionY() - velocityY * getSpeedFactor());
        } else if (velocityY < 0) {
            sprite.setPositionY(sprite.getPositionY() + velocityY * getSpeedFactor());
        }
        setPosition(getLastPosition().getX(), getLastPosition().getY(), getLastPosition().getZ());
    }

    private static void initializeSprite(FXGameContext fxGameContext, String spriteName, FXGameObject fxGameObject) {
        try {
            fxGameObject.setSpriteName(spriteName);
            Image spriteImage = new Image(fxGameObject.getSpriteName());
            fxGameObject.setSprite(Sprite.builder().image(spriteImage)
                .positionX(fxGameObject.getPosition().getX() * fxGameContext.getSpriteFactors() + ((fxGameContext
                    .getSpriteFactors() - spriteImage.getWidth()) / 2))
                .positionY(fxGameObject.getPosition().getY() * fxGameContext.getSpriteFactors() + ((fxGameContext
                    .getSpriteFactors() - spriteImage.getHeight()) / 2))
                .build());
        } catch (Exception e) {
            log.error("Sprite image could not be initialized for path {}", fxGameObject.getSpriteName());
        }
    }

    private static void initializePositions(FXGameObject fxGameObject, IGameObject gameObject) {
        fxGameObject.setX(gameObject.getPosition().getX());
        fxGameObject.setY(gameObject.getPosition().getY());
        fxGameObject.setZ(gameObject.getPosition().getZ());
        fxGameObject.setLastPosition(
            new Position(
                gameObject.getLastPosition().getX(),
                gameObject.getLastPosition().getY(),
                gameObject.getLastPosition().getZ()));
        fxGameObject.setInitialPosition(
            gameObject.getInitialPosition().getX(),
            gameObject.getInitialPosition().getY(),
            gameObject.getInitialPosition().getZ());
    }
}
