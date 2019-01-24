package com.kaniademianchuk.model;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.Objects;
import java.util.Optional;

public class Device {

    private final int id;
    private final String name;
    private final boolean isOn;

    private Device(int id, String name, boolean isOn) {
        this.id = id;
        this.name = name;
        this.isOn = isOn;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return id == device.id &&
                Objects.equals(name, device.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Device[Id: " + id + ", Name: " + name + ", isOn: " + isOn + "]";
    }

    public boolean isOn() {
        return isOn;
    }

    public static class Builder {
        private Optional<Integer> id = Optional.empty();
        private Optional<String> name = Optional.empty();
        private Optional<Boolean> isOn = Optional.empty();

        public Builder() {
        }

        public Builder(Builder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.isOn = builder.isOn;
        }

        public Optional<Integer> getId() {
            return id;
        }

        protected Builder setId(int id) {
            this.id = Optional.of(id);
            return this;
        }

        public Optional<String> getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = Optional.of(name);
            return this;
        }

        public Optional<Boolean> isOn() {
            return isOn;
        }

        public Builder setOn(boolean on) {
            isOn = Optional.of(on);
            return this;
        }

        public Device build() {
            if (!(this.name.isPresent() && this.id.isPresent() && this.isOn.isPresent())) {
                throw new InvalidStateException("Not everything filled");
            }
            return new Device(this.id.get(), this.name.get(), this.isOn.get());
        }
    }
}
