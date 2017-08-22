package es.jor.phd.xvgdl.model.rules;

/**
 * Game Rule Action
 *
 * @author jrquinones
 *
 */
public class GameRuleAction implements IGameRuleAction {

    /** Object name. */
    private String objectName;

    /** Result Type. */
    private GameRuleResultType resultType;

    /** Result Value. */
    private String value;

    @Override
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public GameRuleResultType getResultType() {
        return resultType;
    }

    /**
     * @param resultType the resultType to set
     */
    public void setResultType(GameRuleResultType resultType) {
        this.resultType = resultType;
    }

    @Override
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
