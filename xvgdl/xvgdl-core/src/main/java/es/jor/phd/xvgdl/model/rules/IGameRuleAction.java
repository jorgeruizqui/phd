package es.jor.phd.xvgdl.model.rules;

/**
 * Game Rule Action interface.
 *
 * @author jrquinones
 *
 */
public interface IGameRuleAction {

    /**
     *
     * @return The object Reference Name
     */
    String getObjectName();

    /**
     *
     * @return Game Rule Result Type
     */
    GameRuleResultType getResultType();

    /**
     *
     * @return Result Value
     */
    String getValue();

}
