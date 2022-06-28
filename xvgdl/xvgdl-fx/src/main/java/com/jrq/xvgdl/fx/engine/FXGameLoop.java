package com.jrq.xvgdl.fx.engine;

import com.jrq.xvgdl.fx.context.FXGameContext;
import com.jrq.xvgdl.fx.rules.GameRuleUtils;
import com.jrq.xvgdl.model.endcondition.IGameEndCondition;
import com.jrq.xvgdl.model.rules.GameRuleType;
import com.jrq.xvgdl.model.rules.IGameRule;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FXGameLoop extends AnimationTimer {

    private final FXGameEngine fxGameEngine;
    private final FXGameContext fxGameContext;

    private long lastNanoTime = 0;
    private boolean gameFinished;


    @Override
    public void handle(long currentNanoTime) {
        double differenceInSeconds = (currentNanoTime - lastNanoTime) / 1_000_000;
        lastNanoTime = currentNanoTime;

        // TODO Check also 'pause' option
        try {
            fxGameContext.setLoopTime(System.currentTimeMillis());
            updateState();
            // processEvents();
            processRules();
            render();
            if (differenceInSeconds >= 1) {
                fxGameContext.nextTurn();
            }
            checkEndConditions();

            if (gameFinished) {
                fxGameEngine.finishGame();
            }
        } catch (Exception e) {
            log.error("Exception in game Loop", e);
            this.fxGameEngine.finishGame(e);
        }
    }

    private void updateState() {
        fxGameContext.getFxGameObjects().forEach(fxGameObject -> fxGameObject.updateState(this.fxGameContext));
    }

    private void render() {
        // TODO size base on map properties...
        fxGameContext.getGraphicsContext().setFill(Color.grayRgb(40));
        fxGameContext.getGraphicsContext().fillRect(0, 0,
            fxGameContext.getSpriteFactors() * 28,
            fxGameContext.getSpriteFactors() * 28);
        fxGameContext.getFxGameObjects().stream()
            .filter((fxGameObject) -> fxGameObject.getSprite() != null)
            .forEach(fxGameObject -> {
                fxGameObject.getSprite().render(fxGameContext.getGraphicsContext());
            });
    }

    private void processRules() {

        for (IGameRule rule : fxGameContext.getGameRules()) {
            if (rule.getGameStates().contains(fxGameContext.getCurrentGameState())) {
                GameRuleUtils.applyGameRule(rule, fxGameContext);

                if (rule.getType().equals(GameRuleType.END_CONDITION)) {
                    gameFinished = true;
                }
            }
        }
    }

    private void checkEndConditions() {

        for (IGameEndCondition endCondition : fxGameContext.getGameEndConditions()) {
            if (endCondition.checkCondition(fxGameContext)) {
                log.info("Game end condition reached: " + endCondition.toString());
                gameFinished = true;
                if (endCondition.isWinningCondition()) {
                    //gameContext.setWinningGame(true);
                }
                break;
            }
        }
    }
}
