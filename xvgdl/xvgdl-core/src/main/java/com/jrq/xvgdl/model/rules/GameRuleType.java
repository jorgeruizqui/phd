package com.jrq.xvgdl.model.rules;

import java.util.Arrays;

/**
 * Game Rule Type.
 *
 * @author jrquinones
 */
public enum GameRuleType {

    GENERIC,
    COLLISION,
    PROXIMITY,
    DISTANT,
    END_CONDITION;

    /**
     * @param ruleTypeSt String defining rule type
     * @return the Rule type according to the string parameter
     */
    public static GameRuleType fromString(String ruleTypeSt) {
        return Arrays.asList(values()).stream().filter(s -> ruleTypeSt.trim().equalsIgnoreCase(s.name())).findFirst()
                .orElse(null);
    }

}
