package com.kaniademianchuk.model;

import com.kaniademianchuk.util.MockEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TogglableGroupTest {

    DefaultTogglable smartSwitch;
    DefaultTogglable smartBulb;
    TogglableGroup<DefaultTogglable> togglableGroup;

    @BeforeEach
    void setUp() {
        smartSwitch = new DefaultTogglable("smart switch", false, new MockEventHandler());
        smartBulb = new DefaultTogglable("smart bulb", true, new MockEventHandler());
        togglableGroup = new TogglableGroup("togglable group", smartBulb, smartSwitch);
    }

    @Test
    void turnOnTest() {
        togglableGroup.turnOn();

        for (DefaultTogglable togglable : togglableGroup.getDevices()) {
            assertEquals(true, togglable.isOn());
        }
    }

    @Test
    void turnOffTest() {
        togglableGroup.turnOff();

        for (DefaultTogglable togglable : togglableGroup.getDevices()) {
            assertEquals(false, togglable.isOn());
        }
    }

    @Test
    void toggleTest() {
        int index = 0;
        boolean[] expectedStates = {!smartBulb.isOn(), !smartSwitch.isOn()};

        for (DefaultTogglable togglable : togglableGroup.getDevices()) {
            assertEquals(expectedStates[index++], togglable.isOn());
        }
    }

    @Test
    void isOnTest() {
        boolean beforeTurningOn = togglableGroup.isOn();
        togglableGroup.turnOn();
        boolean afterTurningOn = togglableGroup.isOn();

        assertEquals(false, beforeTurningOn);
        assertEquals(true, afterTurningOn);
    }
}