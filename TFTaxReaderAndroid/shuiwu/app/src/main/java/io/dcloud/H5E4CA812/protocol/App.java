package io.dcloud.H5E4CA812.protocol;


import io.dcloud.H5E4CA812.GlobleApplication;

public class App {
    private static GlobleApplication app;

    public static void setApp(GlobleApplication application) {
        app = application;
    }

    public static GlobleApplication getApp() {
        return app;
    }

}
