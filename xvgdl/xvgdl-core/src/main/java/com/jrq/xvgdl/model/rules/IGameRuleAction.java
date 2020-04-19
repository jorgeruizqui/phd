package com.jrq.xvgdl.model.rules;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.model.object.IGameObject;

/**
 * Game Rule Action interface.
 *
 * @author jrquinones
 */
public interface IGameRuleAction {

    String getObjectName();
    void setObjectName(String objectName);

    GameRuleResultType getResultType();
    void setResultType(GameRuleResultType gameRuleResultType);

    String getValue();
    void setValue(String value);

    Long getValueAsLong();
    Double getValueAsDouble();
    Integer getValueAsInteger();

    boolean executeGameRuleAction(GameContext gameContext, IGameObject gameObject);
}
