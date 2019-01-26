package com.kaniademianchuk.model;

import com.kaniademianchuk.api.ITogglable;

import java.util.Objects;

public class DefaultTogglable extends AbstractIdentifiable implements ITogglable {

    private boolean isOn;

    private DefaultTogglable(Integer id, String name, boolean isOn) {
        super(id, name);
        this.isOn = isOn;
    }

    public DefaultTogglable(String name, boolean isOn) {
        this(AbstractIdentifiable.receiveAndIncrementLatestId(), name, isOn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DefaultTogglable that = (DefaultTogglable) o;
        return isOn == that.isOn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isOn);
    }

    @Override
    public void turnOn() {
        this.isOn = true;
    }

    @Override
    public void turnOff() {
        this.isOn = false;
    }

    @Override
    public String toString() {
        return "DefaultTogglable{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", isOn=" + this.isOn() +
                '}';
    }

    @Override
    public void toggle() {
        this.isOn = !this.isOn;
    }

    @Override
    public boolean isOn() {
        return this.isOn;
    }
}
