package es.jor.phd.xvgdl.model.map;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;

import es.indra.eplatform.util.IOUtils;
import es.indra.eplatform.util.log.ELogger;
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
    public void generateMapRepresentation(IGameMap map, List<IGameObject> objects) {

        try (InputStream f = IOUtils.getInputStream(map.getMapFile());
                BufferedReader br = IOUtils.createBufferedReader(f)) {
            String sCurrentLine = "";
            int y = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                for (int x = 0; x < sCurrentLine.length(); x++) {
                    String s = new String(sCurrentLine.substring(x, x + 1));
                    IGameObject go = objects.stream().filter(o -> o.getName().toLowerCase().startsWith(s.toLowerCase()))
                            .findFirst().orElse(null);
                    if (go != null) {
                        if (go.isLocatedAnyWhereInMap()) {
                            IGameObject newInstance = go.copy();
                            newInstance.moveTo(x, y, 0);
                        } else {
                            go.moveTo(x, y, 0);
                        }
                    }
                }
                y++;
            }

        } catch (Exception e) {
            ELogger.error(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "Error opening map file: " + map.getMapFile());
        }
    }

}
