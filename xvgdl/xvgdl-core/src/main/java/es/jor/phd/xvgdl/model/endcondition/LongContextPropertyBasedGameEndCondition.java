package es.jor.phd.xvgdl.model.endcondition;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * End condition based on Context properties values.
 *
 */
public class LongContextPropertyBasedGameEndCondition implements IGameEndConditionChecker {

    private static final String PROPERTY = "property";
    private static final String VALUE = "value";

    @Override
    public boolean checkCondition(GameContext c, IGameEndCondition gameEndCondition) {

        boolean rto = false;

        try {
            String prop = gameEndCondition.getGameEndConditionDefinition().getStringValue(PROPERTY);
            Long gameContextValue = c.getLongValue(prop.toUpperCase(), -1);
            Long value = Long.parseLong(gameEndCondition.getGameEndConditionDefinition().getStringValue(VALUE));

            if (gameContextValue.equals(value)) {
                rto = true;
            } else rto = false;
        } catch (Exception e) {
        }
        return rto;
    }
}
