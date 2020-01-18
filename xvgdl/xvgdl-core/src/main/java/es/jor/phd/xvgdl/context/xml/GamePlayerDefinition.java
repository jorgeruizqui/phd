package es.jor.phd.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import es.jor.phd.xvgdl.model.object.GameObject;
import es.jor.phd.xvgdl.model.object.GameObjectType;
import es.jor.phd.xvgdl.model.object.GamePlayer;
import es.jor.phd.xvgdl.model.object.IGameObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Game Map XML element Definition
 *
 * @author jrquinones
 *
 */
@Slf4j
@Data
public class GamePlayerDefinition extends GameObjectDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private Integer lives;
    @JacksonXmlProperty(isAttribute = true)
    private Integer initialLives;
    @JacksonXmlProperty(isAttribute = true)
    private Double score;
    @JacksonXmlProperty(isAttribute = true)
    private Integer livePercentage;
    /**
     *
     * @param playerDefinition Object definition
     * @return Game Object completely initialize
     */
    public static IGameObject convert(GamePlayerDefinition playerDefinition) {

        try {
            // Mapping GameObject Properties
            GamePlayer gamePlayer = (GamePlayer) GameObjectDefinition.convert(GamePlayer.class, playerDefinition, 1);
            gamePlayer.setObjectType(GameObjectType.PLAYER);
            gamePlayer.setLives(Optional.ofNullable(playerDefinition.getLives()).orElse(1));
            gamePlayer.setInitialLives(Optional.ofNullable(playerDefinition.getInitialLives()).orElse(1));
            gamePlayer.setLivePercentage(Optional.ofNullable(playerDefinition.getLivePercentage()).orElse(1));
            gamePlayer.setScore(Optional.ofNullable(playerDefinition.getScore()).orElse(0.0d));

            return gamePlayer;
        } catch (Exception e) {
            log.error("Exception converting GameObjectDefinition to GameObject: " + e.getMessage(), e);
            return null;
        }
    }
}
