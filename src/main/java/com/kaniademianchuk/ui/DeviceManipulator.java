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
        commands.put("status", str -> {
            printDevice();
        });
        commands.put("toggle", str -> {
            this.device.toggle();
            printDevice();
        });
        commands.put("on", str -> {
            this.device.turnOn();
            printDevice();
        });
        commands.put("off", str -> {
            this.device.turnOff();
            printDevice();
        });

        commands.put("dim (\\d+)", (str) -> {
            if (!(this.device instanceof IDimmable)) {
                return;
            }
            Optional<Integer> dimValue = matchFirstInteger(dimPattern, str);
            if (!dimValue.isPresent()) {
                this.format("Invalid value\n");
                return;
            }
            Integer value = dimValue.get();
            try {
                ((IDimmable) this.device).setDimLevel(value);
                printDevice();
            } catch (Exception e) {
                this.errFormat("Could not set dimLevel %d, %s\n", value, e.toString());
            }
        });

    }

    private void printDevice() {
        System.out.println(this.device.toString());
    }

    public void run(String input) {
        Matcher matcher = devicePattern.matcher(input);
        if (!matcher.find()) {
            return;
        }
        Integer id = Integer.parseInt(matcher.group(1));
        Optional<ITogglable> optDevice = deviceManager.getDeviceById(id);
        if (!optDevice.isPresent()) {
            this.format("Device with id %d not found\n", id);
            return;
        }
        this.device = optDevice.get();

        while (!done) {
            System.out.print(this.getPrefix() + "Choose a command: ");
            if (this.device instanceof IDimmable) {
                System.out.print("dim <0-100>, ");
            }
            System.out.print("status, toggle, on, off, exit: ");
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

    @Override
    String getPrefix() {
        return "[Device]";
    }
}
