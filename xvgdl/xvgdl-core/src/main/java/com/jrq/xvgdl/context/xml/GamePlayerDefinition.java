package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.GamePlayer;
import com.jrq.xvgdl.model.object.IGameObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Game Map XML element Definition
 *
 * @author jrquinones
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
     * @return Game Player from Game Definition
     */
    public IGameObject toModel() {

        try {
            this.setType(GameObjectType.PLAYER.name());
            GamePlayer gamePlayer = (GamePlayer) super.toModel(GamePlayer.class, 1);
            gamePlayer.setObjectType(GameObjectType.PLAYER);
            gamePlayer.setLives(Optional.ofNullable(this.getLives()).orElse(1));
            gamePlayer.setInitialLives(Optional.ofNullable(this.getInitialLives()).orElse(1));
            gamePlayer.setLivePercentage(Optional.ofNullable(this.getLivePercentage()).orElse(1));
            gamePlayer.setScore(Optional.ofNullable(this.getScore()).orElse(0.0d));

            return gamePlayer;
        } catch (Exception e) {
            log.error("Exception converting GameObjectDefinition to GameObject: " + e.getMessage(), e);
            return null;
        }
    }
}
