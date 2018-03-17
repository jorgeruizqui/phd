package es.jor.phd.xvgdl.model.endcondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * Timeout End condition.
 *
 */
public class NoObjectsPresentGameEndCondition extends AGameEndCondition {

    private static final String XML_OBJECT_NAMES = "objectNames";

    @Override
    public boolean checkCondition(GameContext c) {
        String objectNames = getGameEndConditionDefinition().getProperty(XML_OBJECT_NAMES, "");
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
