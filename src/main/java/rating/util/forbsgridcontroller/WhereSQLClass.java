package rating.util.forbsgridcontroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WhereSQLClass {
    //    static String sql;
//    static StringBuilder sqlSb;
    static private Map<String, String> operatorSQL;
    //    String sql;
    StringBuilder sqlSb;
    static String[] bindParams;
    static int bindParamIndex = 1;

    static {
        operatorSQL = new HashMap<String, String>();
        operatorSQL.put("equal", "=");
        operatorSQL.put("not_equal", "!=");
        operatorSQL.put("in", "IN");
        operatorSQL.put("not_in", "NOT IN");
        operatorSQL.put("less", "<");
        operatorSQL.put("less_or_equal", "<=");
        operatorSQL.put("greater", ">");
        operatorSQL.put("greater_or_equal", ">=");
        operatorSQL.put("begins_with", "LIKE");
        operatorSQL.put("not_begins_with", "NOT LIKE");
        operatorSQL.put("contains", "LIKE");
        operatorSQL.put("not_contains", "NOT LIKE");
        operatorSQL.put("ends_with", "LIKE");
        operatorSQL.put("not_ends_with", "NOT LIKE");
        operatorSQL.put("is_empty", "=");
        operatorSQL.put("is_not_empty", "!=");
        operatorSQL.put("is_null", "IS NULL");
        operatorSQL.put("is_not_null", "IS NOT NULL");
    }

    private String getOperatorSQL(String operatorType) {
        String operator;
        operator = operatorSQL.get(operatorType);
        operator = " " + operator.trim() + " ";
        return operator;
    }

    public String parseRules(List<FilterRuleClass> rules, boolean isGroup) {
        ConditionForRulesClass condition;
        String[] filterValue;
        String filterType;
        String numberType;
        String filterValueSQL;
        String filterOperator;
        if (rules == null) {
            return "";
        }
        if (rules.size() < 1) {
            return "";
        }

        // WHERE clause
        if ((sqlSb == null)) {
            sqlSb = new StringBuilder();
            sqlSb.append("WHERE ");
        }
        int aLen = rules.size();
        int iCount = 0;
        for (FilterRuleClass rule : rules) {
            iCount++;
            if (rule.getCondition() != null) {
                condition = rule.getCondition();
                // condition
                sqlSb.append(condition.getField().toLowerCase());

                // operator
                sqlSb.append(getOperatorSQL(condition.getOperator()));

                filterValue = condition.getFilterValue();
                filterType = condition.getFilterType();
                numberType = condition.getNumberType();
                filterOperator = condition.getOperator();


                filterValueSQL = createFilterValueSQL(filterType, filterOperator, filterValue);
/*
                if($this->usePreparedStatements) {

                    if(!in_array($rule['condition']['operator'], array('is_null', 'is_not_null'))) {

                        if(in_array($rule['condition']['operator'], array('in', 'not_in'))) {
                            $sql .= '(';
                            $filter_value_len = count($filter_value);
                            for($v = 0; $v < $filter_value_len; $v++) {
                                switch($this->pst_placeholder) {
                                    case 'question_mark':
                                        $sql .= '?';
                                        break;
                                    case 'numbered':
                                        $sql .= '$' . $bind_param_index;
                                        $bind_param_index++;
                                }
                                if($v < $filter_value_len - 1) {
                                    $sql .= ',';
                                }

                                // set param type
                                $bind_param = $this->set_bind_param_type($filter_value[$v], $filter_type, $number_type);
                                array_push($bind_params, $bind_param);
                            }
                            $sql .= ')';
                        } else {
                            switch($this->pst_placeholder) {
                                case 'question_mark':
                                    $sql .= '?';
                                    break;
                                case 'numbered':
                                    $sql .= '$' . $bind_param_index;
                                    $bind_param_index++;
                            }

                            if(in_array($rule['condition']['operator'], array('is_empty', 'is_not_empty'))) {
                                array_push($bind_params, '');
                            } else {
                                // set param type
                                $bind_param = $this->set_bind_param_type($filter_value_sql, $filter_type, $number_type);
                                array_push($bind_params, $bind_param);
                            }

                        }

                    }

                } else {
*/
                if (!Arrays.asList(new String[]{"is_null", "is_not_null"}).contains(filterOperator)) {
                    if (Arrays.asList(new String[]{"is_empty", "is_not_empty"}).contains(filterOperator)) {
                        sqlSb.append("''");
                    } else {
                        sqlSb.append(filterValueSQL);
                    }
                }
//                }

            }

            // logical operator (between rules)
            sqlSb.append(iCount < aLen ? " " + rule.getLogicalOperator() + " " : "");

        }
        return sqlSb.toString();
    }

    /*
     * Return current rule filter value as a string suitable for SQL WHERE clause
     *
     * @param string $filter_type (one of 'text', 'number', 'date' - see documentation)
     * @param string $operator_type (see documentation for available operators)
     * @param array|null $a_values the values array
     * @param array|null $filter_value_conversion_server_side
     * @param string $element_rule_id
     * @return string|null
     */
    private String createFilterValueSQL(String filterType, String operatorType, String[] filterValue) {
        StringBuilder sb;
        String[] w = {"is_empty", "is_not_empty", "is_null", "is_not_null"};
        if (Arrays.asList(w).contains(operatorType)) {
            return null;
        }

        sb = new StringBuilder();

        // apply filter value conversion (if any)
/*
        if($this->usePreparedStatements) {
            if(in_array($operator_type, array('equal', 'not_equal', 'less', 'not_equal', 'less_or_equal', 'greater', 'greater_or_equal'))) {
                $res = $a_values[0];
            } else if(in_array($operator_type, array('begins_with', 'not_begins_with'))) {
                $res = $a_values[0] . '%';
            } else if(in_array($operator_type, array('contains', 'not_contains'))) {
                $res = '%' . $a_values[0] . '%';
            } else if(in_array($operator_type, array('ends_with', 'not_ends_with'))) {
                $res = '%' . $a_values[0];
            } else if(in_array($operator_type, array('in', 'not_in'))) {
                for($i = 0; $i < $vlen; $i++) {
                    $res .= ($i == 0 ? '(' : '');
                    $res .= $a_values[$i];
                    $res .= ($i < $vlen - 1 ? ',' : ')');
                }
            }
        } else {
*/
        if (Arrays.asList(new String[]{"equal", "not_equal", "less", "not_equal", "less_or_equal", "greater", "greater_or_equal"}).contains(operatorType)) {
            sb.append(filterType.equals("number") ? filterValue[0] : "'" + filterValue[0] + "'");
        } else if (Arrays.asList(new String[]{"begins_with", "not_begins_with"}).contains(operatorType)) {
            sb.append("'" + filterValue[0] + "%'");
        } else if (Arrays.asList(new String[]{"contains", "not_contains"}).contains(operatorType)) {
            sb.append("'%" + filterValue[0] + "%'");
        } else if (Arrays.asList(new String[]{"ends_with", "not_ends_with"}).contains(operatorType)) {
            sb.append("'%" + filterValue[0] + "'");
        } else if (Arrays.asList(new String[]{"in", "not_in"}).contains(operatorType)) {
            sb.append("(");
            for (int i = 0; i < filterValue.length; i++) {
                if (i > 0)
                    sb.append(",");
                sb.append(filterType.equals("number") ? filterValue[i] : "'" + filterValue[i] + "'");
            }
//            sb.append("'%"+filterValue+"'");
//            for($i = 0; $i < $vlen; $i++) {
//                $res .= ($i == 0 ? '(' : '');
//                $res .= ($filter_type == 'number' ? $a_values[$i] : $ds->qstr($a_values[$i]));
//                $res .= ($i < $vlen - 1 ? ',' : ')');
//            }
            sb.append(")");

        }
        //   }
        return sb.toString();
    }


}
