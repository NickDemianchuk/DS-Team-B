package com.kaniademianchuk.api;

public interface IDimmable extends ITogglable {

    int MIN_DIM_LEVEL = 0;
    int MAX_DIM_LEVEL = 100;

    static boolean isValid(Integer dimLevel) {
        return !(dimLevel < MIN_DIM_LEVEL || dimLevel > MAX_DIM_LEVEL);
    }

    /**
     * 0-100
     *
     * @return
     */
    Integer getDimLevel();

    /**
     * 0-100
     *
     * @param dimLevel
     */
    void setDimLevel(Integer dimLevel);
}
