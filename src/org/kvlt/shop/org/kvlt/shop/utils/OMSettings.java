package org.kvlt.shop.org.kvlt.shop.utils;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

public class OMSettings {

    private Properties props;
    private static OMSettings instance;

    public OMSettings() {
        String defaultsName = "defaults";
        String fileName = "config";
        try {
            File file = new File(fileName);
            File defaultsFile = new File(defaultsName);
            if (!file.exists()) {
                Files.copy(defaultsFile.toPath(), new FileOutputStream(fileName));
            }
            props = new Properties();
            props.load(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Properties $() {
        return OMSettings.get().props;
    }

    public static synchronized OMSettings get() {
        return instance == null ? instance = new OMSettings() : instance;
    }

}
