package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * End condition based on Context properties values.
 *
 */
public class LongContextPropertyBasedGameEndCondition extends AGameEndCondition {

    private static final String PROPERTY = "property";
    private static final String VALUE = "value";

    @Override
    public boolean checkCondition(GameContext c) {

        boolean rto = false;

        try {
            String prop = getGameEndConditionDefinition().getStringValue(PROPERTY);
            Long gameContextValue = c.getLongValue(prop.toUpperCase(), -1);
            Long value = Long.parseLong(getGameEndConditionDefinition().getStringValue(VALUE));

            if (gameContextValue.equals(value)) {
                rto = true;
            } else rto = false;
        } catch (Exception e) {
        }
        return rto;
    }

}
