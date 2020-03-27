package com.jrq.xvgdl.model.map;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.model.object.IGameObject;
import com.jrq.xvgdl.util.XvgdlUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Game Map Generator basic implementations
 *
 * @author jrquinones
 */
@Slf4j
public class FileBasedGameMapGenerator implements IGameMapGenerator {

    @Override
    public void generateMapRepresentation(GameContext gc, IGameMap map) throws XvgdlException {

        int numberOfRows = getNumberOfRows(map);

        createMap(gc, map, numberOfRows);
    }

    private void createMap(GameContext gc, IGameMap map, int numberOfRows) throws XvgdlException {
        try (InputStream f = XvgdlUtils.getFileInputStream(map.getMapFile());
             InputStreamReader isr = new InputStreamReader(f);) {

            BufferedReader br = new BufferedReader(isr);
            int row = 0;
            String sCurrentLine = "";
            while ((sCurrentLine = br.readLine()) != null) {
                for (int col = 0; col < sCurrentLine.length(); col++) {
                    String s = sCurrentLine.substring(col, col + 1);
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
            throw new XvgdlException("Error generating map from file.", e);
        }
    }

    private int getNumberOfRows(IGameMap map) throws XvgdlException {
        int numberOfRows = 0;
        try (InputStream f = XvgdlUtils.getFileInputStream(map.getMapFile());
             InputStreamReader isr = new InputStreamReader(f);) {

            BufferedReader br = new BufferedReader(isr);
            String sCurrentLine = "";
            while ((sCurrentLine = br.readLine()) != null) {
                numberOfRows++;
            }
        } catch (Exception e) {
            log.error("Error parsing map file: " + map.getMapFile(), e);
            throw new XvgdlException("Error generating map from file.", e);
        }
        return numberOfRows;
    }
}
