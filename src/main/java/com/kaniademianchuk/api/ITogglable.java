package com.kaniademianchuk.api;

public interface ITogglable extends IIdentifiable {

    static int latestId = 0;

    ITogglable turnOn();

    ITogglable turnOff();

    ITogglable toggle();

    boolean isOn();

}
