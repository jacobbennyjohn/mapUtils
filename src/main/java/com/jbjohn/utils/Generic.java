package com.jbjohn.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 */
public class Generic {


    public static Object processPredicate(Object map, String predicate) {

        int operation = 0;
        String predicateKey = "";
        String predicateValue = "";

        if (predicate.contains("==")) {
            operation = 1;
            List<String> predicateList = Arrays.asList(predicate.split("=="));
            predicateKey = predicateList.get(0);
            predicateValue = predicateList.get(1);
        } else if (predicate.contains(">")) {
            operation = 2;
            List<String> predicateList = Arrays.asList(predicate.split(">"));
            predicateKey = predicateList.get(0);
            predicateValue = predicateList.get(1);
        } else if (predicate.contains("<")) {
            operation = 3;
            List<String> predicateList = Arrays.asList(predicate.split("<"));
            predicateKey = predicateList.get(0);
            predicateValue = predicateList.get(1);
        } else if (predicate.contains("=")) {
            operation = 1;
            List<String> predicateList = Arrays.asList(predicate.split("="));
            predicateKey = predicateList.get(0);
            predicateValue = predicateList.get(1);
        }

        if (map instanceof ArrayList) {
            ArrayList request = (ArrayList) map;
            ArrayList response = new ArrayList();

            for (Object item : request) {
                Map<String, Object> itemMap = (Map<String, Object>) item;
                if (itemMap.containsKey(predicateKey)) {

                    int valueInt = 0;
                    int predicateInt = 0;
                    if (((String) itemMap.get(predicateKey)).matches("^-?\\d+$") && predicateValue.matches("^-?\\d+$")) {
                        valueInt = Integer.parseInt((String) itemMap.get(predicateKey));
                        predicateInt = Integer.parseInt(predicateValue);
                    }

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

    public static String trimPath(String path) {
        if (path.startsWith("$.")) {
            path = path.substring(2);
        }
        return path;
    }

    public static String trimKey(String key) {
        if (key.startsWith("[") && key.endsWith("]")) {
            key = key.replace("[", "").replace("]", "");
        }
        return key;
    }

    public static String newKey(List<String> stringList) {
        String newKey = "";
        int counter = 0;
        for (String string : stringList) {
            counter++;
            if (counter < 2) {
                continue;
            }
            if (newKey.length() > 1) {
                newKey += ".";
            }
            newKey += string;
        }
        return newKey;
    }

    public static List<String> getKeyList(String path) {
        return Arrays.asList(path.split("\\."));
    }

}
