package es.jor.phd.xvgdl.model.rules;

import java.util.Arrays;

/**
 * Game Rule Type.
 *
 * @author jrquinones
 *
 */
public enum GameRuleType {
    /** GENERIC. A rule with no objects involved. Just implies a result. */
    GENERIC,
    /** Collision. */
    COLLISION,
    /** Proximity. */
    PROXIMITY,
    /** Distant. */
    DISTANT;

    /**
     *
     * @param ruleTypeSt String defining rule type
     * @return the Rule type according to the string parameter
     */
    public static GameRuleType fromString(String ruleTypeSt) {
        return Arrays.asList(values()).stream().filter(s -> ruleTypeSt.trim().equalsIgnoreCase(s.name())).findFirst()
                .orElse(null);
    }

}
