package com.jbjohn.model;

import com.jbjohn.utils.Generic;

import java.util.*;

/**
 */
public class Getter {

    private static Object search(Object map, String key) {
        String predicate = Generic.getPredicate(key);

        if (map instanceof HashMap) {
            return search((HashMap<String, Object>) map, key);
        }
        if (map instanceof ArrayList) {
            return search((ArrayList) map, key, predicate);
        }
        if (map instanceof Collection) {
            search((Collection<Object>) map);
        }
        return map;
    }

    private static Object search(HashMap<String, Object> map, String key) {
        if (key.equals("*")) {
            return map.values();
        }
        return map.get(key);
    }

    private static Object search(ArrayList map, String key, String predicate) {
        if (key.equals("*")) {
            return map;
        }
        if (!predicate.equals("")) {
            return processPredicate(map, predicate);
        }
        if (key.matches("^-?\\d+$")) {
            int index = Integer.parseInt(key);
            return map.get(index);
        }
        ArrayList response = new ArrayList();
        for (Object item : map) {
            Map<String, Object> itemMap = (Map<String, Object>) item;
            if (itemMap.containsKey(key)) {
                response.add(itemMap.get(key));
            }
        }
        return response;
    }

    private static Object search(Collection map) {
        ArrayList<Object> response = new ArrayList<Object>();
        for (Object item : map) {
            response.add(item);
        }
        return response.get(0);
    }

    public static Object searchByPath(Object map, String path) {

        path = Generic.trimPath(path);

        List<String> stringList = Generic.getKeyList(path);
        if (stringList.size() > 1) {
            String key = Generic.trimKey(stringList.get(0));
            map = search(map, key);
            path = Generic.newKey(stringList);
        } else {
            path = Generic.trimPath(path);
            return search(map, path);
        }
        return searchByPath(map, path);
    }

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
}
