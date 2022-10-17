package com.borisjerev.leadconsult.util;

public final class HelpUtil {

    private HelpUtil() {}

    public static boolean isEmpty(String variable) {
        return variable == null || variable.isBlank();
    }
}
