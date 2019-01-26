package com.kaniademianchuk.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultTogglableTest {

    DefaultTogglable turnedOffDevice;
    DefaultTogglable turnedOnDevice;

    @BeforeEach
    void setUp() {
        turnedOffDevice = new DefaultTogglable("turnedOffDevice", false);
        turnedOnDevice = new DefaultTogglable("turnedOnDevice", true);
    }

    @Test
    void turnOnTurnedOff() {
        turnedOffDevice.turnOn();

        assertTrue(turnedOffDevice.isOn());
    }

    @Test
    void turnOnTurnedOn() {
        turnedOnDevice.turnOn();

        assertTrue(turnedOnDevice.isOn());
    }

    @Test
    void turnOffTurnedOn() {
        turnedOnDevice.turnOff();

        assertFalse(turnedOnDevice.isOn());
    }

    @Test
    void turnOffTurnedOff() {
        turnedOffDevice.turnOff();

        assertFalse(turnedOffDevice.isOn());
    }

    @Test
    void toggle() {
        turnedOnDevice.toggle();
        turnedOffDevice.toggle();

        assertFalse(turnedOnDevice.isOn());
        assertTrue(turnedOffDevice.isOn());
    }

    @Test
    void isOn() {
        assertTrue(turnedOnDevice.isOn());
        assertFalse(turnedOffDevice.isOn());
    }

    @Test
    void getId() {
        DefaultTogglable togglable = new DefaultTogglable("togglable", true);
        Integer expectedId = AbstractIdentifiable.receiveAndIncrementLatestId() - 1;

        assertEquals(expectedId, togglable.getId());
    }

    @Test
    void getName() {
        String expectedTurnedOff = "turnedOffDevice";
        String expectedTurnedOn = "turnedOnDevice";

        assertEquals(expectedTurnedOff, turnedOffDevice.getName());
        assertEquals(expectedTurnedOn, turnedOnDevice.getName());
    }

    @Test
    void setId() {
        Integer newId = new Integer(5);
        int oldId = turnedOffDevice.getId();

        turnedOffDevice.setId(newId);

        assertNotEquals(oldId, turnedOffDevice.getId());
        assertEquals(newId, turnedOffDevice.getId());
    }

    @Test
    void setName() {
        String newName = "brand new togglable device";
        String oldName = turnedOnDevice.getName();

        turnedOnDevice.setName(newName);

        assertNotEquals(oldName, turnedOnDevice.getName());
        assertEquals(newName, turnedOnDevice.getName());
    }

    @Test
    void toStringTest() {
        Integer turnedOffId = turnedOffDevice.getId();
        Integer turnedOnId = turnedOnDevice.getId();
        String turnedOffToString = "DefaultTogglable{id=" + turnedOffId + ", " +
                "name='turnedOffDevice', " +
                "isOn=false}";
        String turnedOnToString = "DefaultTogglable{id=" + turnedOnId + ", " +
                "name='turnedOnDevice', " +
                "isOn=true}";

        assertEquals(turnedOffToString, turnedOffDevice.toString());
        assertEquals(turnedOnToString, turnedOnDevice.toString());
    }

    @Test
    void equalsTest() {
        DefaultTogglable turnedOffTogglable = new DefaultTogglable("turnedOffDevice", false);
        Integer desiredId = turnedOffDevice.getId();

        turnedOffTogglable.setId(desiredId);

        assertTrue(turnedOffDevice.equals(turnedOffTogglable));
    }

    @Test
    void hashCodeTest() {
        DefaultTogglable turnedOffTogglable = new DefaultTogglable("turnedOffDevice", false);
        Integer desiredId = turnedOffDevice.getId();

        turnedOffTogglable.setId(desiredId);
        int expectedHashCode = turnedOffTogglable.hashCode();

        assertEquals(expectedHashCode, turnedOffDevice.hashCode());
    }
}