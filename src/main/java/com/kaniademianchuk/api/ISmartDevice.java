package com.kaniademianchuk.api;


import java.util.Collection;

public interface ISmartDevice<T> {
    Integer getId();

    ISmartDevice<T> setId(Integer id);

    String getName();

    ISmartDevice<T> setName(String name);

    Collection<T> getFunctions();

    ISmartDevice<T> setFunctions(Collection<T> functions);
}
