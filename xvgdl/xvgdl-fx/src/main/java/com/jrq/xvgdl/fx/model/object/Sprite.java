package com.jrq.xvgdl.fx.model.object;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sprite {

    private Image image;
    private double positionX;
    private double positionY;
    private double lastPositionX;
    private double lastPositionY;

    public void update(double velocityX, double velocityY) {
        lastPositionX = positionX;
        lastPositionY = positionY;
        positionX += velocityX;
        positionY += velocityY;
    }

    public double getHeight() {
        return image != null ? image.getHeight() : 1d;
    }

    public double getWidth() {
        return image != null ? image.getWidth() : 1d;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionY, positionX);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionY, positionX, image.getWidth(), image.getHeight());
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }
}
