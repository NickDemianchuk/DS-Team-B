package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IIdentifiable;
import com.kaniademianchuk.util.MockEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    Manager<IIdentifiable> manager;
    IIdentifiable togglable;
    IIdentifiable dimmable;
    IIdentifiable deviceGroup;

    @BeforeEach
    void setUp(){
        manager = new Manager<>();
        togglable = new DefaultTogglable("smart switch", true, new MockEventHandler());
        dimmable = new DefaultDimmable("smart bulb", 50, new MockEventHandler());
        deviceGroup = new DeviceGroup<>("device group", togglable, dimmable);
    }

    @Test
    void addDevice() {
        int firstId = togglable.getId();
        int secondId = dimmable.getId();
        Optional<IIdentifiable> firstExpected = manager.addDevice(togglable);
        Optional<IIdentifiable> secondExpected = manager.addDevice(dimmable);

        assertNotEquals(firstExpected, Optional.empty());
        assertNotEquals(secondExpected, Optional.empty());
        assertEquals(2, manager.getAllDevices().size());
        assertEquals(togglable, manager.getDeviceById(firstId).get());
        assertEquals(dimmable, manager.getDeviceById(secondId).get());

    }

    @Test
    void addDeviceGroup() {
        int groupId = deviceGroup.getId();
        Optional<IIdentifiable> expectedGroup = manager.addDevice(deviceGroup);

        assertNotEquals(expectedGroup, Optional.empty());
        assertEquals(expectedGroup.get(), deviceGroup);
        assertEquals(deviceGroup, manager.getDeviceById(groupId).get());
    }

    @Test
    void removeDevice() {
        IIdentifiable deviceToBeRemoved = togglable;
        manager.addDevice(deviceToBeRemoved);
        manager.addDevice(dimmable);

        int sizeBeforeRemoval = manager.getAllDevices().size();
        boolean removed = manager.removeDevice(deviceToBeRemoved);
        int sizeAfterRemoval = manager.getAllDevices().size();

        assertTrue(removed);
        assertNotEquals(sizeBeforeRemoval, sizeAfterRemoval);
        assertFalse(manager.getAllDevices().contains(deviceToBeRemoved));
    }

    @Test
    void removeDeviceGroup() {
        IIdentifiable groupToBeRemoved = togglable;
        manager.addDevice(groupToBeRemoved);

        int sizeBeforeRemoval = manager.getAllDevices().size();
        boolean removed = manager.removeDevice(groupToBeRemoved);
        int sizeAfterRemoval = manager.getAllDevices().size();

        assertTrue(removed);
        assertNotEquals(sizeBeforeRemoval, sizeAfterRemoval);
        assertFalse(manager.getAllDevices().contains(groupToBeRemoved));
    }

    @Test
    void removeDeviceById() {
        IIdentifiable deviceToBeRemoved = togglable;
        manager.addDevice(deviceToBeRemoved);
        manager.addDevice(dimmable);

        int idToBeRemoved = deviceToBeRemoved.getId();
        int sizeBeforeRemoval = manager.getAllDevices().size();
        boolean removed = manager.removeDevice(idToBeRemoved);
        int sizeAfterRemoval = manager.getAllDevices().size();

        assertTrue(removed);
        assertNotEquals(sizeBeforeRemoval, sizeAfterRemoval);
        assertFalse(manager.getAllDevices().contains(deviceToBeRemoved));
    }

    @Test
    void removeDeviceGroupById() {
        IIdentifiable groupToBeRemoved = deviceGroup;
        manager.addDevice(groupToBeRemoved);

        int idToBeRemoved = groupToBeRemoved.getId();
        int sizeBeforeRemoval = manager.getAllDevices().size();
        boolean removed = manager.removeDevice(idToBeRemoved);
        int sizeAfterRemoval = manager.getAllDevices().size();

        assertTrue(removed);
        assertNotEquals(sizeBeforeRemoval, sizeAfterRemoval);
        assertFalse(manager.getAllDevices().contains(groupToBeRemoved));
    }

    @Test
    void getDeviceById() {
        IIdentifiable deviceToBeRetrieved = togglable;
        int idToBeRetrieved = deviceToBeRetrieved.getId();
        manager.addDevice(deviceToBeRetrieved);

        IIdentifiable expectedDevice = manager.getDeviceById(idToBeRetrieved).get();

        assertNotNull(expectedDevice);
        assertEquals(expectedDevice, deviceToBeRetrieved);
    }

    @Test
    void getDeviceGroupById() {
        IIdentifiable groupToBeRetrieved = deviceGroup;
        int groupIdToBeRetrieved = groupToBeRetrieved.getId();
        manager.addDevice(groupToBeRetrieved);

        IIdentifiable expectedGroup = manager.getDeviceById(groupIdToBeRetrieved).get();

        assertNotNull(expectedGroup);
        assertEquals(expectedGroup, groupToBeRetrieved);
    }

    @Test
    void getAllDevices() {
        manager.addDevice(togglable);
        manager.addDevice(dimmable);

        Collection<IIdentifiable> expectedList = Arrays.asList(togglable, dimmable);
        Collection<IIdentifiable> retrievedList = manager.getAllDevices();

        assertFalse(retrievedList.isEmpty());
        assertEquals(2, retrievedList.size());
        assertTrue(retrievedList.containsAll(expectedList));
    }
}