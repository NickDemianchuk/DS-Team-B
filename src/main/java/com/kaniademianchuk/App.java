package com.kaniademianchuk;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.*;
import com.kaniademianchuk.ui.DeviceManipulator;
import com.kaniademianchuk.ui.GroupManipulator;
import com.kaniademianchuk.ui.ScheduleManipulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {


    static Manager<TogglableGroup<ITogglable>> groupManager = new Manager<TogglableGroup<ITogglable>>();
    static Manager<ITogglable> deviceManager = new Manager<ITogglable>();
    static Manager<ScheduledTask> taskManager = new Manager<ScheduledTask>();
    static Map<String, Command> commands = new HashMap<>();
    static Scanner reader = new Scanner(System.in);

    static {
        commands.put("exit", (str) -> System.exit(0));
        commands.put("listGroups", (str) -> App.listManager(groupManager));
        commands.put("listDevices", (str) -> App.listManager(deviceManager));
        commands.put("item (\\d+)", (str) -> new DeviceManipulator(App.reader, deviceManager).run(str));
        commands.put("group (\\d+)", (str) -> new GroupManipulator(App.reader, groupManager).run(str));
        commands.put("schedule", (str) -> new ScheduleManipulator(App.reader, taskManager).run(str));
    }

    public static void listManager(Manager manager) {
        System.out.println(manager.getAllDevices().toString());
    }

    public static void initialize() {
        DefaultTogglable device1 = new DefaultTogglable("Outlet1", false);
        DefaultTogglable device2 = new DefaultTogglable("Outlet2", false);
        DefaultTogglable device3 = new DefaultTogglable("Outlet3", false);

        App.groupManager.addDevice(new TogglableGroup<ITogglable>("PowerSwitch1",
                device1,
                device2,
                device3
        ));

        App.deviceManager.addDevice(device1);
        App.deviceManager.addDevice(device2);
        App.deviceManager.addDevice(device3);

    }

    public static void main(String[] args) {
        App.initialize();
        while (true) {
            System.out.print("Choose a command: listGroups, listDevices, item <id>, group <id>, exit: ");
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

    interface Command {
        void run(String command);
    }
}
