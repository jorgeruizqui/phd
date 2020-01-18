package es.jor.phd.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import es.jor.phd.xvgdl.model.map.GameMap;
import es.jor.phd.xvgdl.model.map.GameMapType;
import es.jor.phd.xvgdl.model.map.IGameMap;
import es.jor.phd.xvgdl.model.map.IGameMapGenerator;
import es.jor.phd.xvgdl.model.object.IGameObject;

import java.util.Optional;

/**
 * Game Map XML element Definition
 *
 * @author jrquinones
 *
 */
@Slf4j
@Data
public class GameMapDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private Integer sizeX;
    @JacksonXmlProperty(isAttribute = true)
    private Integer sizeY;
    @JacksonXmlProperty(isAttribute = true)
    private Integer sizeZ;
    @JacksonXmlProperty(isAttribute = true)
    private String generator;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean toroidal;
    @JacksonXmlProperty(isAttribute = true)
    private String file;

    /**
     * Convert to IGameMap interface
     * @param definition Game Map Definition
     * @return Game Map converted from XML to Object
     */
    public static IGameMap convert(GameMapDefinition definition) {
        GameMap gameMap = new GameMap();
        try {
            gameMap.setSizeX(definition.getSizeX());
            gameMap.setSizeY(definition.getSizeY());
            gameMap.setSizeZ(definition.getSizeZ());

            if (StringUtils.isNotEmpty(definition.getGenerator())) {
	            gameMap.setMapGenerator((IGameMapGenerator) Class.forName(
	                    definition.getGenerator()).newInstance());
            }

            gameMap.setMapRepresentation(
                    new IGameObject[gameMap.getSizeX()][gameMap.getSizeY()][gameMap.getSizeZ()]);
            gameMap.setMapType(GameMapType.fromString(definition.getType()));
            gameMap.setToroidal(Optional.ofNullable(definition.getToroidal()).orElse(Boolean.FALSE));
            gameMap.setMapFile(definition.getFile());
        } catch (Exception e) {
            log.error("Exception converting GameMapDefinition to GameMap: " + e.getMessage(), e);
            gameMap = null;
        }

        return gameMap;
    }
}
