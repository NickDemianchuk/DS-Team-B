package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.Manager;
import com.kaniademianchuk.model.TogglableGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupManipulator extends AbstractManipulator {
    private static final Pattern groupPattern = Pattern.compile("group (\\d+)");
    private static final Pattern addDevicePattern = Pattern.compile("addDevice (\\d+)");
    private static final Pattern removeDevicePattern = Pattern.compile("removeDevice (\\d+)");
    private final Manager<TogglableGroup<ITogglable>> groupManager;
    private final Manager<ITogglable> deviceManager;
    private Map<String, Command> commands = new HashMap<>();
    private TogglableGroup<ITogglable> group;
    private boolean done = false;

    public GroupManipulator(Scanner reader, Manager<TogglableGroup<ITogglable>> groupManager, Manager<ITogglable> deviceManager) {
        super(reader);
        this.groupManager = groupManager;
        this.deviceManager = deviceManager;
        commands.put("exit", str -> {
            GroupManipulator.this.done = true;
        });
        commands.put("list", str -> {
            printDevicesInGroup();
        });
        commands.put("toggle", str -> {
            this.group.toggle();
            printDevicesInGroup();
        });
        commands.put("addDevice (\\d+)", str -> {
            Optional<Integer> match = matchFirstInteger(addDevicePattern, str);
            if (!match.isPresent()) {
                return;
            }
            Integer id = match.get();
            Optional<ITogglable> device = GroupManipulator.this.deviceManager.getDeviceById(id);
            if (!device.isPresent()) {
                System.out.format("Device with id %d not found\n", id);
                return;
            }
            if (GroupManipulator.this.group.getDeviceById(id) != null) {
                System.err.format("Group contains device with id %d already\n", id);
                return;
            }
            GroupManipulator.this.group.addDevice(device.get());
            printDevicesInGroup();
        });
        commands.put("removeDevice (\\d+)", str -> {
            Optional<Integer> match = matchFirstInteger(removeDevicePattern, str);
            if (!match.isPresent()) {
                return;
            }
            Integer id = match.get();
            boolean success = GroupManipulator.this.group.removeDevice(id);
            if (!success) {
                System.err.format("Device with id %d not found in group\n", id);
            }
            printDevicesInGroup();
        });
    }

    private void printDevicesInGroup() {
        System.out.println(this.group.getDevices().toString());
    }


    public void run(String input) {
        Matcher m = groupPattern.matcher(input);
        if (!m.find()) {
            return;
        }
        Integer id = Integer.parseInt(m.group(1));
        Optional<TogglableGroup<ITogglable>> optDevice = groupManager.getDeviceById(id);
        if (!optDevice.isPresent()) {
            System.out.format("Device with id %d not found\n", id);
            return;
        }
        this.group = optDevice.get();

        while (!done) {
            System.out.print("Choose a command: addDevice <id>, removeDevice <id>, list, toggle, exit: ");
            String n = reader.nextLine();
            if (n.length() == 0) {
                continue;
            }
            for (Map.Entry<String, Command> entry : commands.entrySet()) {
                if (n.matches(entry.getKey())) {
                    entry.getValue().run(n);
                }
            }
        }
    }
}