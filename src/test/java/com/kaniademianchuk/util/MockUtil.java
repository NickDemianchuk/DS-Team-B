package com.kaniademianchuk.util;

import java.io.ByteArrayInputStream;

public class MockUtil {

    private MockUtil() {
    }

    public static void mockCommands(String... commands) {
        String joined = String.join("\n", commands) + "\n\n";
        System.setIn(new ByteArrayInputStream(joined.getBytes()));
    }
}
