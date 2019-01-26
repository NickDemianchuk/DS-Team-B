package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.IDimmable;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceManipulator extends AbstractManipulator {
    private static final Pattern devicePattern = Pattern.compile("device (\\d+)");
    private static final Pattern dimPattern = Pattern.compile("dim (\\d+)");
    private final Manager<ITogglable> deviceManager;
    private Map<String, Command> commands = new HashMap<>();
    private ITogglable device;
    private boolean done = false;

    public DeviceManipulator(Scanner reader, Manager<ITogglable> deviceManager) {
        super(reader);
        this.deviceManager = deviceManager;
        commands.put("exit", str -> {
            DeviceManipulator.this.done = true;
        });
        commands.put("toggle", str -> {
            this.device.toggle();
            System.out.println(this.device.toString());
        });
        commands.put("on", str -> {
            this.device.turnOn();
            System.out.println(this.device.toString());
        });
        commands.put("off", str -> {
            this.device.turnOff();
            System.out.println(this.device.toString());
        });

        commands.put("dim (\\d+)", (str) -> {
            if (!(this.device instanceof IDimmable)) {
                return;
            }
            Matcher m = dimPattern.matcher(str);
            if (!m.find()) {
                System.err.format("Invalid command\n");
            }
            Integer value = Integer.parseInt(m.group(1));
            try {
                ((IDimmable) this.device).setDimLevel(value);
                System.out.println(this.device.toString());
            } catch (Exception e) {
                System.err.format("Could not set dimLevel %d, %s\n", value, e.toString());
            }
        });

    }

    public void run(String input) {
        Matcher matcher = devicePattern.matcher(input);
        if (!matcher.find()) {
            return;
        }
        Integer id = Integer.parseInt(matcher.group(1));
        Optional<ITogglable> optDevice = deviceManager.getDeviceById(id);
        if (!optDevice.isPresent()) {
            System.out.format("Device with id %d not found\n", id);
            return;
        }
        this.device = optDevice.get();

        while (!done) {
            System.out.print("Choose a command: ");
            if (this.device instanceof IDimmable) {
                System.out.print("dim <0-100>, ");
            }
            System.out.print("toggle, on, off, exit: ");
            String lineInput = reader.nextLine();
            if (lineInput.length() == 0) {
                continue;
            }
            for (Map.Entry<String, Command> entry : commands.entrySet()) {
                if (lineInput.matches(entry.getKey())) {
                    entry.getValue().run(lineInput);
                }
            }
        }
    }
}
