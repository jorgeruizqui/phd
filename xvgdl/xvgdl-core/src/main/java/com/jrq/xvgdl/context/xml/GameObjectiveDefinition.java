package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.jrq.xvgdl.model.objectives.IGameObjective;
import com.jrq.xvgdl.util.GameBaseProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Game Objective XML element Definition
 *
 * @author jrquinones
 */
@Slf4j
@Data
public class GameObjectiveDefinition {

    @JacksonXmlProperty(isAttribute = true)
    public String objectiveCheckerClass;

    @JacksonXmlProperty(isAttribute = true)
    public Double weight;

    @JacksonXmlProperty(isAttribute = true)
    public Double score;

    /**
     * @return Game Objective initialized
     */
    public IGameObjective toModel() {

        try {
            IGameObjective gameObjective = (IGameObjective) Class.forName(this.getObjectiveCheckerClass()).getDeclaredConstructor().newInstance();
            gameObjective.setGameObjectiveDefinition(this);

            return gameObjective;
        } catch (Exception e) {
            log.error("Exception converting GameObjectiveDefinition to IGameObjective: " + e.getMessage(), e);
            return null;
        }
    }
}
