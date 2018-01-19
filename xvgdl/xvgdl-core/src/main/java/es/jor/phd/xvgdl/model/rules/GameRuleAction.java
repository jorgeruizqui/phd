package es.jor.phd.xvgdl.model.rules;

import lombok.Data;

/**
 * Game Rule Action
 *
 * @author jrquinones
 *
 */
@Data
public class GameRuleAction implements IGameRuleAction {

    /** Object name. */
    private String objectName;

    /** Result Type. */
    private GameRuleResultType resultType;

    /** Result Value as a number. */
    private Double value;

}
