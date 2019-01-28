package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IEventHandler;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.events.EventHandlerType;

import java.util.Objects;

public class DefaultTogglable extends AbstractIdentifiable implements ITogglable {

    private final IEventHandler eventListener;
    private boolean isOn;

    private DefaultTogglable(Integer id, String name, boolean isOn, IEventHandler eventListener) {
        super(id, name);
        this.isOn = isOn;
        this.eventListener = eventListener;
    }

    public DefaultTogglable(String name, boolean isOn, IEventHandler evenListener) {
        this(AbstractIdentifiable.receiveAndIncrementLatestId(), name, isOn, evenListener);
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
        synchronized (this) {
            this.isOn = true;
        }
        this.eventListener.handle(this, EventHandlerType.TURN_ON);
    }

    @Override
    public void turnOff() {
        synchronized (this) {
            this.isOn = false;
        }
        this.eventListener.handle(this, EventHandlerType.TURN_OFF);
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
        if (this.isOn()) {
            this.turnOff();
        } else {
            this.turnOn();
        }
    }

    @Override
    public boolean isOn() {
        return this.isOn;
    }
}
