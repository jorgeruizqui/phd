package es.jor.phd.xvgdl.model.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Game Rule
 *
 * @author jrquinones
 *
 */
public class GameRule implements IGameRule {

    /** Game Rule Name. */
    private String name;

    /** Game Rule Type. */
    private GameRuleType type;

    /** Rule Actions. */
    private List<IGameRuleAction> ruleActions;

    /**
     * Constructor.
     */
    public GameRule() {
        ruleActions = new ArrayList<IGameRuleAction>();
    }

    @Override
    public String getRuleName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param type the type to set
     */
    public void setType(GameRuleType type) {
        this.type = type;
    }

    @Override
    public GameRuleType getGameRuleType() {
        return type;
    }

    @Override
    public void addRuleAction(IGameRuleAction action) {
        this.ruleActions.add(action);
    }

    @Override
    public List<IGameRuleAction> getRuleActions() {
        return this.ruleActions;
    }

    @Override
    public IGameRuleAction getRuleActionByName(String objectName) {
        return ruleActions.stream().filter(s -> s.getObjectName().equalsIgnoreCase(objectName)).findFirst()
                .orElse(null);
    }

}
