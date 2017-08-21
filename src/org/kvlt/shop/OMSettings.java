package org.kvlt.shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class OMSettings {

    private Properties props;
    private Properties defaults;
    private String fileName = "config";
    private String defaultsName = "defaults";

    private static OMSettings instance;

    public OMSettings() {
        try {
            File file = new File(fileName);
            File defaultsFile = new File(defaultsName);
            if (!file.exists()) {
                Files.copy(defaultsFile.toPath(), new FileOutputStream(defaultsFile));
            }
            props = new Properties();
            props.load(new FileInputStream(fileName));
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
