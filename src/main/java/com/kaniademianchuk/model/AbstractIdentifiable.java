package com.kaniademianchuk.model;


import com.kaniademianchuk.api.IIdentifiable;

import java.util.Objects;

abstract class AbstractIdentifiable implements IIdentifiable {

    protected static int latestId = 0;
    protected Integer id;
    protected String name;

    protected AbstractIdentifiable(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractIdentifiable that = (AbstractIdentifiable) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
