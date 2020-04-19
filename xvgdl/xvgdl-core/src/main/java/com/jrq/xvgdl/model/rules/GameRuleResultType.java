package com.jrq.xvgdl.model.rules;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * Game Rule Result Type.
 *
 * @author jrquinones
 */
public enum GameRuleResultType {

    NONE,
    CANT_MOVE,
    SCORE_UP,
    SCORE_DOWN,
    SCORE_SET_TO,
    SCORE_RESET,
    LIVES_UP,
    LIVES_DOWN,
    LIVES_RESET,
    LIVES_PERCENTAGE_UP,
    LIVES_PERCENTAGE_DOWN,
    LIVES_PERCENTAGE_RESET,
    DISAPPEAR,
    DUPLICATE,
    TELETRANSPORT,
    CHANGE_DIRECTION,
    FREEZE,
    TRANSFORM,
    BOUNCE,
    TIME_UP,
    TIME_DOWN,
    TIME_RESET,
    SPEED_UP,
    SPEED_DOWN,
    SPEED_RESET,
    STATE_TRANSITION;

    /**
     * @param ruleResultTypeSt String defining rule result type
     * @return the Rule Result type according to the string parameter
     */
    public static GameRuleResultType fromString(String ruleResultTypeSt) {

        return StringUtils.isNotEmpty(ruleResultTypeSt)
                ? Arrays.asList(values()).stream()
                .filter(s -> ruleResultTypeSt.trim().equalsIgnoreCase(s.name().replace("_", ""))).findFirst()
                .orElse(null)
                : NONE;
    }
}
