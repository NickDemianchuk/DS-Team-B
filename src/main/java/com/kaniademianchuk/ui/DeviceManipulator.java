package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceManipulator {
    private static final Pattern p = Pattern.compile("item (\\d+)");
    private final Scanner reader;
    private final Manager<ITogglable> deviceManager;
    private Map<String, Runnable> commands = new HashMap<>();
    private ITogglable device;
    private boolean done = false;

    public DeviceManipulator(Scanner reader, Manager<ITogglable> deviceManager) {
        this.reader = reader;
        this.deviceManager = deviceManager;
        commands.put("exit", () -> {
            DeviceManipulator.this.done = true;
        });
        commands.put("toggle", () -> {
            this.device = this.device.toggle();
            this.deviceManager.updateDevice(this.device);
            System.out.println(this.device.toString());
        });
        commands.put("on", () -> {
            this.device = this.device.turnOn();
            this.deviceManager.updateDevice(this.device);
            System.out.println(this.device.toString());
        });
        commands.put("off", () -> {
            this.device = this.device.turnOff();
            this.deviceManager.updateDevice(this.device);
            System.out.println(this.device.toString());
        });
    }


    public void run(String input) {
        Matcher m = p.matcher(input);
        if (!m.find()) {
            return;
        }
        Integer id = Integer.parseInt(m.group(1));
        Optional<ITogglable> optDevice = deviceManager.getDeviceById(id);
        if (!optDevice.isPresent()) {
            System.out.format("Device with id %d not found\n", id);
            return;
        }
        this.device = optDevice.get();

        while (!done) {
            System.out.print("Choose a command: toggle, on, off, exit: ");
            String n = reader.nextLine();
            if (n.length() == 0) {
                continue;
            }
            for (Map.Entry<String, Runnable> entry : commands.entrySet()) {
                if (n.matches(entry.getKey())) {
                    entry.getValue().run();
                }
            }
        }
    }


}
