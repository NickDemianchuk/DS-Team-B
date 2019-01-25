package com.kaniademianchuk.ui;

import com.kaniademianchuk.model.Manager;
import com.kaniademianchuk.model.ScheduledTask;
import com.kaniademianchuk.model.Scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScheduleManipulator {
    private final Scanner reader;
    private final Manager<ScheduledTask> taskManager;
    private final Scheduler scheduler;
    private Map<String, Runnable> commands = new HashMap<>();
    private boolean done = false;

    public ScheduleManipulator(Scanner reader, Manager<ScheduledTask> taskManager) {
        this.reader = reader;
        this.taskManager = taskManager;
        this.scheduler = new Scheduler();
        commands.put("test", () -> {
            this.scheduler.scheduleTask(new Date(), 1000, () -> System.out.println("Yes"));
        });
    }

    public void run(String input) {
        while (!done) {
            System.out.print("Choose a command: toggle, on, off, exit: ");
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
