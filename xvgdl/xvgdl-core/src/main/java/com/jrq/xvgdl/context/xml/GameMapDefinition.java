package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.map.GameMap;
import com.jrq.xvgdl.model.map.GameMapType;
import com.jrq.xvgdl.model.map.IGameMap;
import com.jrq.xvgdl.model.map.IGameMapGenerator;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Game Map XML element Definition
 *
 * @author jrquinones
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
     * @return Model Game Map from Definition
     */
    public IGameMap toModel() {
        try {
            GameMap gameMap = new GameMap();
            gameMap.setSizeX(this.getSizeX());
            gameMap.setSizeY(this.getSizeY());
            gameMap.setSizeZ(this.getSizeZ());

            if (StringUtils.isNotEmpty(this.getGenerator())) {
                gameMap.setMapGenerator((IGameMapGenerator) Class.forName(
                        this.getGenerator()).newInstance());
            }

            gameMap.setMapRepresentation(
                    new IGameObject[gameMap.getSizeX()][gameMap.getSizeY()][gameMap.getSizeZ()]);
            gameMap.setMapType(GameMapType.fromString(this.getType()));
            gameMap.setIsToroidal(Optional.ofNullable(this.getToroidal()).orElse(Boolean.FALSE));
            gameMap.setMapFile(this.getFile());

            return gameMap;
        } catch (Exception e) {
            log.error("Exception converting GameMapDefinition to GameMap: " + e.getMessage(), e);
            return null;
        }
    }
}
