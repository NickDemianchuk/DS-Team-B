package com.kaniademianchuk.model;


import com.kaniademianchuk.api.IIdentifiable;

import java.util.Objects;

public abstract class AbstractIdentifiable implements IIdentifiable {

    private static int latestId = 0;
    private Integer id;
    private String name;

    protected AbstractIdentifiable(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static int receiveAndIncrementLatestId() {
        return AbstractIdentifiable.latestId++;
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
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
