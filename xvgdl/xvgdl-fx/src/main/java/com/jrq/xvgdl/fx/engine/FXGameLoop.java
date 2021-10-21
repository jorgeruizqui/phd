package com.jrq.xvgdl.fx.engine;

import com.jrq.xvgdl.context.GameContext;
import javafx.animation.AnimationTimer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FXGameLoop extends AnimationTimer  {

    private final GameContext gameContext;

    private long lastNanoTime = 0;
    @Override
    public void handle(long currentNanoTime) {
        System.out.println("Animator ellapsed time :" + (currentNanoTime - lastNanoTime));
        lastNanoTime = currentNanoTime;
    }
}
