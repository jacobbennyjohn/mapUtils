package com.jbjohn.utils;

import java.util.*;

/**
 */
public class PredicateInMap {

    public enum Operator {
        DEFAULT("  ") {
            @Override
            boolean check(Map<String, Object> request, String key, String value) {
                return false;
            }
        },
        EQUALS("==") {
            @Override
            boolean check(Map<String, Object> request, String key, String value) {
                if (request.get(key) instanceof String && request.get(key).equals(value)) {
                    return true;
                }
                return false;
            }
        },
        GREATER_THAN(">") {
            @Override
            boolean check(Map<String, Object> request, String key, String value) {
                int valueInt = Generic.getIntValue((String) request.get(key), -1);
                int predicateInt = Generic.getIntValue(value, -1);
                if ((valueInt > predicateInt) && (predicateInt > -1)) {
                    return true;
                }
                return false;
            }
        },
        LESSER_THAN("<") {
            @Override
            boolean check(Map<String, Object> request, String key, String value) {
                int valueInt = Generic.getIntValue((String) request.get(key), -1);
                int predicateInt = Generic.getIntValue(value, -1);
                if ((valueInt < predicateInt) && (valueInt > -1)) {
                    return true;
                }
                return false;
            }
        },
        NOT_EQUALS("!=") {
            @Override
            boolean check(Map<String, Object> request, String key, String value) {
                if (request.get(key) instanceof String && !request.get(key).equals(value)) {
                    return true;
                }
                return false;
            }
        };

        private String operation;

        Operator(String operation) {
            this.operation = operation;
        }

        String getOperator() {
            return operation;
        }

        abstract boolean check(Map<String, Object> request, String key, String value);
    }

    private static Operator operator;
    private static String predicateKey = "";
    private static String predicateValue = "";

    public static void preProcess(String predicate) {
        for (Operator operator : Operator.values()) check(operator, predicate);
    }

    static void check(Operator operator, String predicate) {
        if (predicate.contains(operator.getOperator())) {
            List<String> predicateList = Arrays.asList(predicate.split(operator.getOperator()));
            predicateKey = predicateList.get(0);
            predicateValue = predicateList.get(1);
            PredicateInMap.operator = operator;
        }
    }

    public static boolean isMatch(Object request) {

        if (request instanceof HashMap) {
            Map<String, Object> itemMap = (Map<String, Object>) request;
            if (itemMap.containsKey(predicateKey)) {
                return operator.check(itemMap, predicateKey, predicateValue);
            }
        } else if (request instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) request;
            if (entry.getKey() instanceof String) {
                if (entry.getKey().equals(predicateKey)) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put(entry.getKey().toString(), entry.getValue());
                    return operator.check(map, predicateKey, predicateValue);
                }
            }
        }
        return false;
    }
}
