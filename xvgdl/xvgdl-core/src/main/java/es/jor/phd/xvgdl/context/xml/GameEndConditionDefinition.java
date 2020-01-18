
package es.jor.phd.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import es.jor.phd.xvgdl.model.endcondition.IGameEndCondition;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Game End Condition XML element Definition
 *
 * @author jrquinones
 *
 */
@Slf4j
@Data
public class GameEndConditionDefinition {

    @JacksonXmlProperty(isAttribute = true)
    private String checkerClass;
    @JacksonXmlProperty(isAttribute = true)
    private String objectNames;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean winningCondition;
    @JacksonXmlProperty(isAttribute = true)
    private String property;
    @JacksonXmlProperty(isAttribute = true)
    private String value;

    /**
     *
     * @param endConditionDefinition Object definition
     * @return Game End Condition initialized
     */
    public static IGameEndCondition convert(GameEndConditionDefinition endConditionDefinition) {

        try {
            IGameEndCondition gameEndCondition = (IGameEndCondition) Class.forName(
                    endConditionDefinition.getCheckerClass()).getDeclaredConstructor().newInstance();
            gameEndCondition.setGameEndConditionDefinition(endConditionDefinition);
            return gameEndCondition;
        } catch (Exception e) {
            log.error("Exception converting GameEndConditionDefinition to IGameEndCondition: " + e.getMessage(), e);
            return null;
        }
    }
}
