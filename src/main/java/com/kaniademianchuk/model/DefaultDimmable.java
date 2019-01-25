package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IDimmable;

public class DefaultDimmable extends AbstractIdentifiable implements IDimmable {

    private Integer dimmLevel;

    private DefaultDimmable(Integer id, String name, Integer dimmLevel) {
        super(id, name);
        if (!IDimmable.isValid(dimmLevel)) {
            throw new RuntimeException("Dimmlevel no bueno");
        }
        this.dimmLevel = dimmLevel;
    }

    public DefaultDimmable(String name, Integer dimmLevel) {
        this(AbstractIdentifiable.receiveAndIncrementLatestId(), name, dimmLevel);
    }

    @Override
    public String toString() {
        return "DefaultDimmable{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", dimmLevel=" + this.dimmLevel+
                '}';
    }

    @Override
    public void turnOn() {
        this.dimmLevel = MAX_DIMM_LEVEL;
    }

    @Override
    public void turnOff() {
        this.dimmLevel = MIN_DIMM_LEVEL;
    }


    @Override
    public void toggle() {
        this.dimmLevel = this.isOn() ? MIN_DIMM_LEVEL : MAX_DIMM_LEVEL;
    }

    @Override
    public boolean isOn() {
        return this.dimmLevel > MIN_DIMM_LEVEL;
    }

    @Override
    public Integer getDimmLevel() {
        return this.dimmLevel;
    }

    @Override
    public void setDimmLevel(Integer dimmLevel) {
        this.dimmLevel = dimmLevel;
    }
}
