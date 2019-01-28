package com.kaniademianchuk.util;

import java.util.regex.Pattern;

public class PatternUtil {
    public static final String DIM = "dim (\\d+)";
    public static final String DEVICE = "device (\\d+)";
    public static final String GROUP = "group (\\d+)";
    public static final String ADD_DEVICE = "addDevice (\\d+)";
    public static final String REMOVE_DEVICE = "removeDevice (\\d+)";
    public static final String REMOVE_GROUP = "removeGroup (\\d+)";

    public static final Pattern DIM_PATTERN = Pattern.compile(PatternUtil.DIM);
    public static final Pattern DEVICE_PATTERN = Pattern.compile(PatternUtil.DEVICE);
    public static final Pattern GROUP_PATTERN = Pattern.compile(PatternUtil.GROUP);
    public static final Pattern ADD_DEVICE_PATTERN = Pattern.compile(PatternUtil.ADD_DEVICE);
    public static final Pattern REMOVE_DEVICE_PATTERN = Pattern.compile(PatternUtil.REMOVE_DEVICE);
    public static final Pattern REMOVE_GROUP_PATTERN = Pattern.compile(PatternUtil.REMOVE_GROUP);

    private PatternUtil() {
    }
}
