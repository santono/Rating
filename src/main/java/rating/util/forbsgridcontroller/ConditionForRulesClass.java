package rating.util.forbsgridcontroller;


import java.util.Arrays;

public class ConditionForRulesClass {
    private String field;
    private String operator;
    private String[] filterValue;
    private String filterType;
    private String numberType;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String[] getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String[] filterValue) {
        this.filterValue = filterValue;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    @Override
    public String toString() {
        return "conditions:field=" + field + " " + " operator=" + operator + " filterType=" + filterType + " numberType=" + numberType + " filterValue=" + Arrays.toString(filterValue);

    }
}
