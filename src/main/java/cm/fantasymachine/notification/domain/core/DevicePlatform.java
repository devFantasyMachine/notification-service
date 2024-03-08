/*
 * Copyright (c) 2023. Create by Yowyob
 */

package cm.fantasymachine.notification.domain.core;

public enum DevicePlatform {

    ANDROID("android"),
    DESKTOP("windows"),
    IOS("ios"),
    WEB("web"),
    UNKNOWN("unknown");

    private final String tagValue;

    DevicePlatform(String tagValue) {
        this.tagValue = tagValue.toUpperCase();
    }

    public static DevicePlatform resolve(String deviceOs) {

        if (deviceOs == null || deviceOs.trim().length() < 1)
            return DevicePlatform.UNKNOWN;

        return switch (deviceOs.trim().toUpperCase()) {
            case "ANDROID" -> DevicePlatform.ANDROID;
            case "IOS" -> DevicePlatform.IOS;
            case "windows" -> DevicePlatform.DESKTOP;
            default -> DevicePlatform.UNKNOWN;
        };
    }

    public String getTagValue() {
        return tagValue;
    }
}
