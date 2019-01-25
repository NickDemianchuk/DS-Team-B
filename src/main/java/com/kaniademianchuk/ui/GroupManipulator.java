package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.DeviceGroup;
import com.kaniademianchuk.model.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupManipulator {
    private static final Pattern p = Pattern.compile("group (\\d+)");
    private final Scanner reader;
    private final Manager<DeviceGroup<ITogglable>> groupManager;
    private Map<String, Runnable> commands = new HashMap<>();
    private DeviceGroup<ITogglable> group;
    private boolean done = false;

    public GroupManipulator(Scanner reader, Manager<DeviceGroup<ITogglable>> groupManager) {
        this.reader = reader;
        this.groupManager = groupManager;
        commands.put("exit", () -> {
            GroupManipulator.this.done = true;
        });
        commands.put("list", () -> {
            System.out.println(this.group.getDevices().toString());
        });
    }


    public void run(String input) {
        Matcher m = p.matcher(input);
        if (!m.find()) {
            return;
        }
        Integer id = Integer.parseInt(m.group(1));
        Optional<DeviceGroup<ITogglable>> optDevice = groupManager.getDeviceById(id);
        if (!optDevice.isPresent()) {
            System.out.format("Device with id %d not found\n", id);
            return;
        }
        this.group = optDevice.get();

        while (!done) {
            System.out.print("Choose a command: list, exit: ");
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
