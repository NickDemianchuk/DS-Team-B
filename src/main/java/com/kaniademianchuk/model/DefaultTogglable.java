package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IIdentifiable;
import com.kaniademianchuk.api.ITogglable;

import java.util.Objects;

public class DefaultTogglable extends AbstractIdentifiable implements ITogglable {

    private final boolean isOn;

    protected DefaultTogglable(Integer id, String name, boolean isOn) {
        super(id, name);
        this.isOn = isOn;
    }

    public DefaultTogglable(String name, boolean isOn) {
        this(AbstractIdentifiable.latestId++, name, isOn);
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
    public DefaultTogglable turnOn() {
        return new DefaultTogglable(this.id, this.name, true);
    }

    @Override
    public DefaultTogglable turnOff() {
        return new DefaultTogglable(this.id, this.name, false);
    }

    @Override
    public String toString() {
        return "DefaultTogglable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isOn=" + isOn +
                '}';
    }

    @Override
    public DefaultTogglable toggle() {
        return new DefaultTogglable(this.id, this.name, !this.isOn);
    }

    @Override
    public boolean isOn() {
        return this.isOn;
    }


    @Override
    public IIdentifiable setId(Integer id) {
        return new DefaultTogglable(id, this.name, this.isOn);
    }

    @Override
    public ITogglable setName(String name) {
        return new DefaultTogglable(this.id, name, this.isOn);
    }
}
