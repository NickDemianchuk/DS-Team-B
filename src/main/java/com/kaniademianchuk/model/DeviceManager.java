package com.kaniademianchuk.model;


import com.kaniademianchuk.api.SmartDeviceApi;

import java.util.*;

public class DeviceManager implements SmartDeviceApi {

    private Map<Integer, Device> allDevices = new HashMap<Integer, Device>();
    private int latestId = 0;

    @Override
    public Optional<Integer> addDevice(Device.Builder deviceBuilder) {
        if (!this.isReadyToBeAdded(deviceBuilder)) {
            return Optional.empty();
        }
        int id = this.latestId++;
        deviceBuilder.setId(id);
        this.allDevices.put(id, deviceBuilder.build());
        return Optional.of(id);
    }

    @Override
    public boolean updateDevice(int id, Device.Builder deviceBuilder) {
        Device prev = this.allDevices.get(id);
        if (prev == null) {
            return false;
        }
        Device.Builder builder = new Device.Builder(deviceBuilder);
        if (!builder.getName().isPresent()) {
            builder.setName(prev.getName());
        }
        if (!builder.isOn().isPresent()) {
            builder.setOn(prev.isOn());
        }
        builder.setId(prev.getId());
        this.allDevices.put(id, builder.build());
        return true;
    }

    @Override
    public boolean removeDevice(int id) {
        if (!this.allDevices.containsKey(id)) {
            return false;
        }
        this.allDevices.remove(id);
        return true;
    }

    @Override
    public Optional<Device> getDeviceById(int id) {
        if (!this.allDevices.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(allDevices.get(id));
    }

    @Override
    public Collection<Device> getAllDevices() {
        return Collections.unmodifiableCollection(this.allDevices.values());
    }

    @Override
    public boolean isReadyToBeAdded(Device.Builder deviceBuilder) {
        return (deviceBuilder.getName().isPresent() && deviceBuilder.isOn().isPresent());
    }
}
