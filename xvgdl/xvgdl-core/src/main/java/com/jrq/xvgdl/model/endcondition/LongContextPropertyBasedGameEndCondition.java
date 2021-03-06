package com.jrq.xvgdl.model.endcondition;

import com.jrq.xvgdl.context.GameContext;

/**
 * End condition based on Context properties values.
 */
public class LongContextPropertyBasedGameEndCondition extends AGameEndCondition {

    @Override
    public boolean checkCondition(GameContext c) {

        try {
            String prop = getGameEndConditionDefinition().getProperty();
            Long gameContextValue = c.getGameDefinition().getProperties().getLongValue(prop.toUpperCase(), -1);
            Long value = Long.parseLong(getGameEndConditionDefinition().getValue());

            return gameContextValue.equals(value);
        } catch (Exception e) {
        }
        return false;
    }

}
