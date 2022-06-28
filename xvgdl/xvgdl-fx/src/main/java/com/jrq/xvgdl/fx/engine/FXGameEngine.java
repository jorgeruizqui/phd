package com.jrq.xvgdl.fx.engine;

import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.fx.context.FXGameContext;
import com.jrq.xvgdl.fx.input.FXKeyboardHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO Complete the fx game engine with :
/*
    1. Adding ghost with an IA to be able to chase pacman. This is just moving trying to not collision with a wall.
    2. Updating the reset method in FXGame Object to restore the movement properly
 */
@NoArgsConstructor
@Slf4j
public class FXGameEngine extends Application {

    private Scene scene;
    private Stage stage;
    private FXKeyboardHandler keyboardHandler;
    private FXGameContext fxGameContext = new FXGameContext();
    private FXGameLoop gameLoop;
    @Getter
    private GraphicsContext graphicsContext;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Group root = new Group();
        // TODO Check map properties in XML
        this.scene = new Scene(root, 30 * 28 + 50, 30 * 28 + 50);
        Canvas canvas = new Canvas(30 * 28, 30 * 28);
        root.getChildren().add(canvas);
        graphicsContext = canvas.getGraphicsContext2D();

        initializeFxGameEngine("/pacmanXvgdlFx.xml");

        this.stage.setScene(scene);
        this.stage.show();

        startGameLoop(this.stage);
    }

    public void finishGame() {
        this.gameLoop.stop();
        Group root = new Group();
        GridPane gridPane = new GridPane();
        Label label = new Label("Game Finished!");
        gridPane.add(label, 0, 0);
        root.getChildren().add(gridPane);

        this.scene = new Scene(root, 30 * 28 + 50, 30 * 28 + 50);
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void finishGame(Exception e) {
        finishGame();
    }

    private void startGameLoop(Stage stage) {
        fxGameContext.setStartTime(System.currentTimeMillis());
        fxGameContext.setLoopTime(fxGameContext.getStartTime());
        this.gameLoop.start();
        // TODO This here? or launch an event? or something? renderGameFinished();
    }


    public void initializeFxGameEngine(String configFile) throws XvgdlException {
        try {
            loadGameContext(configFile);
            addKeyboardListener();
            addGameLoopHandler(fxGameContext);
        } catch (Exception e) {
            log.error("Exception initializing game context with file: " + configFile, e);
            throw e;
        }
    }

    private void addGameLoopHandler(FXGameContext fxGameContext) {
        this.gameLoop = new FXGameLoop(this, this.fxGameContext);
    }

    private void addKeyboardListener() {
        this.keyboardHandler = new FXKeyboardHandler(this.fxGameContext);
        this.scene.setOnKeyPressed(keyboardHandler);
        this.scene.setOnKeyReleased(keyboardHandler);
    }

    private void loadGameContext(String configFile) throws XvgdlException {
        log.debug("Context to be created with file: " + configFile);
        long start = System.currentTimeMillis();
        fxGameContext.loadGameContext(configFile);
        fxGameContext.setGraphicsContext(this.graphicsContext);
        long end = System.currentTimeMillis();
        log.debug("Context has been created in " + (end - start) + " ms.");
    }
}
