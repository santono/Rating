package rating.util.forbsgridcontroller;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SortAndRulesFromRequestClass {
    public List<SortingClass> getSorting(HttpServletRequest request) {
        List<SortingClass> sorting = new ArrayList<SortingClass>();
        int i;
        final int L = 10;
        StringBuilder sb;
        SortingClass sort;
        String fld;
        String ord;
        for (i = 0; i < L; i++) {
            sb = new StringBuilder();
            sb = sb.append("sorting");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[field]");
            fld = request.getParameter(sb.toString());
            if (fld == null) {
                break;
            }
            sb = new StringBuilder();
            sb = sb.append("sorting");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[order]");
            ord = request.getParameter(sb.toString());
            if (ord == null) {
                ord = "ascending";
            }
            sort = new SortingClass();
            sort.setField(fld);
            sort.setOrder(ord);
            sorting.add(sort);
        }
        return sorting;
    }

    public List<FilterRuleClass> getFilterRules(HttpServletRequest request) {
        List<FilterRuleClass> rules = new ArrayList<FilterRuleClass>();
        int i;
        final int L = 10;
        StringBuilder sb;
        FilterRuleClass filterRule;
        ConditionForRulesClass condition;
        String elementRuleId;
        String logicalOperator;
        String filterType;
        String field;
        String operator;
        String numberType;
        String[] filterValue;
        boolean need;

        for (i = 0; i < L; i++) {

            sb = new StringBuilder();
            sb = sb.append("filter_rules");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[element_rule_id]");
            elementRuleId = request.getParameter(sb.toString());


            sb = new StringBuilder();
            sb = sb.append("filter_rules");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[logical_operator]");
            logicalOperator = request.getParameter(sb.toString());


            sb = new StringBuilder();
            sb = sb.append("filter_rules");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[condition]");
            sb = sb.append("[filterType]");
            filterType = request.getParameter(sb.toString());

            sb = new StringBuilder();
            sb = sb.append("filter_rules");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[condition]");
            sb = sb.append("[operator]");
            operator = request.getParameter(sb.toString());

            sb = new StringBuilder();
            sb = sb.append("filter_rules");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[condition]");
            sb = sb.append("[numberType]");
            numberType = request.getParameter(sb.toString());

            sb = new StringBuilder();
            sb = sb.append("filter_rules");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[condition]");
            sb = sb.append("[field]");
            field = request.getParameter(sb.toString());
            //     logger.debug("field="+field+" sb="+sb.toString());

            sb = new StringBuilder();
            sb = sb.append("filter_rules");
            sb = sb.append("[" + i + "]");
            sb = sb.append("[condition]");
            sb = sb.append("[filterValue][]");
            filterValue = request.getParameterValues(sb.toString());

            need = false;
            if (field != null) {
                if (field.trim().length() > 0)
                    need = true;
            }
            if (need) {
                condition = new ConditionForRulesClass();
                condition.setField(field);
                condition.setFilterType(filterType);
                condition.setFilterValue(filterValue);
                condition.setNumberType(numberType);
                condition.setOperator(operator);

                filterRule = new FilterRuleClass();
                filterRule.setCondition(condition);
                ;
                filterRule.setElementRuleId(elementRuleId);
                filterRule.setLogicalOperator(logicalOperator);
                rules.add(filterRule);
            }
        }
        return rules;
    }

}
