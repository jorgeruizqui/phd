package es.jor.phd.xvgdl.renderer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.GamePlayer;
import es.jor.phd.xvgdl.model.object.IGameObject;

/**
 * Basic ascii renderer.
 * Asgins an Ascii character to every kind of {@link GameObjectType}
 *
 * @author jrquinones
 *
 */
public class BasicAsciiRenderer implements IGameRenderer {

    /** Game Context. */
    private GameContext gameContext;

    @Override
    public void initializeRenderer(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void render() {

        if (SystemUtils.IS_OS_WINDOWS) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else if (SystemUtils.IS_OS_LINUX) {
            try {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

        // 1- Take Layout
        // 2. Rendering:
        // 2.1 Render the Top|Right|Left|bottom components
        GamePlayer gp = this.gameContext.getCurrentGamePlayer();
        System.out.println("Player : " + gp.getName() + " - Score: " + gp.getScore() + " - Lives: " + gp.getLives());

        // 2.2 Render the game screen.

        char[] arraySlash = new char[this.gameContext.getGameMap().getSizeX()];
        Arrays.fill(arraySlash, 0, arraySlash.length, '-');
        System.out.println(arraySlash);

        char[][] array = new char[this.gameContext.getGameMap().getSizeX()][this.gameContext.getGameMap().getSizeY()];

        // 2.2.1 Render Map Walls
        // 2.2.2 Render Map Items
        // 2.2.3 Render Map Enemies
        // 2.2.4 Render Map Player

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array[i].length; j++) {
                IGameObject gameObject = this.gameContext.getObjectAt(i, j, 0);
                if (gameObject != null) {
                    array[i][j] = gameObject.getName().charAt(0);
                } else {
                    array[i][j] = ' ';
                }
            }
        }
        array[gp.getX()][gp.getY()] = gp.getName().charAt(0);

        // Print the array in reverse order
        for (int i = array.length - 1; i >= 0; i--) {
            System.out.println(array[i]);
        }

        // Print latest row with slashes
        System.out.println(arraySlash);

    }

}
