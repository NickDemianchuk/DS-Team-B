package com.kaniademianchuk.model;

import com.kaniademianchuk.api.ITogglable;

import java.util.Map;

public class TogglableGroup<T extends ITogglable> extends DeviceGroup<T> implements ITogglable {

    protected TogglableGroup(Integer id, String name, Map<Integer, T> map) {
        super(id, name, map);
    }

    public TogglableGroup(String name, Map<Integer, T> map) {
        super(name, map);
    }

    public TogglableGroup(String name, T... devices) {
        super(name, devices);
    }

    protected TogglableGroup(Integer id, String name, T... devices) {
        super(id, name, devices);
    }

    @Override
    public void turnOn() {
        this.getDevices().forEach(device -> device.turnOn());
    }

    @Override
    public void turnOff() {
        this.getDevices().forEach(device -> device.turnOff());
    }

    @Override
    public void toggle() {
        this.getDevices().forEach(device -> device.toggle());
    }

    @Override
    public boolean isOn() {
        for (ITogglable i : this.getDevices()) {
            if (!i.isOn()) {
                return false;
            }
        }
        return true;
    }
}
