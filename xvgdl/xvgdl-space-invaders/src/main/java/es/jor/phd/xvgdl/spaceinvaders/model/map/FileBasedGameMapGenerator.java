package es.jor.phd.xvgdl.spaceinvaders.model.map;

import java.io.BufferedReader;
import java.io.InputStream;

import es.indra.eplatform.util.IOUtils;
import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.map.IGameMap;
import es.jor.phd.xvgdl.model.map.IGameMapGenerator;
import es.jor.phd.xvgdl.model.object.IGameObject;
import es.jor.phd.xvgdl.util.GameConstants;

/**
 * Game Map Generator basic implementations
 *
 * @author jrquinones
 *
 */
public class FileBasedGameMapGenerator implements IGameMapGenerator {

    @Override
    public void generateMapRepresentation(GameContext gc, IGameMap map) {

        try (InputStream f = IOUtils.getInputStream(map.getMapFile())) {
            String sCurrentLine = "";
//            char [][] mapRepresentation;
            int numberOfRows = 0;
            BufferedReader br = IOUtils.createBufferedReader(f);
            while ((sCurrentLine = br.readLine()) != null) {
                numberOfRows++;
            }
            f.close();
            InputStream readValues = IOUtils.getInputStream(map.getMapFile());
            br = IOUtils.createBufferedReader(readValues);
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
            ELogger.error(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Error parsing map file: " + map.getMapFile(), e);
        }
    }

}
