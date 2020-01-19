package com.jrq.xvgdl.spaceinvaders.model.map;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.map.IGameMap;
import com.jrq.xvgdl.model.map.IGameMapGenerator;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        try (InputStream f = new FileInputStream(map.getMapFile())) {
            String sCurrentLine = "";
//            char [][] mapRepresentation;
            int numberOfRows = 0;
            BufferedReader br = new BufferedReader(new InputStreamReader(f));

            while ((sCurrentLine = br.readLine()) != null) {
                numberOfRows++;
            }
            f.close();
            InputStream readValues = new FileInputStream(map.getMapFile());
            br = new BufferedReader(new InputStreamReader(readValues));

            int y = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                for (int x = 0; x < sCurrentLine.length(); x++) {
                    String s = new String(sCurrentLine.substring(x, x + 1));
                    IGameObject go = gc.getObjectsAsList().stream()
                            .filter(o -> o.getName().toLowerCase().startsWith(s.toLowerCase())).findFirst()
                            .orElse(null);
                    if (go != null) {
                        if (go.isLocatedAnyWhereInMap()) {
                            IGameObject newInstance = go.copy();
                            newInstance.moveTo(x, numberOfRows - y, 0);
                            gc.addObject(newInstance);
                        } else {
                            go.moveTo(x, numberOfRows - y, 0);
                        }
                    }
                }
                y++;
            }
            readValues.close();

        } catch (Exception e) {
            log.error("Error parsing map file: " + map.getMapFile(), e);
        }
    }

}
