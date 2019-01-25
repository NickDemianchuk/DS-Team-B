package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IIdentifiable;
import com.kaniademianchuk.api.ITogglable;

import java.util.Optional;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask implements IIdentifiable {
    private static Integer latestId = 0;
    private final Manager<ITogglable> source;
    private final Integer subjectId;
    private final ScheduledTaskType task;
    private Integer id;
    private String name;

    public ScheduledTask(Manager<ITogglable> source, int subjectId, ScheduledTaskType task) {
        this.source = source;
        this.subjectId = subjectId;
        this.task = task;
        this.id = latestId++;
        this.name = String.format("Task %d", this.id);
    }

    @Override
    public void run() {
        Optional<ITogglable> device = this.source.getDeviceById(id);
        if (!device.isPresent()) {
            return;
        }
        if (task.equals(ScheduledTaskType.TOGGLE)) {
            device.get().toggle();
        } else if (task.equals(ScheduledTaskType.TURN_ON)) {
            device.get().turnOn();
        } else if (task.equals(ScheduledTaskType.TURN_OFF)) {
            device.get().turnOff();
        } else {
            System.err.format("Unknown task '%s' on device %d in taskId:%d\n", task, subjectId, id);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
