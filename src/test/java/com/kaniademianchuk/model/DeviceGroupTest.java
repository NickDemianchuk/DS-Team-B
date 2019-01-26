package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IIdentifiable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DeviceGroupTest {

    IIdentifiable togglable;
    IIdentifiable dimmable;
    DeviceGroup<IIdentifiable> deviceGroup;

    @BeforeEach
    void setUp() {
        togglable = new DefaultTogglable("smart switch", false);
        dimmable = new DefaultDimmable("smart bulb", 50);
        deviceGroup = new DeviceGroup<>("smart devices", togglable, dimmable);
    }

    @Test
    void getDevices() {
        Collection<IIdentifiable> devices = deviceGroup.getDevices();

        for (IIdentifiable device : devices) {
            assertNotNull(device);
        }
    }

    @Test
    void addUnexistingDevice() {
        IIdentifiable newDimmable = new DefaultDimmable("new smart bulb", 90);

        int sizeBeforeAdding = deviceGroup.getSize();
        IIdentifiable expectedResponse = deviceGroup.addDevice(newDimmable);
        int sizeAfterAdding = deviceGroup.getSize();

        assertNull(expectedResponse);
        assertNotEquals(sizeBeforeAdding, sizeAfterAdding);
        assertEquals(sizeBeforeAdding + 1, sizeAfterAdding);
    }

    @Test
    void addExistingDevice() {
        int sizeBeforeAdding = deviceGroup.getSize();
        IIdentifiable expectedResponse = deviceGroup.addDevice(dimmable);
        int sizeAfterAdding = deviceGroup.getSize();

        assertNotNull(expectedResponse);
        assertEquals(sizeBeforeAdding, sizeAfterAdding);
        assertEquals(expectedResponse, dimmable);
    }

    @Test
    void removeUnexistingDevice() {
        IIdentifiable newDimmable = new DefaultDimmable("new smart bulb", 100);

        int sizeBeforeRemoving = deviceGroup.getSize();
        boolean expectedResponse = deviceGroup.removeDevice(newDimmable);
        int sizeAfterRemoving = deviceGroup.getSize();

        assertFalse(expectedResponse);
        assertEquals(sizeBeforeRemoving, sizeAfterRemoving);
    }

    @Test
    void removeExistingDevice() {
        int sizeBeforeRemoving = deviceGroup.getSize();
        boolean expectedResponse = deviceGroup.removeDevice(dimmable);
        int sizeAfterRemoving = deviceGroup.getSize();

        assertTrue(expectedResponse);
        assertEquals(expectedResponse, dimmable);
        assertNotEquals(sizeBeforeRemoving, sizeAfterRemoving);
        assertEquals(sizeBeforeRemoving - 1, sizeAfterRemoving);
    }

    @Test
    void getDeviceById() {
        Integer expectedId = dimmable.getId();
        IIdentifiable expectedDevice = deviceGroup.getDeviceById(expectedId);

        assertEquals(expectedDevice, dimmable);
    }

    @Test
    void getSize() {
        int sizeBeforeAdding = deviceGroup.getSize();
        IIdentifiable newTogglable = new DefaultTogglable("new smart bulb", true);
        deviceGroup.addDevice(newTogglable);
        int sizeAfterAdding = deviceGroup.getSize();

        assertEquals(2, sizeBeforeAdding);
        assertEquals(3, sizeAfterAdding);
    }

    @Test
    void toStringTest() {
        String deviceGroupToString = "DeviceGroup{id='2', name='smart devices', size=2}";

        assertEquals(deviceGroupToString, deviceGroup.toString());
    }
}