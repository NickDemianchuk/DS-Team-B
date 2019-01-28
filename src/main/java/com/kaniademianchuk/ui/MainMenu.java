package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.IDimmable;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.events.DefaultEventHandler;
import com.kaniademianchuk.events.EventBroker;
import com.kaniademianchuk.model.*;
import com.kaniademianchuk.util.PatternUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu extends AbstractManipulator {
    private Manager<TogglableGroup<ITogglable>> groupManager = new Manager<>();
    private Manager<ITogglable> deviceManager = new Manager<>();
    private Manager<ScheduledTask> taskManager = new Manager<>();
    private Map<String, Command> commands = new HashMap<>();
    private EventBroker<DefaultEventHandler> eventBroker = new EventBroker<>();

    public MainMenu(Scanner reader) {
        super(reader);

        commands.put("exit", str -> System.exit(0));
        commands.put("listGroups", str -> this.listManager(groupManager));
        commands.put("listDevices", str -> this.listManager(deviceManager));
        commands.put("device (\\d+)", str -> {
            new DeviceManipulator(this.reader, deviceManager).run(str);
        });
        commands.put("group (\\d+)", str -> {
            new GroupManipulator(this.reader, groupManager, deviceManager).run(str);
        });
        commands.put("schedule", str -> new ScheduleManipulator(this.reader, taskManager, deviceManager).run(str));
        commands.put("eventHandlers", str -> new EventHandlerManipulator(this.reader, this.taskManager, this.deviceManager, this.eventBroker).run(str));
        commands.put("createDevice", str -> {
            ITogglable device = this.addDeviceDialog();
            this.deviceManager.addDevice(device);
            this.format("Added device with id %d\n", device.getId());
        });
        commands.put(PatternUtil.REMOVE_DEVICE, str -> {
            Optional<Integer> match = matchFirstInteger(PatternUtil.REMOVE_DEVICE_PATTERN, str);
            if (!match.isPresent()) {
                return;
            }
            Integer id = match.get();
            boolean success = MainMenu.this.deviceManager.removeDevice(id);
            if (!success) {
                this.errFormat("Device with id %d not found\n", id);
                return;
            }
            for (TogglableGroup<ITogglable> group : MainMenu.this.groupManager.getAllDevices()) {
                group.removeDevice(id);
            }
            this.format("Device with id %d deleted\n", id);
        });
        commands.put("createGroup", str -> {
            TogglableGroup<ITogglable> newGroup = this.addGroupDialog();
            this.groupManager.addDevice(newGroup);
            this.format("Added group with id %d\n", newGroup.getId());
        });
        commands.put(PatternUtil.REMOVE_GROUP, str -> {
            Optional<Integer> match = matchFirstInteger(PatternUtil.REMOVE_GROUP_PATTERN, str);
            if (!match.isPresent()) {
                return;
            }
            Integer id = match.get();
            boolean success = MainMenu.this.groupManager.removeDevice(id);
            if (!success) {
                this.errFormat("Group with id %d not found\n", id);
                return;
            }
            this.format("Group with id %d deleted\n", id);
        });
    }

    @Override
    String getPrefix() {
        return "";
    }

    public void listManager(Manager manager) {
        System.out.println(manager.getAllDevices().toString());
    }

    public void initialize() {
        DefaultTogglable device1 = new DefaultTogglable("Outlet1", false, eventBroker);
        DefaultTogglable device2 = new DefaultTogglable("Outlet2", false, eventBroker);
        DefaultTogglable device3 = new DefaultTogglable("Outlet3", false, eventBroker);

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
            return new DefaultTogglable(toggleName, isOn, eventBroker);
        } else {
            return new DefaultDimmable(toggleName, isOn ? IDimmable.MAX_DIM_LEVEL : IDimmable.MIN_DIM_LEVEL, eventBroker);
        }
    }

    private TogglableGroup<ITogglable> addGroupDialog() {
        String toggleName = promptString("Group name: ");
        return new TogglableGroup<ITogglable>(toggleName, new HashMap<>());
    }


    public void run() {
        this.initialize();
        while (true) {
            String n = this.promptString("Choose a command: createGroup, createDevice, removeGroup, removeDevice, eventHandlers, listGroups, listDevices, device <id>, group <id>, schedule, exit: ");
            for (Map.Entry<String, Command> entry : commands.entrySet()) {
                if (n.matches(entry.getKey())) {
                    entry.getValue().run(n);
                }
            }
        }
    }
}
