//package com.kaniademianchuk.model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DeviceTest {
//
//    Device device;
//
//    @BeforeEach
//    void setUp() {
//        device =
//                new Device.Builder()
//                        .setId(1)
//                        .setName("Smart bulb")
//                        .setOn(true)
//                        .build();
//    }
//
//    @Test
//    void getId() {
//        assertEquals(1, device.getId());
//    }
//
//    @Test
//    void getName() {
//        assertEquals("Smart bulb", device.getName());
//    }
//
//    @Test
//    void equals() {
//        assertEquals(device, new Device.Builder().setId(1).setName("Smart bulb").setOn(true).build());
//    }
//
//    @Test
//    void hashCodeTest() {
//        Device testDevice = new Device.Builder().setId(1).setName("Smart bulb").setOn(true).build();
//
//        assertEquals(device.hashCode(), testDevice.hashCode());
//    }
//
//    @Test
//    void toStringTest() {
//        String test = "Device[Id: 1, Name: Smart bulb, isOn: true]";
//
//        assertEquals(test, device.toString());
//    }
//
//    @Test
//    void isOn() {
//        assertEquals(true, device.isOn());
//    }
//}