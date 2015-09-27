package com.jbjohn.model;

import com.jbjohn.model.common.InMap;
import com.jbjohn.utils.Generic;

/**
 */
public class Caster extends InMap {

    private static Type type = Type.STRING;

    public static Object setByPath(Object map, String key, Type type) {
        Caster.type = type;
        key = Generic.trimPath(key);
        setOperation(new Caster());

        return setByPath(map, key);
    }

    @Override
    public Object getValue(Object value) {
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
