package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IDimmable;

public class DefaultDimmable extends AbstractIdentifiable implements IDimmable {

    private Integer dimLevel;

    private DefaultDimmable(Integer id, String name, Integer dimLevel) {
        super(id, name);
        if (!IDimmable.isValid(dimLevel)) {
            throw new RuntimeException("Invalid dim level");
        }
        this.dimLevel = dimLevel;
    }

    public DefaultDimmable(String name, Integer dimLevel) {
        this(AbstractIdentifiable.receiveAndIncrementLatestId(), name, dimLevel);
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
        this.dimLevel = MAX_DIM_LEVEL;
    }

    @Override
    public void turnOff() {
        this.dimLevel = MIN_DIM_LEVEL;
    }

    @Override
    public void toggle() {
        this.dimLevel = this.isOn() ? MIN_DIM_LEVEL : MAX_DIM_LEVEL;
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
        this.dimLevel = dimLevel;
    }
}
