package com.jbjohn.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 */
public class Predicate {

    public enum Operator {
        EQUALS("==", 1),
        GREATER_THAN(">", 2),
        LESSER_THAN("<", 3);

        private String operation;
        private int id;

        Operator(String operation, int id) {
            this.operation = operation;
            this.id = id;
        }

        public String getOperator() {
            return operation;
        }

        public int getId() {
            return id;
        }

        public static Operator getOperatorById(int id) {
            for (Operator operator : Operator.values()) {
                if (operator.getId() == id) {
                    return operator;
                }
            }
            return null;
        }
    }

    private static String predicateKey = "";
    private static String predicateValue = "";
    private static int operation = 0;

    public static Object process(Object map, String predicate) {

        if (predicate.contains(Operator.EQUALS.getOperator())) {
            operation = Operator.EQUALS.getId();
        } else if (predicate.contains(Operator.GREATER_THAN.getOperator())) {
            operation = Operator.GREATER_THAN.getId();
        } else if (predicate.contains(Operator.LESSER_THAN.getOperator())) {
            operation = Operator.LESSER_THAN.getId();
        }

        List<String> predicateList = Arrays.asList(predicate.split(Operator.getOperatorById(operation).getOperator()));
        predicateKey = predicateList.get(0);
        predicateValue = predicateList.get(1);

        if (map instanceof ArrayList) {
            ArrayList request = (ArrayList) map;
            ArrayList response = new ArrayList();

            for (Object item : request) {
                Map<String, Object> itemMap = (Map<String, Object>) item;
                if (itemMap.containsKey(predicateKey)) {

                    int valueInt = Generic.getIntValue((String) itemMap.get(predicateKey));
                    int predicateInt = Generic.getIntValue(predicateValue);

                    switch (operation) {
                        case 1:
                            if (itemMap.get(predicateKey).equals(predicateValue)) {
                                response.add(itemMap);
                            }
                            break;
                        case 2:
                            if (valueInt > predicateInt) {
                                response.add(itemMap);
                            }
                            break;
                        case 3:
                            if (valueInt < predicateInt) {
                                response.add(itemMap);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            return response;
        }

        return map;
    }
}
