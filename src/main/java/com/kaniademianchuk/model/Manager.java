package com.kaniademianchuk.model;


import com.kaniademianchuk.api.IIdentifiable;

import java.util.*;

public class Manager<T extends IIdentifiable> {

    private final Map<Integer, T> allDevices;

    public Manager() {
        this(new HashMap<>());
    }

    public Manager(Map<Integer, T> map) {
        this.allDevices = map;
    }

    public Optional<T> addDevice(T device) {
        this.allDevices.put(device.getId(), device);
        return Optional.of(device);
    }

    public boolean updateDevice(T object) {
        Integer id = object.getId();
        if (id == null) {
            return false;
        }
        this.allDevices.put(id, object);
        return true;
    }

    public boolean removeDevice(T device) {
        return this.removeDevice(device.getId());
    }

    public boolean removeDevice(Integer id) {
        return (this.allDevices.remove(id) != null);
    }

    public Optional<T> getDeviceById(int id) {
        if (!this.allDevices.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(allDevices.get(id));
    }

    public Collection<T> getAllDevices() {
        return Collections.unmodifiableCollection(this.allDevices.values());
    }
}
