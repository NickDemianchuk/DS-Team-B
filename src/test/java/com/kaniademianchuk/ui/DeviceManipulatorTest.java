package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.IDimmable;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.DefaultDimmable;
import com.kaniademianchuk.model.DefaultTogglable;
import com.kaniademianchuk.model.Manager;
import com.kaniademianchuk.util.MockEventHandler;
import com.kaniademianchuk.util.MockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DeviceManipulatorTest {
    private Manager<ITogglable> deviceManager;
    private DeviceManipulator deviceManipulator;
    private DefaultTogglable testDevice0;
    private DefaultDimmable testDevice1;

    @BeforeEach
    void setUp() {
        deviceManager = new Manager<>();
        testDevice0 = new DefaultTogglable("TestDevice0", true, new MockEventHandler());
        testDevice1 = new DefaultDimmable("TestDevice1", IDimmable.MAX_DIM_LEVEL, new MockEventHandler());
        deviceManager.addDevice(testDevice0);
        deviceManager.addDevice(testDevice1);
    }

    @Test
    void toggleToOffTest() {
        MockUtil.mockCommands("toggle", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);

        this.deviceManipulator.run("device " + testDevice0.getId());

        assertFalse(testDevice0.isOn());
    }

    @Test
    void toggleToOnTest() {
        MockUtil.mockCommands("toggle", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);
        testDevice0.turnOff();

        this.deviceManipulator.run("device " + testDevice0.getId());

        assertTrue(testDevice0.isOn());
    }

    @Test
    void turnOnTurnedOffTest() {
        MockUtil.mockCommands("on", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);
        testDevice0.turnOff();

        this.deviceManipulator.run("device " + testDevice0.getId());

        assertTrue(testDevice0.isOn());
    }

    @Test
    void turnOnTurnedOnTest() {
        MockUtil.mockCommands("on", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);

        this.deviceManipulator.run("device " + testDevice0.getId());

        assertTrue(testDevice0.isOn());
    }

    @Test
    void turnOffTurnedOffTest() {
        MockUtil.mockCommands("off", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);
        testDevice0.turnOff();

        this.deviceManipulator.run("device " + testDevice0.getId());

        assertFalse(testDevice0.isOn());
    }

    @Test
    void turnOffTurnedOnTest() {
        MockUtil.mockCommands("off", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);

        this.deviceManipulator.run("device " + testDevice0.getId());

        assertFalse(testDevice0.isOn());
    }

    @Test
    void statusTest() {
        MockUtil.mockCommands("status", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        this.deviceManipulator.run("device " + testDevice0.getId());
        String output = baos.toString();

        assertTrue(output.contains(testDevice0.toString()));
    }

    @Test
    void exitHasNoEffectOnDevicesTest(){
        MockUtil.mockCommands("exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        int sizeBeforeExiting = deviceManager.getAllDevices().size();
        this.deviceManipulator.run("device " + testDevice0.getId());
        int sizeAfterExiting = deviceManager.getAllDevices().size();
        String output = baos.toString();

        assertFalse(output.contains(testDevice0.toString()));
        assertEquals(sizeBeforeExiting, sizeAfterExiting);
    }
}