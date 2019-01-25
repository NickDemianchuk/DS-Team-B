package com.kaniademianchuk.api;

public interface IDimmable extends ITogglable {

    int MIN_DIMM_LEVEL = 0;
    int MAX_DIMM_LEVEL = 100;

    static boolean isValid(Integer dimmLevel) {
        return !(dimmLevel < MIN_DIMM_LEVEL || dimmLevel > MAX_DIMM_LEVEL);
    }

    /**
     * 0-100
     *
     * @return
     */
    Integer getDimmLevel();

    /**
     * 0-100
     *
     * @param dimmLevel
     */
    void setDimmLevel(Integer dimmLevel);
}
