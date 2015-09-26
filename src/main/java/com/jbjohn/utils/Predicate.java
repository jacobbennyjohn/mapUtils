package com.jbjohn.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 */
public class Predicate {

    public enum Operator {
        DEFAULT("Default", 0) {
            @Override
            ArrayList apply(Map<String, Object> request, String key, String value, ArrayList response) {
                response.add(request);
                return response;
            }
        },
        EQUALS("==", 1) {
            @Override
            ArrayList apply(Map<String, Object> request, String key, String value, ArrayList response) {
                if (request.get(key).equals(value)) {
                    response.add(request);
                }
                return response;
            }
        },
        GREATER_THAN(">", 2) {
            @Override
            ArrayList apply(Map<String, Object> request, String key, String value, ArrayList response) {
                int valueInt = Generic.getIntValue((String) request.get(key));
                int predicateInt = Generic.getIntValue(value);
                if (valueInt > predicateInt) {
                    response.add(request);
                }
                return response;
            }
        },
        LESSER_THAN("<", 3) {
            @Override
            ArrayList apply(Map<String, Object> request, String key, String value, ArrayList response) {
                int valueInt = Generic.getIntValue((String) request.get(key));
                int predicateInt = Generic.getIntValue(value);
                if (valueInt < predicateInt) {
                    response.add(request);
                }
                return response;
            }
        },
        NOT_EQUALS("!=", 4) {
            @Override
            ArrayList apply(Map<String, Object> request, String key, String value, ArrayList response) {
                if (!request.get(key).equals(value)) {
                    response.add(request);
                }
                return response;
            }
        };

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
            return Operator.DEFAULT;
        }

        public boolean check(String predicate) {
            if (predicate.contains(getOperator())) {
                return true;
            }
            return false;
        }

        abstract ArrayList apply(Map<String, Object> request, String key, String value, ArrayList response);
    }

    private static int operation = 0;

    public static Object process(Object map, String predicate) {

        for (Operator operator : Operator.values()) {
            if (operator.check(predicate)) {
                operation = operator.getId();
                break;
            }
        }

        List<String> predicateList = Arrays.asList(predicate.split(Operator.getOperatorById(operation).getOperator()));
        String predicateKey = predicateList.get(0);
        String predicateValue = predicateList.get(1);

        if (map instanceof ArrayList) {
            ArrayList request = (ArrayList) map;
            ArrayList response = new ArrayList();

            for (Object item : request) {
                Map<String, Object> itemMap = (Map<String, Object>) item;
                if (itemMap.containsKey(predicateKey)) {
                    Operator.getOperatorById(operation).apply(itemMap, predicateKey, predicateValue, response);
                }
            }
            return response;
        }

        return map;
    }
}
