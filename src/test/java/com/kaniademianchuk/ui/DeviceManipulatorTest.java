package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.IDimmable;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.DefaultDimmable;
import com.kaniademianchuk.model.DefaultTogglable;
import com.kaniademianchuk.model.Manager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


class DeviceManipulatorTest {
    private Manager<ITogglable> deviceManager;
    private DeviceManipulator deviceManipulator;
    private DefaultTogglable testDevice0;
    private DefaultDimmable testDevice1;

    @BeforeEach
    void setUp() {
        deviceManager = new Manager<ITogglable>();
        testDevice0 = new DefaultTogglable("TestDevice0", true);
        testDevice1 = new DefaultDimmable("TestDevice1", IDimmable.MAX_DIM_LEVEL);
        deviceManager.addDevice(testDevice0);
        deviceManager.addDevice(testDevice1);
    }

    @AfterEach
    void tearDown() {
    }

    void mockCommands(String... commands) {
        String joined = String.join("\n", commands) + "\n\n";
        System.setIn(new ByteArrayInputStream(joined.getBytes()));
    }

    @Test
    void toggleToOff() {
        mockCommands("toggle", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);

        this.deviceManipulator.run("device " + testDevice0.getId());

        assertFalse(testDevice0.isOn());
    }

    @Test
    void toggleToOn() {
        mockCommands("toggle", "exit");
        deviceManipulator = new DeviceManipulator(new Scanner(System.in), deviceManager);
        testDevice0.turnOff();

        this.deviceManipulator.run("device " + testDevice0.getId());

        assertTrue(testDevice0.isOn());
    }
}