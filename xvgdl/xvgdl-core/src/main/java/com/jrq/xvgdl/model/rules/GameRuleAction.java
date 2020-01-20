package com.jrq.xvgdl.model.rules;

import lombok.Data;

/**
 * Game Rule Action
 *
 * @author jrquinones
 */
@Data
public class GameRuleAction implements IGameRuleAction {

    /**
     * Object name.
     */
    private String objectName;

    /**
     * Result Type.
     */
    private GameRuleResultType resultType;

    /**
     * Result Value as a number.
     */
    private String value;

    @Override
    public Double getValueAsDouble() {
        Double rto;
        try {
            rto = Double.parseDouble(getValue());
        } catch (Exception e) {
            rto = 0.0d;
        }
        return rto;
    }

    @Override
    public Long getValueAsLong() {
        Long rto;
        try {
            rto = Long.parseLong(getValue());
        } catch (Exception e) {
            rto = 0L;
        }
        return rto;
    }
}
