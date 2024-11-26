package gui;

import java.net.URL;

public enum Resources {
    LOGO("/gui/images/logo.png");

    private final String JAR_PATH;

    Resources(String jarPath) {
        JAR_PATH = jarPath;
    }

    URL getResource() {
        URL resource = getClass().getResource(JAR_PATH);
        if(resource == null) throw new RuntimeException("[ERROR] " + JAR_PATH + ": not exist in Jar");
        return resource;
    }
}
