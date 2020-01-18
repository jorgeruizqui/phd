package es.jor.phd.xvgdl.model.endcondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Timeout End condition.
 *
 */
public class NoObjectsPresentGameEndCondition extends AGameEndCondition {

    @Override
    public boolean checkCondition(GameContext c) {
        String objectNames = Optional.ofNullable(getGameEndConditionDefinition().getObjectNames()).orElse("");
        List<String> objectList = new ArrayList<>();

        if (!objectNames.isEmpty()) {
            if (objectNames.contains(",")) {
                objectList.addAll(Arrays.asList(objectNames.split(",")));
            } else {
                objectList.add(objectNames);
            }
        }

        // Check if there is any element of the concrete name in the context
        boolean anyObject = false;
        for (String object : objectList) {

            if (!c.getObjectsListByName(object).isEmpty()) {
                anyObject = true;
                break;
            }
        }

        return !anyObject;
    }

}
