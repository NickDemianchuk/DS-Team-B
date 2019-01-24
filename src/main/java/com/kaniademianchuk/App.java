package com.kaniademianchuk;

import com.kaniademianchuk.api.SmartDeviceApi;
import com.kaniademianchuk.model.Device;
import com.kaniademianchuk.model.DeviceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class App {

    static SmartDeviceApi deviceManager = new DeviceManager();
    static Map<String, Runnable> commands = new HashMap<>();
    static Scanner reader = new Scanner(System.in);

    static {
        commands.put("list", App::listDevices);
        commands.put("add", App::addDevice);
        commands.put("remove", App::removeDevice);
        commands.put("exit", () -> System.exit(0));
    }

    public static void listDevices() {
        System.out.println(deviceManager.getAllDevices().toString());
    }

    public static void removeDevice() {
        System.out.print("Id: ");
        int id = reader.nextInt();
        boolean success = deviceManager.removeDevice(id);

        if (!success) {
            System.out.println("Could not remove device");
            return;
        }
        System.out.format("Device with id %d removed successfuly\n", id);
        reader.nextLine();
    }

    public static void addDevice() {
        System.out.print("Name: ");
        String name = reader.nextLine();
        System.out.print("isOn: ");
        boolean isOn = reader.nextBoolean();

        Device.Builder builder = new Device.Builder().setName(name).setOn(isOn);
        Optional<Integer> id = deviceManager.addDevice(builder);
        if (!id.isPresent()) {
            System.out.println("Could not add device");
            return;
        }
        System.out.format("Added device successfully, id %d\n", id.get());
        reader.nextLine();
    }

    public static void main(String[] args) {
        System.out.println("Choose a command: list, add, update, remove, exit");
        while (true) {
            String n = reader.nextLine();
            if (n.length() == 0) {
                continue;
            }
            if (commands.containsKey(n)) {
                commands.get(n).run();
            } else {
                System.out.println("Invalid command");
            }
        }
    }
}
