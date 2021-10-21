package com.jrq.xvgdl.fx;

import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class FXRenderer extends Application {

    final int WIDTH = 600;
    final int HEIGHT = 400;

    double ballRadius = 40;
    double ballX = 100;
    double ballY = 200;
    double xSpeed = 4;

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 400);
        Canvas canvas = new Canvas( 600, 400 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image pacman = new Image( "pacman.png" );
        gc.drawImage(pacman, 300, 200);

/*
        Circle circle = new Circle();
        circle.setCenterX(ballX);
        circle.setCenterY(ballY);
        circle.setRadius(ballRadius);
        circle.setFill(Color.BLUE);
        root.getChildren().add(circle);*/
        stage.setScene(scene);
        stage.show();

        AnimationTimer animator = new AnimationTimer(){

            long lastNanoTime = 0;
            @Override
            public void handle(long currentNanoTime) {

                // calculate time since last update.

                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                // UPDATE
                ballX += xSpeed;

                if (ballX + ballRadius >= WIDTH)
                {
                    ballX = WIDTH - ballRadius;
                    xSpeed *= -1;
                }
                else if (ballX - ballRadius < 0)
                {
                    ballX = 0 + ballRadius;
                    xSpeed *= -1;
                }

                // RENDER
                gc.clearRect(0, 0, 600, 400);
                gc.drawImage(pacman, ballX, 200);
            }
        };

        animator.start();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
