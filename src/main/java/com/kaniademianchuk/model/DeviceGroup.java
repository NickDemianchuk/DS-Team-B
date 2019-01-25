package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IIdentifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DeviceGroup<T extends IIdentifiable> extends AbstractIdentifiable implements IIdentifiable {
    private final Map<Integer, T> devices = new HashMap<>();

    protected DeviceGroup(Integer id, String name, Map<Integer, T> map) {
        super(id, name);
        this.devices.putAll(map);
    }

    public DeviceGroup(String name, Map<Integer, T> map) {
        super(AbstractIdentifiable.receiveAndIncrementLatestId(), name);
        this.devices.putAll(map);
    }

    public DeviceGroup(String name, T... devices) {
        super(AbstractIdentifiable.receiveAndIncrementLatestId(), name);
        for (T device : devices)
            this.devices.put(device.getId(), device);
    }

    protected DeviceGroup(Integer id, String name, T... devices) {
        super(id, name);
        for (T device : devices)
            this.devices.put(device.getId(), device);
    }

    public Collection<T> getDevices() {
        return this.devices.values();
    }

    public T addDevice(T device) {
        return this.devices.put(device.getId(), device);
    }

    public T removeDevice(T device) {
        return this.devices.remove(device.getId());
    }

    public T getDeviceById(int id) {
        return this.devices.get(id);
    }

    public int getSize() {
        return devices.size();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DeviceGroup{id='" + this.getId() +
                "', name='" + this.getName() +
                "', size=" + this.devices.size());
        buffer.append("}");
        return buffer.toString();
    }
}
