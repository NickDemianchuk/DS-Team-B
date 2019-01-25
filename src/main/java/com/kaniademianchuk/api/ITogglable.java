package com.kaniademianchuk.api;

public interface ITogglable extends IIdentifiable {

    static int latestId = 0;

    void turnOn();

    void turnOff();

    void toggle();

    /**
     * @return Returns true if a device or all devices in a group are on
     * false otherwise
     */
    boolean isOn();

}
