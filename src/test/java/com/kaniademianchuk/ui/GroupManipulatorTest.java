package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.IDimmable;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.*;
import com.kaniademianchuk.util.MockUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

class GroupManipulatorTest {
    private Manager<TogglableGroup<ITogglable>> groupManager;
    private Manager<ITogglable> deviceManager;
    private GroupManipulator groupManipulator;
    private ITogglable testDevice0;
    private ITogglable testDevice1;
    private TogglableGroup<ITogglable> deviceGroup;

    @BeforeEach
    void setUp() {
        groupManager = new Manager<>();
        deviceManager = new Manager<>();
        testDevice0 = new DefaultTogglable("TestDevice0", true);
        testDevice1 = new DefaultDimmable("TestDevice1", IDimmable.MAX_DIM_LEVEL);
        deviceGroup = new TogglableGroup<>("smart devices", testDevice0, testDevice1);
        deviceManager.addDevice(testDevice0);
        deviceManager.addDevice(testDevice1);
        groupManager.addDevice(deviceGroup);
    }

    @Test
    void addDevice() {
        ITogglable newDevice = new DefaultDimmable("new smart bulb", IDimmable.MAX_DIM_LEVEL);
        deviceManager.addDevice(newDevice);
        int newId = newDevice.getId();
        MockUtil.mockCommands("addDevice " + newId, "exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);

        int sizeBeforeAdding = deviceGroup.getSize();
        this.groupManipulator.run("group " + deviceGroup.getId());
        int sizeAfterAdding = deviceGroup.getSize();

        assertNotEquals(sizeBeforeAdding, sizeAfterAdding);
        assertEquals(sizeBeforeAdding + 1, sizeAfterAdding);
        assertEquals(newDevice, deviceGroup.getDeviceById(newId));
    }

    @Test
    void addAlreadyPresentDevice() {
        int presentId = testDevice0.getId();
        String errorMessage = String.format("Group contains device with id %d already", presentId);
        MockUtil.mockCommands("addDevice " + presentId, "exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setErr(new PrintStream(baos));
        this.groupManipulator.run("group " + deviceGroup.getId());
        String output = baos.toString();

        assertFalse(output.contains(deviceGroup.toString()));
        assertTrue(output.contains(errorMessage));
    }

    @Test
    void addUnexistingDevice() {
        int unexistingId = Integer.MAX_VALUE;
        String errorMessage = String.format("Device with id %d not found", unexistingId);
        MockUtil.mockCommands("addDevice " + unexistingId, "exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setErr(new PrintStream(baos));
        this.groupManipulator.run("group " + deviceGroup.getId());
        String output = baos.toString();

        assertFalse(output.contains(deviceGroup.toString()));
        assertTrue(output.contains(errorMessage));
    }

    @Test
    void removeDevice() {
        ITogglable deviceToBeRemoved = new DefaultTogglable("useless smart bulb", false);
        deviceManager.addDevice(deviceToBeRemoved);
        int idToBeRemoved = deviceToBeRemoved.getId();
        deviceGroup.addDevice(deviceToBeRemoved);
        MockUtil.mockCommands("removeDevice " + idToBeRemoved, "exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);

        int sizeBeforeRemoving = deviceGroup.getSize();
        this.groupManipulator.run("group " + deviceGroup.getId());
        int sizeAfterRemoving = deviceGroup.getSize();

        assertNotEquals(sizeBeforeRemoving, sizeAfterRemoving);
        assertEquals(sizeBeforeRemoving - 1, sizeAfterRemoving);
        assertNull(deviceGroup.getDeviceById(idToBeRemoved));
    }

    @Test
    void removeUnexistingDevice() {
        int unexistingId = deviceManager.getAllDevices().size() + 1;
        String errorMessage = String.format("Device with id %d not found in group", unexistingId);
        MockUtil.mockCommands("removeDevice " + unexistingId, "exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setErr(new PrintStream(baos));
        this.groupManipulator.run("group " + deviceGroup.getId());
        String output = baos.toString();

        assertFalse(output.contains(deviceGroup.toString()));
        assertTrue(output.contains(errorMessage));
    }

    @Test
    void listDevices() {
        MockUtil.mockCommands("list", "exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        this.groupManipulator.run("group " + deviceGroup.getId());
        String output = baos.toString();

        assertTrue(output.contains(deviceGroup.getDevices().toString()));
    }

    @Test
    void toggleToOff() {
        MockUtil.mockCommands("toggle", "exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);

        this.groupManipulator.run("group " + deviceGroup.getId());

        assertFalse(deviceGroup.isOn());
    }

    @Test
    void toggleToOn() {
        MockUtil.mockCommands("toggle", "exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);
        deviceGroup.turnOff();

        this.groupManipulator.run("group " + deviceGroup.getId());

        assertTrue(deviceGroup.isOn());
    }

    @Test
    void exitHasNoEffectOnDevices() {
        MockUtil.mockCommands("exit");
        groupManipulator = new GroupManipulator(new Scanner(System.in), groupManager, deviceManager);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        int sizeBeforeExiting = deviceGroup.getSize();
        this.groupManipulator.run("group " + deviceGroup.getId());
        int sizeAfterExiting = deviceGroup.getSize();
        String output = baos.toString();

        assertFalse(output.contains(testDevice0.toString()));
        assertEquals(sizeBeforeExiting, sizeAfterExiting);
    }
}