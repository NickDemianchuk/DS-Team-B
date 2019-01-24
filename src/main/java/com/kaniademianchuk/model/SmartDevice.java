package com.kaniademianchuk.model;

import com.kaniademianchuk.api.ISmartDevice;

import java.util.Collection;

public class SmartDevice<T> implements ISmartDevice<T> {

    private final Integer id;
    private final String name;
    private final Collection<T> functions;

    public SmartDevice(Integer id, String name, Collection<T> functions) {
        this.id = id;
        this.name = name;
        this.functions = functions;
    }

    public SmartDevice(ISmartDevice<T> other) {
        this.id = other.getId();
        this.name = other.getName();
        this.functions = other.getFunctions();
    }

    @Override
    public Integer getId() {
        return id;
    }

    public SmartDevice<T> setId(Integer id) {
        return new SmartDevice<T>(id, this.name, this.functions);
    }

    @Override
    public String getName() {
        return name;
    }

    public SmartDevice<T> setName(String name) {
        return new SmartDevice<T>(this.id, name, this.functions);
    }

    @Override
    public Collection<T> getFunctions() {
        return functions;
    }

    public SmartDevice<T> setFunctions(Collection<T> functions) {
        return new SmartDevice<T>(this.id, this.name, functions);
    }

    @Override
    public void changeFunction(T inp) {

    }
}
