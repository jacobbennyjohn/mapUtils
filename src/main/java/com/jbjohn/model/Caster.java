package com.jbjohn.model;

import com.jbjohn.utils.Generic;

import java.util.*;

/**
 */
public class Caster {

    private static Type type = Type.STRING;

    public static Object setByPath(Object map, String key, Type type) {
        Caster.type = type;
        key = Generic.trimPath(key);

        if (map instanceof HashMap) {
            return setByPath((HashMap<String, Object>) map, key);
        } else {
            return setByPath((ArrayList) map, key);
        }
    }

    private static Object setByPath(Object map, String key) {

        if (map instanceof HashMap) {
            return setByPath((HashMap<String, Object>) map, key);
        } else if (map instanceof ArrayList)  {
            return setByPath((ArrayList) map, key);
        }
        return map;
    }

    private static Object setByPath(HashMap<String, Object> map, String path) {

        List<String> stringList = Generic.getKeyList(path);
        if (stringList.size() > 1) {
            String key = Generic.trimKey(stringList.get(0));
            String newKey = Generic.newKey(stringList);
            if (key.equals("*")) {
                HashMap<String, Object> tempMap = (HashMap<String, Object>) map.clone();
                Iterator it = tempMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    map.put((String) pair.getKey(), setByPath(map.get(pair.getKey()), newKey));
                    it.remove();
                }
            } else {
                if (map.get(key) instanceof HashMap) {
                    map.put(key, setByPath(map.get(key), newKey));
                }
                if (map.get(key) instanceof ArrayList) {
                    map.put(key, setByPath(map.get(key), newKey));
                }
            }
        } else {
            path = Generic.trimKey(path);
            if (path.equals("*")) {
                HashMap<String, Object> tempMap = (HashMap<String, Object>) map.clone();
                Iterator it = tempMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    map.put((String) pair.getKey(), getValue(map.get(pair.getKey())));
                    it.remove();
                }
            } else {
                path = Generic.trimKey(path);
                map.put(path, getValue(map.get(path)));
            }
        }
        return map;
    }

    public static Object setByPath(ArrayList map, String path) {

        int index = 0;
        List<String> stringList = Generic.getKeyList(path);
        if (stringList.size() > 1) {
            String key = Generic.trimKey(stringList.get(0));
            String newKey = Generic.newKey(stringList);
            if (key.equals("*")) {
                int counter = 0;
                for (Object values : map) {
                    map.set(counter, setByPath(map.get(counter), newKey));
                    counter++;
                }
            } else {
                if (key.matches("^-?\\d+$")) {
                    index = Integer.parseInt(key);
                }
                if (map.get(index) instanceof HashMap) {
                    map.set(index, setByPath(map.get(index), newKey));
                }
                if (map.get(index) instanceof ArrayList) {
                    map.set(index, setByPath(map.get(index), newKey));
                }
            }
        } else {
            path = Generic.trimKey(path);
            if (path.equals("*")) {
                int counter = 0;
                for (Object values : map) {
                    map.set(counter, getValue(map.get(counter)));
                    counter++;
                }
            } else {
                index = Integer.parseInt(path);
                map.set(index, getValue(map.get(index)));
            }
        }

        return map;
    }

    private static Object getValue(Object value) {
        Object response = value;
        try {
            switch (type) {
                case STRING:
                    if (!(value instanceof String)) {
                        response = String.valueOf(value);
                    }
                    break;
                case INTEGER:
                    if (!(value instanceof Integer) && value.toString().matches("^-?\\d+$")) {
                        response = Integer.parseInt(value.toString());
                    }
                    break;
                case BOOLEAN:
                    if (!(value instanceof Boolean) && (value.toString().equalsIgnoreCase("true") || value.toString().equalsIgnoreCase("false"))) {
                        response = Boolean.parseBoolean(value.toString());
                    }
                    break;
                case FLOAT:
                    if (!(value instanceof Float) && value.toString().matches("^-?\\d+(?:[.]\\d+)$")) {
                        response = Float.parseFloat(value.toString());
                    }
                    break;
                default:
                    response = value;
                    break;
            }
        } catch (Exception e) {
            // TODO log exception
        }
        return response;
    }

    public enum Type {
        STRING("String"),
        INTEGER("Integer"),
        BOOLEAN("Boolean"),
        FLOAT("Float");

        private String type;

        Type(String type) {
            this.type = type;
        }
    }
}
