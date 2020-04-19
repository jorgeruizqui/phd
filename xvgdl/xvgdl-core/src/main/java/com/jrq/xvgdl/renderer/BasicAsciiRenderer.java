package com.jrq.xvgdl.renderer;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.engine.GameEngine;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.GamePlayer;
import com.jrq.xvgdl.model.object.IGameObject;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Basic ascii renderer.
 * Asgins an Ascii character to every kind of {@link GameObjectType}
 *
 * @author jrquinones
 */
public class BasicAsciiRenderer extends AGameRenderer {

    @Override
    public void initializeRenderer(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void render() {
        clearScreen();
        printPlayerInfo();
        printSlashs();
        printMap();
        printSlashs();
        printGamePlayTime();
    }

    @Override
    public void renderGameFinished() {
        clearScreen();
        printSlashs();
        IntStream.range(0, 5).forEach(i -> printWhites());
        printEndGame();
        IntStream.range(0, 5).forEach(i -> printWhites());
        printSlashs();
        printPlayerInfo();
    }

    private void printGamePlayTime() {
        System.out.println(
                "Gameplay Time: "
                        + GameEngine.getInstance().getGameContext().getTimePlayed() / 1000
                        + " Seg.");
    }

    private void printEndGame() {
        if (gameContext.isWinningGame()) {
            System.out.println("Player Wins :) !!!");
        } else {
            System.out.println("Game Over :( ");
        }
    }

    protected void printMap() {
        char[][] array = new char[this.gameContext.getGameMap().getSizeX()][this.gameContext.getGameMap().getSizeY()];

        for (int row = array.length - 1; row >= 0; row--) {
            for (int col = 0; col < array[row].length; col++) {
                IGameObject gameObject = this.gameContext.getObjectAt(row + 1, col, 0);
                if (gameObject != null) {
                    char c = gameObject.getObjectType().equals(GameObjectType.PROJECTILE) ?
                            '*'
                            : gameObject.getName().charAt(0);
                    array[row][col] = c;
                } else {
                    array[row][col] = ' ';
                }
            }
        }

        for (int i = array.length - 1; i >= 0; i--) {
            System.out.println(array[i]);
        }
    }

    private void printSlashs() {
        char[] arraySlash = new char[this.gameContext.getGameMap().getSizeY()];
        Arrays.fill(arraySlash, 0, arraySlash.length, '-');
        System.out.println(arraySlash);
    }

    private void printWhites() {
        char[] arrayWhites = new char[this.gameContext.getGameMap().getSizeY()];
        Arrays.fill(arrayWhites, 0, arrayWhites.length, ' ');
        System.out.println(arrayWhites);
    }

    protected void printPlayerInfo() {
        GamePlayer gp = this.gameContext.getCurrentGamePlayer();
        System.out.println("Score: " + gp.getScore() + " - Lives: " + gp.getLives());
    }

    private void clearScreen() {
        if (SystemUtils.IS_OS_WINDOWS) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
