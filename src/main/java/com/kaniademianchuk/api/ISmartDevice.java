package com.kaniademianchuk.api;

import java.util.Collection;

public interface ISmartDevice<T> {
    Integer getId();
    String getName();
    Collection<T> getFunctions();
    void changeFunction(T inp);
}
