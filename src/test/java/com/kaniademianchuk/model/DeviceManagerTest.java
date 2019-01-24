//package com.kaniademianchuk.model;
//
//import com.kaniademianchuk.api.ISmartDeviceApi;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DeviceManagerTest {
//
//    ISmartDeviceApi deviceManager;
//
//    @BeforeEach
//    void setUp() {
//        deviceManager = new DeviceManager();
//    }
//
//    @Test
//    void addDevice() {
//        Device.Builder builder =
//                new Device.Builder()
//                        .setId(1)
//                        .setName("smart bulb")
//                        .setOn(true);
//
//        Optional<Integer> id = deviceManager.addDevice(builder);
//
//        assertEquals(builder.build(), deviceManager.getDeviceById(id.get()).get());
//    }
//
//    @Test
//    void updateDevice() {
//        Device.Builder oldBuilder =
//                new Device.Builder()
//                        .setId(1)
//                        .setName("old bulb")
//                        .setOn(false);
//        Device.Builder newBuilder =
//                new Device.Builder()
//                        .setName("brand new bulb")
//                        .setOn(true);
//        deviceManager.addDevice(oldBuilder);
//
//        boolean isUpdated = deviceManager.updateDevice(oldBuilder.getId().get(), newBuilder);
//
//        assertEquals(true, isUpdated);
//    }
//
//    @Test
//    void removeDevice() {
//        Device.Builder builder =
//                new Device.Builder()
//                        .setId(1)
//                        .setName("smart bulb")
//                        .setOn(true);
//        deviceManager.addDevice(builder);
//
//        boolean isRemoved = deviceManager.removeDevice(builder.getId().get());
//
//        assertEquals(true, isRemoved);
//        assertEquals(0, deviceManager.getAllDevices().size());
//    }
//
//    @Test
//    void getAllDevices() {
//        Device.Builder builder =
//                new Device.Builder()
//                        .setName("smart bulb")
//                        .setOn(true);
//
//        for (int i = 0; i < 5; i++) {
//            deviceManager.addDevice(builder);
//        }
//
//        assertEquals(5, deviceManager.getAllDevices().size());
//    }
//
//    @Test
//    void getDeviceById() {
//        Device.Builder builder =
//                new Device.Builder()
//                        .setName("smart bulb")
//                        .setOn(true);
//
//        int id = deviceManager.addDevice(builder).get();
//
//        assertEquals(builder.build(), deviceManager.getDeviceById(id).get());
//    }
//}