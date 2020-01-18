package es.jor.phd.xvgdl.model.map;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.IGameObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Game Map Generator basic implementations
 *
 * @author jrquinones
 *
 */
@Slf4j
public class FileBasedGameMapGenerator implements IGameMapGenerator {

    @Override
    public void generateMapRepresentation(GameContext gc, IGameMap map) {

        try (InputStream f = new FileInputStream(map.getMapFile());
             InputStreamReader isr = new InputStreamReader(f);) {

            String sCurrentLine = "";
            int numberOfRows = 0;
            f.mark(0);
            BufferedReader br = new BufferedReader(isr);
            while ((sCurrentLine = br.readLine()) != null) {
                numberOfRows++;
            }
            f.reset();
            br = new BufferedReader(isr);
            int row = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                for (int col = 0; col < sCurrentLine.length(); col++) {
                    String s = new String(sCurrentLine.substring(col, col + 1));
                    IGameObject go = gc.getObjectsAsList().stream()
                            .filter(o -> o.getName().toLowerCase().startsWith(s.toLowerCase())).findFirst()
                            .orElse(null);
                    if (go != null) {
                        if (go.isLocatedAnyWhereInMap()) {
                            IGameObject newInstance = go.copy();
                            newInstance.moveTo(numberOfRows - row, col, 0);
                            gc.addObject(newInstance);
                        } else {
                            go.moveTo(numberOfRows - row, col, 0);
                        }
                    }
                }
                row++;
            }

        } catch (Exception e) {
            log.error("Error parsing map file: " + map.getMapFile(), e);
        }
    }

}
