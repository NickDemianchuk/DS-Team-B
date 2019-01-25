package com.kaniademianchuk.api;

import java.util.Collection;
import java.util.Optional;

public interface ISmartDeviceApi {
    Optional<Integer> addDevice(ISmartDevice<ITogglable> object);

    boolean updateDevice(ISmartDevice<ITogglable> object);

    boolean removeDevice(ISmartDevice<ITogglable> object);

    boolean removeDevice(Integer id);

    Optional<ISmartDevice<ITogglable>> getDeviceById(int id);

    Collection<ISmartDevice<ITogglable>> getAllDevices();
}
