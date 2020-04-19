package com.jrq.xvgdl.model.rules;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;
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
    public boolean executeGameRuleAction(GameContext gameContext, IGameObject gameObject) {
        return GameRuleUtils.executeResult(gameContext, gameObject, this);
    }

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

    @Override
    public Integer getValueAsInteger() {
        Integer rto;
        try {
            rto = Integer.parseInt(getValue());
        } catch (Exception e) {
            rto = 0;
        }
        return rto;
    }
}
