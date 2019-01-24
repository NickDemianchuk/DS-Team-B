package com.kaniademianchuk.model;


import com.kaniademianchuk.api.ISmartDeviceApi;

import java.util.*;

public class DeviceManager implements ISmartDeviceApi {

    private Map<Integer, SmartDevice<Object>> allDevices = new HashMap<>();
    private int latestId = 0;

    @Override
    public Optional<Integer> addDevice(SmartDevice<Object> object) {
        int id = this.latestId++;
        object.setId(id);
        this.allDevices.put(id, object);
        return Optional.of(id);
    }

    @Override
    public boolean updateDevice(SmartDevice<Object> object) {
        Integer id = object.getId();
        if (id == null) {
            return false;
        }
        this.allDevices.put(id, object);
        return true;
    }

    @Override
    public boolean removeDevice(SmartDevice<Object> device) {
        return this.removeDevice(device.getId());
    }

    @Override
    public boolean removeDevice(Integer id) {
        if (!this.allDevices.containsKey(id)) {
            return false;
        }
        this.allDevices.remove(id);
        return true;
    }

    @Override
    public Optional<SmartDevice<Object>> getDeviceById(int id) {
        if (!this.allDevices.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(allDevices.get(id));
    }

    @Override
    public Collection<SmartDevice<Object>> getAllDevices() {
        return Collections.unmodifiableCollection(this.allDevices.values());
    }
}
