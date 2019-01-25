package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IDimmable;
import com.kaniademianchuk.api.IIdentifiable;
import com.kaniademianchuk.api.ITogglable;

public class DefaultDimmable extends AbstractIdentifiable implements IDimmable {

    private final Integer dimmLevel;

    protected DefaultDimmable(Integer id, String name, Integer dimmLevel) {
        super(id, name);
        if (!IDimmable.isValid(dimmLevel)) {
            throw new RuntimeException("Dimmlevel no bueno");
        }
        this.dimmLevel = dimmLevel;
    }

    public DefaultDimmable(String name, Integer dimmLevel) {
        this(AbstractIdentifiable.latestId++, name, dimmLevel);
    }

    @Override
    public String toString() {
        return "DefaultDimmable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dimmLevel=" + dimmLevel +
                '}';
    }

    @Override
    public IDimmable turnOn() {
        return new DefaultDimmable(this.id, this.name, MAX_DIMM_LEVEL);
    }

    @Override
    public IDimmable turnOff() {
        return new DefaultDimmable(this.id, this.name, MIN_DIMM_LEVEL);
    }


    @Override
    public IDimmable toggle() {
        return new DefaultDimmable(this.id, this.name, this.isOn() ? MIN_DIMM_LEVEL : MAX_DIMM_LEVEL);
    }

    @Override
    public boolean isOn() {
        return this.dimmLevel > MIN_DIMM_LEVEL;
    }

    @Override
    public IIdentifiable setId(Integer id) {
        return new DefaultDimmable(id, this.name, this.dimmLevel);
    }


    @Override
    public ITogglable setName(String name) {
        return new DefaultDimmable(this.id, name, this.dimmLevel);
    }

    @Override
    public Integer getDimmLevel() {
        return this.dimmLevel;
    }

    @Override
    public IDimmable setDimmLevel(Integer dimmLevel) {
        return new DefaultDimmable(this.id, this.name, dimmLevel);
    }
}
