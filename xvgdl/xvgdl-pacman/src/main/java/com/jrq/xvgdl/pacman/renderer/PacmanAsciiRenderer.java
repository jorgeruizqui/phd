package com.jrq.xvgdl.pacman.renderer;

import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.renderer.BasicAsciiRenderer;

import java.util.Map;

/**
 * Basic ascii renderer.
 * Assigns an Ascii character to every kind of {@link GameObjectType}
 *
 * @author jrquinones
 *
 */
public class PacmanAsciiRenderer extends BasicAsciiRenderer {

    private static final Map<String, Character> renderCodes = Map.of(
            "smallDot", Character.valueOf('\u25cb'),
            "bigDot", Character.valueOf('\u25c9'),
            "ghost", Character.valueOf('\u15e3'),
            "pacman", Character.valueOf('\u2687'),
            "wall", Character.valueOf('\u2263'),
            "cherry", Character.valueOf('\u2730')
    );

    protected void printMap() {
        char[][] array = new char[this.gameContext.getGameMap().getSizeX()][this.gameContext.getGameMap().getSizeY()];

        for (int row = array.length - 1; row >= 0; row--) {
            for (int col = 0; col < array[row].length; col++) {
                IGameObject gameObject = this.gameContext.getObjectAt(row + 1, col, 0);
                if (gameObject != null) {
                    array[row][col] = renderCodes.get(gameObject.getName());
                } else {
                    array[row][col] = ' ';
                }
            }
        }

        for (int i = array.length - 1; i >= 0; i--) {
            System.out.println(array[i]);
        }
    }
}
