package com.kaniademianchuk.api;

import com.kaniademianchuk.model.Device;

import java.util.Collection;
import java.util.Optional;

public interface SmartDeviceApi {
    Optional<Integer> addDevice(Device.Builder deviceBuilder);

    boolean updateDevice(int id, Device.Builder deviceBuilder);

    boolean removeDevice(int id);

    Optional<Device> getDeviceById(int id);

    Collection<Device> getAllDevices();

    boolean isReadyToBeAdded(Device.Builder deviceBuilder);
}
