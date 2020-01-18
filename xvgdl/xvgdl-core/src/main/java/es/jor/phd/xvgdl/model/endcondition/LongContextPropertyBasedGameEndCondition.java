package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * End condition based on Context properties values.
 *
 */
public class LongContextPropertyBasedGameEndCondition extends AGameEndCondition {

    @Override
    public boolean checkCondition(GameContext c) {

        try {
            String prop = getGameEndConditionDefinition().getProperty();
            Long gameContextValue = c.getLongValue(prop.toUpperCase(), -1);
            Long value = Long.parseLong(getGameEndConditionDefinition().getValue());

            return gameContextValue.equals(value);
        } catch (Exception e) {
        }
        return false;
    }

}
