package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IDimmable;
import com.kaniademianchuk.api.IEventHandler;
import com.kaniademianchuk.events.EventHandlerType;

public class DefaultDimmable extends AbstractIdentifiable implements IDimmable {

    private final IEventHandler eventListener;
    private Integer dimLevel;

    private DefaultDimmable(Integer id, String name, Integer dimLevel, IEventHandler eventListener) {
        super(id, name);
        this.eventListener = eventListener;
        this.setDimLevel(dimLevel);
    }

    public DefaultDimmable(String name, Integer dimLevel, IEventHandler eventListener) {
        this(AbstractIdentifiable.receiveAndIncrementLatestId(), name, dimLevel, eventListener);
    }

    @Override
    public String toString() {
        return "DefaultDimmable{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", dimLevel=" + this.getDimLevel() +
                '}';
    }

    @Override
    public void turnOn() {
        synchronized (this) {
            this.dimLevel = MAX_DIM_LEVEL;
        }
        this.eventListener.handle(this, EventHandlerType.TURN_ON);
    }

    @Override
    public void turnOff() {
        synchronized (this) {
            this.dimLevel = MIN_DIM_LEVEL;
        }
        this.eventListener.handle(this, EventHandlerType.TURN_OFF);
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
        return this.dimLevel > MIN_DIM_LEVEL;
    }

    @Override
    public Integer getDimLevel() {
        return this.dimLevel;
    }

    @Override
    public void setDimLevel(Integer dimLevel) {
        if (!IDimmable.isValid(dimLevel)) {
            throw new RuntimeException("Invalid dim level");
        }
        synchronized (this) {
            this.dimLevel = dimLevel;
        }
        if (dimLevel > 0) {
            this.eventListener.handle(this, EventHandlerType.TURN_ON);
        } else {
            this.eventListener.handle(this, EventHandlerType.TURN_OFF);
        }
    }
}
