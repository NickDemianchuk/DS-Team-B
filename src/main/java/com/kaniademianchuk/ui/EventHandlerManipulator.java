package com.kaniademianchuk.ui;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.events.DefaultEventHandler;
import com.kaniademianchuk.events.DefaultEventHandler.Condition;
import com.kaniademianchuk.events.EventBroker;
import com.kaniademianchuk.events.EventHandlerType;
import com.kaniademianchuk.model.Manager;
import com.kaniademianchuk.model.ScheduledTask;
import com.kaniademianchuk.model.ScheduledTaskType;
import com.kaniademianchuk.model.Scheduler;
import com.kaniademianchuk.util.DateUtil;

import java.util.*;
import java.util.regex.Pattern;

public class EventHandlerManipulator extends AbstractManipulator {
    private final Manager<ScheduledTask> taskManager;
    private final Manager<ITogglable> deviceManager;
    private final EventBroker<DefaultEventHandler> eventBroker;
    private final Scheduler scheduler;
    private Map<String, Runnable> commands = new HashMap<>();
    private boolean done = false;

    public EventHandlerManipulator(Scanner reader,
                                   Manager<ScheduledTask> taskManager,
                                   Manager<ITogglable> deviceManager,
                                   EventBroker<DefaultEventHandler> eventBroker) {
        super(reader);
        this.taskManager = taskManager;
        this.deviceManager = deviceManager;
        this.eventBroker = eventBroker;
        this.scheduler = new Scheduler();
        commands.put("list", () -> {
            System.out.println(this.eventBroker.getAllDevices().toString());
        });
        commands.put("exit", () -> {
            EventHandlerManipulator.this.done = true;
        });
        commands.put("create", this::addEventListener);
        commands.put("remove", this::removeEventListener);
    }

    private void removeEventListener() {
        System.out.print("HandlerId: ");
        Integer id = this.reader.nextInt();
        this.reader.nextLine();

        Optional<DefaultEventHandler> task = this.eventBroker.getDeviceById(id);
        if (!task.isPresent()) {
            this.errFormat("Handler with id %d not found\n", id);
            return;
        }
        this.eventBroker.removeDevice(task.get());
    }

    private void addEventListener() {
        String name = this.promptString("Name for handler: ");
        String nameRegex = this.promptString("Name regex for subject (eg. PowerSwitch(\\d+)): ");
        String task;
        EventHandlerType listenOnTask = null;
        while (listenOnTask == null) {
            task = promptOneFromMany("Task to listen on (turnOn|turnOff): ", "turnOn", "turnOff");
            switch (task) {
                case "turnOn":
                    listenOnTask = EventHandlerType.TURN_ON;
                    break;
                case "turnOff":
                    listenOnTask = EventHandlerType.TURN_OFF;
                    break;
            }
        }

        ScheduledTaskType taskToSchedule = null;
        while (taskToSchedule == null) {
            task = promptOneFromMany("Task to perform (toggle|turnOn|turnOff): ", "toggle", "turnOn", "turnOff");
            switch (task) {
                case "toggle":
                    taskToSchedule = ScheduledTaskType.TOGGLE;
                    break;
                case "turnOn":
                    taskToSchedule = ScheduledTaskType.TURN_ON;
                    break;
                case "turnOff":
                    taskToSchedule = ScheduledTaskType.TURN_OFF;
                    break;
            }
        }
        Integer delayInMinutes = 0;
        while (delayInMinutes < 1) {
            System.out.print("Delay in minutes (at least 1): ");
            delayInMinutes = this.reader.nextInt();
        }
        this.reader.nextLine();

        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(this.createNameCondition(nameRegex));
        conditions.add(this.createTaskTypeCondition(listenOnTask));
        DefaultEventHandler.Action action = this.createAction(name, taskToSchedule, delayInMinutes);

        DefaultEventHandler handler = new DefaultEventHandler(name, conditions, action);
        this.eventBroker.addDevice(handler);

        this.format("Created handler with id %d\n", handler.getId());
    }

    private Condition createNameCondition(String nameRegex) {
        return new Condition() {
            private final Pattern namePattern = Pattern.compile(nameRegex);

            @Override
            public boolean evaluate(ITogglable subject, EventHandlerType event) {
                return (this.namePattern.matcher(subject.getName()).find());
            }
        };
    }

    private Condition createTaskTypeCondition(EventHandlerType taskType) {
        return new Condition() {
            @Override
            public boolean evaluate(ITogglable subject, EventHandlerType event) {
                return event == taskType;
            }
        };
    }

    private DefaultEventHandler.Action createAction(String name, ScheduledTaskType taskType, Integer delay) {
        return new DefaultEventHandler.Action() {
            private final Scheduler scheduler = EventHandlerManipulator.this.scheduler;
            private final Manager<ITogglable> deviceManager = EventHandlerManipulator.this.deviceManager;
            private final Manager<ScheduledTask> taskManager = EventHandlerManipulator.this.taskManager;

            @Override
            public void execute(ITogglable subject) {
                Calendar date = Calendar.getInstance();
                long t = date.getTimeInMillis();
                Date expectedDate = new Date(t + (delay * DateUtil.MINUTE_IN_MILLISECONDS));
                ScheduledTask scheduledTask = new ScheduledTask(
                        "Task created by eventHandler " + name,
                        this.deviceManager,
                        subject.getId(),
                        taskType
                );
                this.scheduler.scheduleTask(expectedDate, Optional.empty(), scheduledTask);
                this.taskManager.addDevice(scheduledTask);
            }
        };
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
        return "[EventHandler]";
    }
}
