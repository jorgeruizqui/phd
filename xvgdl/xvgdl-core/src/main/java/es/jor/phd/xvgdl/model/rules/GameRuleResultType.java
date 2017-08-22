package es.jor.phd.xvgdl.model.rules;

import java.util.Arrays;

/**
 * Game Rule Result Type.
 *
 * @author jrquinones
 *
 */
public enum GameRuleResultType {

    /** Score Up. */
    SCORE_UP,
    /** Score Down. */
    SCORE_DOWN,
    /** Score set to a concrete value. */
    SCORE_SET_TO,
    /** Score reset to initial value. */
    SCORE_RESET,
    /** Lives up. */
    LIVES_UP,
    /** Lives down. */
    LIVES_DOWN,
    /** Lives reset to initial value. */
    LIVES_RESET,
    /** Disappear. */
    DISAPPEAR,
    /** Duplicate. */
    DUPLICATE,
    /** Teletransport. */
    TELETRANSPORT,
    /** Freeze. */
    FREEZE,
    /** Transform. */
    TRANSFORM,
    /** Bounce. */
    BOUNCE,
    /** Time Up. */
    TIME_UP,
    /** Time Down. */
    TIME_DOWN,
    /** Time Reset to initial value. */
    TIME_RESET;

    /**
     *
     * @param ruleResultTypeSt String defining rule result type
     * @return the Rule Result type according to the string parameter
     */
    public static GameRuleResultType fromString(String ruleResultTypeSt) {

        return Arrays.asList(values()).stream()
                .filter(s -> ruleResultTypeSt.trim().equalsIgnoreCase(s.name().replace("_", ""))).findFirst()
                .orElse(null);
    }
}
