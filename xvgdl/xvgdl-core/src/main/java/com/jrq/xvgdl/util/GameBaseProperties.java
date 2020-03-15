package com.jrq.xvgdl.util;

import java.util.Properties;

public class GameBaseProperties extends Properties {


    public final Long getLongValue(String attributeName, long defaultValue) {
        try {
            return Long.parseLong(getProperty(attributeName));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public final Integer getIntegerValue(String attributeName, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(attributeName));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public final Boolean getBooleanValue(String attributeName, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(getProperty(attributeName));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public final double getDoubleValue(String attributeName, double defaultValue) {
        try {
            return Double.parseDouble(getProperty(attributeName));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
