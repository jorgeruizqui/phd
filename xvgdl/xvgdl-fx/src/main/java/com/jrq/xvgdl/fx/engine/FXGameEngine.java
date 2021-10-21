package com.jrq.xvgdl.fx.engine;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.fx.input.FXKeyboardHandler;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class FXGameEngine extends Application {

    private Scene scene;
    private FXKeyboardHandler keyboardHandler;
    private GameContext gameContext = new GameContext();
    private FXGameLoop gameLoop;

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        this.scene = new Scene(root, 600, 400);

        initializeFxGameEngine("/pacmanXvgdl.xml");
        startGameLoop(stage);
    }

    private void startGameLoop(Stage stage) {
        stage.setScene(scene);
        stage.show();
        this.gameLoop.start();
    }


    public void initializeFxGameEngine(String configFile) throws XvgdlException {
        try {
            loadGameContext(configFile);
            addKeyboardListener(this.gameContext);
            addGameLoopHandler(this.gameContext);
        } catch (Exception e) {
            log.error("Exception initializing game context with file: " + configFile, e);
            throw e;
        }
    }

    private void addGameLoopHandler(GameContext gameContext) {
        this.gameLoop = new FXGameLoop(gameContext);
    }

    private void addKeyboardListener(GameContext gameContext) {
        this.keyboardHandler = new FXKeyboardHandler(gameContext);
        this.scene.setOnKeyPressed(keyboardHandler);
        this.scene.setOnKeyReleased(keyboardHandler);
    }

    private void loadGameContext(String configFile) throws XvgdlException {
        log.debug("Context to be created with file: " + configFile);
        long start = System.currentTimeMillis();
        gameContext.loadGameContext(configFile);
        long end = System.currentTimeMillis();
        log.debug("Context has been created in " + (end - start)+ " ms.");
    }
}
