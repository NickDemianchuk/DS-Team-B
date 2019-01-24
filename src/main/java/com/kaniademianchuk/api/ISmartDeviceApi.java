package com.kaniademianchuk.api;

import com.kaniademianchuk.model.SmartDevice;

import java.util.Collection;
import java.util.Optional;

public interface ISmartDeviceApi {
    Optional<Integer> addDevice(SmartDevice<Object> object);

    boolean updateDevice(SmartDevice<Object> object);

    boolean removeDevice(SmartDevice<Object> object);

    boolean removeDevice(Integer id);

    Optional<SmartDevice<Object>> getDeviceById(int id);

    Collection<SmartDevice<Object>> getAllDevices();
}
