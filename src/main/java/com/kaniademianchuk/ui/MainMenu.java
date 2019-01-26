package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.IDimmable;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainMenu extends AbstractManipulator {
    private Manager<TogglableGroup<ITogglable>> groupManager = new Manager<TogglableGroup<ITogglable>>();
    private Manager<ITogglable> deviceManager = new Manager<ITogglable>();
    private Manager<ScheduledTask> taskManager = new Manager<ScheduledTask>();
    private Map<String, Command> commands = new HashMap<>();

    public MainMenu(Scanner reader) {
        super(reader);

        commands.put("exit", str -> System.exit(0));
        commands.put("listGroups", str -> this.listManager(groupManager));
        commands.put("listDevices", str -> this.listManager(deviceManager));
        commands.put("device (\\d+)", str -> new DeviceManipulator(this.reader, deviceManager).run(str));
        commands.put("group (\\d+)", str -> new GroupManipulator(this.reader, groupManager).run(str));
        commands.put("schedule", str -> new ScheduleManipulator(this.reader, taskManager, deviceManager).run(str));
        commands.put("createDevice", str -> {
            ITogglable device = this.addDeviceDialog();
            this.deviceManager.addDevice(device);
            System.out.format("Added device with id %d\n", device.getId());
        });
        commands.put("createGroup", str -> {
            TogglableGroup<ITogglable> newGroup = this.addGroupDialog();
            this.groupManager.addDevice(newGroup);
            System.out.format("Added group with id %d\n", newGroup.getId());
        });
    }

    public void listManager(Manager manager) {
        System.out.println(manager.getAllDevices().toString());
    }

    public void initialize() {
        DefaultTogglable device1 = new DefaultTogglable("Outlet1", false);
        DefaultTogglable device2 = new DefaultTogglable("Outlet2", false);
        DefaultTogglable device3 = new DefaultTogglable("Outlet3", false);

        this.groupManager.addDevice(new TogglableGroup<ITogglable>("PowerSwitch1",
                device1,
                device2,
                device3
        ));

        this.deviceManager.addDevice(device1);
        this.deviceManager.addDevice(device2);
        this.deviceManager.addDevice(device3);
    }

    private ITogglable addDeviceDialog() {
        String toggleName = promptString("Device name: ");
        String type = promptOneFromMany("Type (dimmable|toggle): ", "toggle", "dimmable");
        System.out.print("isOn: ");
        boolean isOn = reader.nextBoolean();
        reader.nextLine();
        if (type.equals("toggle")) {
            return new DefaultTogglable(toggleName, isOn);
        } else {
            return new DefaultDimmable(toggleName, isOn ? IDimmable.MAX_DIM_LEVEL : IDimmable.MIN_DIM_LEVEL);
        }
    }

    private TogglableGroup<ITogglable> addGroupDialog() {
        String toggleName = promptString("Group name: ");
        return new TogglableGroup<ITogglable>(toggleName, new HashMap<>());
    }


    public void run() {
        this.initialize();
        while (true) {
            String n = this.promptString("hoose a command: createGroup, createDevice, listGroups, listDevices, device <id>, group <id>, schedule, exit: ");
            for (Map.Entry<String, Command> entry : commands.entrySet()) {
                if (n.matches(entry.getKey())) {
                    entry.getValue().run(n);
                }
            }
        }
    }
}
