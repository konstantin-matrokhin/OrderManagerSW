package org.kvlt.shop;

public class Log {

    private static boolean enabled = OMSettings.$().getProperty("log").equalsIgnoreCase("true");

    public static void $(Object o) {
        if (enabled) System.out.println("[LOG]: " + o);
    }

}
