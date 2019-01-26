package com.kaniademianchuk.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDimmableTest {

    DefaultDimmable turnedOffDevice;
    DefaultDimmable turnedOnDevice;

    @BeforeEach
    void setUp() {
        turnedOffDevice = new DefaultDimmable("turnedOffDevice", 0);
        turnedOnDevice = new DefaultDimmable("turnedOnDevice", 100);
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
        DefaultDimmable dimmable = new DefaultDimmable("dimmable", 40);
        Integer expectedId = AbstractIdentifiable.receiveAndIncrementLatestId() - 1;

        assertEquals(expectedId, dimmable.getId());
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
        Integer newId = new Integer(50);
        int oldId = turnedOffDevice.getId();

        turnedOffDevice.setId(newId);

        assertNotEquals(oldId, turnedOffDevice.getId());
        assertEquals(newId, turnedOffDevice.getId());
    }

    @Test
    void setName() {
        String newName = "brand new dimmable device";
        String oldName = turnedOnDevice.getName();

        turnedOnDevice.setName(newName);

        assertNotEquals(oldName, turnedOnDevice.getName());
        assertEquals(newName, turnedOnDevice.getName());
    }

    @Test
    void getDimLevel() {
        Integer expectedTurnedOff = new Integer(0);
        Integer expectedTurnedOn = new Integer(100);

        assertEquals(expectedTurnedOff, turnedOffDevice.getDimLevel());
        assertEquals(expectedTurnedOn, turnedOnDevice.getDimLevel());
    }

    @Test
    void setDimLevel() {
        Integer newDimLevel = new Integer(60);
        Integer oldDimLevel = turnedOnDevice.getDimLevel();

        turnedOnDevice.setDimLevel(newDimLevel);

        assertNotEquals(oldDimLevel, turnedOnDevice.getDimLevel());
        assertEquals(newDimLevel, turnedOnDevice.getDimLevel());
    }

    @Test
    void toStringTest() {
        Integer turnedOffId = turnedOffDevice.getId();
        Integer turnedOnId = turnedOnDevice.getId();
        String turnedOffToString = "DefaultDimmable{id=" + turnedOffId + ", " +
                "name='turnedOffDevice', " +
                "dimLevel=0}";
        String turnedOnToString = "DefaultDimmable{id=" + turnedOnId + ", " +
                "name='turnedOnDevice', " +
                "dimLevel=100}";

        assertEquals(turnedOffToString, turnedOffDevice.toString());
        assertEquals(turnedOnToString, turnedOnDevice.toString());
    }

    @Test
    void equalsTest() {
        DefaultDimmable turnedOffDimmable = new DefaultDimmable("turnedOffDevice", 0);
        Integer desiredId = turnedOffDevice.getId();

        turnedOffDimmable.setId(desiredId);

        assertTrue(turnedOffDevice.equals(turnedOffDimmable));
    }

    @Test
    void hashCodeTest() {
        DefaultDimmable turnedOffDimmable = new DefaultDimmable("turnedOffDevice", 0);
        Integer desiredId = turnedOffDevice.getId();

        turnedOffDimmable.setId(desiredId);
        int expectedHashCode = turnedOffDimmable.hashCode();

        assertEquals(expectedHashCode, turnedOffDevice.hashCode());
    }
}