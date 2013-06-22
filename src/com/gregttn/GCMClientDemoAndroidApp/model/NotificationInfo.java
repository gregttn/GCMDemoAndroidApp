package com.gregttn.GCMClientDemoAndroidApp.model;

public class NotificationInfo {

    public static final String DEFAULT_TITLE = "Received Notification";
    private final String title;
    private final String message;

    public NotificationInfo(String message) {
        this(DEFAULT_TITLE, message);
    }

    public NotificationInfo(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "[" + this.title + ":" + this.message + "]";
    }
}
