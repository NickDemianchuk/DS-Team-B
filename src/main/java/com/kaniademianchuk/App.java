package com.kaniademianchuk;

import com.kaniademianchuk.api.ISmartDeviceApi;
import com.kaniademianchuk.model.DeviceManager;
import com.kaniademianchuk.model.SmartDevice;

import java.util.*;

public class App {

    static ISmartDeviceApi deviceManager = new DeviceManager();
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
        System.out.format("Device with id %d removed successfully\n", id);
        reader.nextLine();
    }

    public static void addDevice() {
        System.out.print("Name: ");
        String name = reader.nextLine();
        System.out.print("isOn: ");
        boolean isOn = reader.nextBoolean();

        SmartDevice<Object> device = new SmartDevice<>(null, name, new ArrayList<>());
        Optional<Integer> id = deviceManager.addDevice(device);
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
