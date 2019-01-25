package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.Manager;
import com.kaniademianchuk.model.ScheduledTask;
import com.kaniademianchuk.model.ScheduledTaskType;
import com.kaniademianchuk.model.Scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScheduleManipulator {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static int DAILY = 1000 * 60 * 60 * 24;
    private static int WEEKLY = DAILY * 7;
    private final Scanner reader;
    private final Manager<ScheduledTask> taskManager;
    private final Manager<ITogglable> deviceManager;
    private final Scheduler scheduler;
    private Map<String, Runnable> commands = new HashMap<>();
    private boolean done = false;

    public ScheduleManipulator(Scanner reader, Manager<ScheduledTask> taskManager, Manager<ITogglable> deviceManager) {
        this.reader = reader;
        this.taskManager = taskManager;
        this.deviceManager = deviceManager;
        this.scheduler = new Scheduler();
        commands.put("list", () -> {
            System.out.println(this.taskManager.getAllDevices().toString());
        });
        commands.put("exit", () -> {
            ScheduleManipulator.this.done = true;
        });
        commands.put("add", this::addTask);
        commands.put("remove", this::removeTask);
    }

    private void removeTask() {
        System.out.print("TaskId: ");
        Integer id = this.reader.nextInt();
        this.reader.nextLine();

        Optional<ScheduledTask> task = this.taskManager.getDeviceById(id);
        if (!task.isPresent()) {
            System.err.format("Task with id %d not found\n", id);
            return;
        }
        task.get().cancel();
        this.taskManager.removeDevice(task.get());
    }

    private void addTask() {
        System.out.print("Name: ");
        String name = this.reader.nextLine();
        Date initialDate;
        while (true) {
            System.out.print("Time (eg. 2009-12-31 23:59:59): ");
            String dateString = this.reader.nextLine();
            try {
                initialDate = ScheduleManipulator.format.parse(dateString);
                break;
            } catch (ParseException e) {
                System.out.format("Invalid date %s\n", e.toString());
            }
        }
        Optional<Integer> interval;
        while (true) {
            System.out.print("Interval, blank if one-time (daily|weekly): ");
            String intervalString = this.reader.nextLine();
            if (intervalString.length() == 0) {
                interval = Optional.empty();
            } else if (intervalString.equals("daily")) {
                interval = Optional.of(DAILY);
            } else if (intervalString.equals("weekly")) {
                interval = Optional.of(WEEKLY);
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
            System.out.print("Task (toggle/turnOn/turnOff): ");
            task = this.reader.nextLine();
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
        ScheduledTask scheduledTask = new ScheduledTask(this.deviceManager, id, actualTask);
        this.scheduler.scheduleTask(initialDate, interval, scheduledTask);
        this.taskManager.addDevice(scheduledTask);
        System.out.format("Created task with id %d\n", scheduledTask.getId());
    }


    public void run(String input) {
        while (!done) {
            System.out.print("Choose a command: list, add, remove, exit: ");
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
