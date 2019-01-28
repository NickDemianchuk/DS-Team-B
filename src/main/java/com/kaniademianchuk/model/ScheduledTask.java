package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IIdentifiable;
import com.kaniademianchuk.api.ITogglable;

import java.util.Objects;
import java.util.Optional;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask implements IIdentifiable {
    private static Integer latestId = 0;
    private final Manager<ITogglable> source;
    private final Integer subjectId;
    private final ScheduledTaskType task;
    private Integer id;
    private String name;

    public ScheduledTask(String name, Manager<ITogglable> source, int subjectId, ScheduledTaskType task) {
        this.name = name;
        this.source = source;
        this.subjectId = subjectId;
        this.task = task;
        this.id = latestId++;
    }

    @Override
    public String toString() {
        return "ScheduledTask{" +
                "taskId=" + id +
                ", subjectId=" + subjectId +
                ", task=" + task +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTask that = (ScheduledTask) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(subjectId, that.subjectId) &&
                task == that.task &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, subjectId, task, id, name);
    }

    @Override
    public void run() {
        Optional<ITogglable> device = this.source.getDeviceById(subjectId);
        if (!device.isPresent()) {
            return;
        }
        switch (this.task) {
            case TOGGLE:
                device.get().toggle();
                break;
            case TURN_ON:
                device.get().turnOn();
                break;
            case TURN_OFF:
                device.get().turnOff();
                break;
            default:
                System.err.format("Unknown task '%s' on device %d in taskId:%d\n", task, subjectId, id);
                break;
        }
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected static Integer testGetLatestId() {
        return ScheduledTask.latestId - 1;
    }
}
