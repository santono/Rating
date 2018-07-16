package rating.util.forbsgridcontroller;


public class FilterRuleClass {
    private String logicalOperator;
    private String elementRuleId;
    private ConditionForRulesClass condition;

    public String getLogicalOperator() {
        return logicalOperator;
    }

    public void setLogicalOperator(String logicalOperator) {
        this.logicalOperator = logicalOperator;
    }

    public ConditionForRulesClass getCondition() {
        return condition;
    }

    public void setCondition(ConditionForRulesClass condition) {
        this.condition = condition;
    }

    public String getElementRuleId() {
        return elementRuleId;
    }

    public void setElementRuleId(String elementRuleId) {
        this.elementRuleId = elementRuleId;
    }

    @Override
    public String toString() {
        return "FilterRule: logicalOperator=" + logicalOperator + " elementRuleId=" + elementRuleId + " " + condition.toString();
    }

}
