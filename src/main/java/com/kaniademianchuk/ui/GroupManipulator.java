package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.Manager;
import com.kaniademianchuk.model.TogglableGroup;
import com.kaniademianchuk.util.PatternUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GroupManipulator extends AbstractManipulator {
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
        commands.put(PatternUtil.ADD_DEVICE, str -> {
            Optional<Integer> match = matchFirstInteger(PatternUtil.ADD_DEVICE_PATTERN, str);
            if (!match.isPresent()) {
                return;
            }
            Integer id = match.get();
            Optional<ITogglable> device = GroupManipulator.this.deviceManager.getDeviceById(id);
            if (!device.isPresent()) {
                this.errFormat("Device with id %d not found\n", id);
                return;
            }
            if (GroupManipulator.this.group.getDeviceById(id) != null) {
                this.errFormat("Group contains device with id %d already\n", id);
                return;
            }
            GroupManipulator.this.group.addDevice(device.get());
            printDevicesInGroup();
        });
        commands.put(PatternUtil.REMOVE_DEVICE, str -> {
            Optional<Integer> match = matchFirstInteger(PatternUtil.REMOVE_DEVICE_PATTERN, str);
            if (!match.isPresent()) {
                return;
            }
            Integer id = match.get();
            boolean success = GroupManipulator.this.group.removeDevice(id);
            if (!success) {
                this.errFormat("Device with id %d not found in group\n", id);
            }
            printDevicesInGroup();
        });
    }

    private void printDevicesInGroup() {
        System.out.println(this.group.getDevices().toString());
    }


    public void run(String input) {
        Matcher m = PatternUtil.GROUP_PATTERN.matcher(input);
        if (!m.find()) {
            return;
        }
        Integer id = Integer.parseInt(m.group(1));
        Optional<TogglableGroup<ITogglable>> optDevice = groupManager.getDeviceById(id);
        if (!optDevice.isPresent()) {
            this.format("Device with id %d not found\n", id);
            return;
        }
        this.group = optDevice.get();

        while (!done) {
            String n = promptString("Choose a command: addDevice <id>, removeDevice <id>, list, toggle, exit: ");
            for (Map.Entry<String, Command> entry : commands.entrySet()) {
                if (n.matches(entry.getKey())) {
                    entry.getValue().run(n);
                }
            }
        }
    }

    @Override
    String getPrefix() {
        return "[Group]";
    }
}