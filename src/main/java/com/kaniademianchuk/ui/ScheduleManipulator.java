package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.Manager;
import com.kaniademianchuk.model.ScheduledTask;
import com.kaniademianchuk.model.ScheduledTaskType;
import com.kaniademianchuk.model.Scheduler;
import com.kaniademianchuk.util.DateUtil;

import java.text.ParseException;
import java.util.*;

public class ScheduleManipulator extends AbstractManipulator {
    private final Manager<ScheduledTask> taskManager;
    private final Manager<ITogglable> deviceManager;
    private final Scheduler scheduler;
    private Map<String, Runnable> commands = new HashMap<>();
    private boolean done = false;

    public ScheduleManipulator(Scanner reader, Manager<ScheduledTask> taskManager, Manager<ITogglable> deviceManager) {
        super(reader);
        this.taskManager = taskManager;
        this.deviceManager = deviceManager;
        this.scheduler = new Scheduler();
        commands.put("list", () -> {
            System.out.println(this.taskManager.getAllDevices().toString());
        });
        commands.put("exit", () -> {
            ScheduleManipulator.this.done = true;
        });
        commands.put("create", this::addTask);
        commands.put("remove", this::removeTask);
    }

    private void removeTask() {
        System.out.print("TaskId: ");
        Integer id = this.reader.nextInt();
        this.reader.nextLine();

        Optional<ScheduledTask> task = this.taskManager.getDeviceById(id);
        if (!task.isPresent()) {
            this.errFormat("Task with id %d not found\n", id);
            return;
        }
        task.get().cancel();
        this.taskManager.removeDevice(task.get());
    }

    private void addTask() {
        String name = this.promptString("Name: ");
        Date initialDate;
        while (true) {
            String dateString = this.promptString("Time (eg. 2009-12-31 23:59:59): ");
            try {
                initialDate = DateUtil.FORMAT.parse(dateString);
                break;
            } catch (ParseException e) {
                this.format("Invalid date %s\n", e.toString());
            }
        }
        Optional<Integer> interval;
        while (true) {
            System.out.print("Interval, blank if one-time (daily|weekly): ");
            String intervalString = this.reader.nextLine();
            if (intervalString.length() == 0) {
                interval = Optional.empty();
            } else if (intervalString.equals("daily")) {
                interval = Optional.of(DateUtil.DAILY);
            } else if (intervalString.equals("weekly")) {
                interval = Optional.of(DateUtil.WEEKLY);
            } else {
                continue;
            }
            break;
        }
        System.out.print("DeviceId: ");
        Integer id = this.reader.nextInt();
        this.reader.nextLine();
        String task;
        ScheduledTaskType actualTask = null;
        while (actualTask == null) {
            task = promptOneFromMany("Task (toggle|turnOn|turnOff): ", "toggle", "turnOn", "turnOff");
            switch (task) {
                case "toggle":
                    actualTask = ScheduledTaskType.TOGGLE;
                    break;
                case "turnOn":
                    actualTask = ScheduledTaskType.TURN_ON;
                    break;
                case "turnOff":
                    actualTask = ScheduledTaskType.TURN_OFF;
                    break;
            }
        }
        ScheduledTask scheduledTask = new ScheduledTask(name, this.deviceManager, id, actualTask);
        this.scheduler.scheduleTask(initialDate, interval, scheduledTask);
        this.taskManager.addDevice(scheduledTask);
        this.format("Created task with id %d\n", scheduledTask.getId());
    }

    public void run(String input) {
        while (!done) {
            String n = this.promptString("Choose a command: list, create, remove, exit: ");
            for (Map.Entry<String, Runnable> entry : commands.entrySet()) {
                if (n.matches(entry.getKey())) {
                    entry.getValue().run();
                }
            }
        }
    }

    @Override
    String getPrefix() {
        return "[Schedule]";
    }
}
