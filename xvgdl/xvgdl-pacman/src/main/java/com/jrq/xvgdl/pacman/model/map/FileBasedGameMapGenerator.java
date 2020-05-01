package com.jrq.xvgdl.pacman.model.map;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.model.map.IGameMap;
import com.jrq.xvgdl.model.map.IGameMapGenerator;
import com.jrq.xvgdl.model.object.GamePlayer;
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
 *
 */
@Slf4j
public class FileBasedGameMapGenerator implements IGameMapGenerator {

    @Override
    public void generateMapRepresentation(GameContext gc, IGameMap map) throws XvgdlException {

        int numberOfRows = getNumberOfRows(map);

        allocateObjectsInMap(gc, map, numberOfRows);
    }

    private void allocateObjectsInMap(GameContext gc, IGameMap map, int numberOfRows) throws XvgdlException {

        try (InputStream f = XvgdlUtils.getFileInputStream(map.getMapFile())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(f));

            int y = 0;
            String sCurrentLine = "";
            while ((sCurrentLine = br.readLine()) != null) {
                for (int x = 0; x < sCurrentLine.length(); x++) {
                    String s = sCurrentLine.substring(x, x + 1);
                    allocateObjectFromCharacter(gc, numberOfRows, y, x, s);
                }
                y++;
            }

        } catch (Exception e) {
            throw new XvgdlException("Error parsing map file: " + map.getMapFile(), e);
        }
    }

    private void allocateObjectFromCharacter(GameContext gc, int numberOfRows, int y, int x, String s) {
        IGameObject go = gc.getObjectsAsList().stream()
                .filter(o -> o.getName().toLowerCase().startsWith(s.toLowerCase())).findFirst()
                .orElse(null);
        if (go != null) {
            if (go.isLocatedAnyWhereInMap()) {
                IGameObject newInstance = go.copy();
                newInstance.moveTo(numberOfRows - y -1, x,0);
                gc.addObject(newInstance);
            } else {
                go.moveTo(numberOfRows - y -1, x,0);
                setPlayerAsCurrent(go);
            }
        }
    }

    private void setPlayerAsCurrent(IGameObject newInstance) {
        if (newInstance instanceof GamePlayer) {
            ((GamePlayer) newInstance).setCurrentPlayer(true);
        }
    }

    private int getNumberOfRows(IGameMap map) throws XvgdlException {
        int numberOfRows = 0;
        try (InputStream f = XvgdlUtils.getFileInputStream(map.getMapFile())) {
            BufferedReader br = new BufferedReader(new InputStreamReader(f));

            while (br.readLine() != null) {
                numberOfRows++;
            }
        } catch (Exception e) {
            log.error("Error parsing map file: " + map.getMapFile(), e);
            throw new XvgdlException("Error generating map from file.", e);
        }
        return numberOfRows;
    }
}
