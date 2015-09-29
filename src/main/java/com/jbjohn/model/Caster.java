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
        try {
            return type.getValue(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public enum Type {
        STRING("String") {
            @Override
            Object getValue(Object value) {
                if (!(value instanceof String)) {
                    return String.valueOf(value);
                }
                return value;
            }
        },
        INTEGER("Integer") {
            @Override
            Object getValue(Object value) {
                if (!(value instanceof Integer) && (value instanceof String) && value.toString().matches("^-?\\d+$")) {
                    return Integer.parseInt(value.toString());
                }
                return value;
            }
        },
        BOOLEAN("Boolean") {
            @Override
            Object getValue(Object value) {
                if (!(value instanceof Boolean) && (value instanceof String) && (value.toString().equalsIgnoreCase("true") || value.toString().equalsIgnoreCase("false"))) {
                    return Boolean.parseBoolean(value.toString());
                }
                return value;
            }
        },
        DOUBLE("Double") {
            @Override
            Object getValue(Object value) {
                if (!(value instanceof Double) && (value instanceof String) && value.toString().matches("^-?\\d+(?:[.]\\d+)$")) {
                    return Double.parseDouble(value.toString());
                }
                return value;
            }
        };

        private String type;

        Type(String type) {
            this.type = type;
        }

        abstract Object getValue(Object value);
    }
}
