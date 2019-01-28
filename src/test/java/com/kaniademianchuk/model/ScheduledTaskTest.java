package com.kaniademianchuk.model;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.util.MockEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduledTaskTest {

    private ITogglable device0;
    private Manager<ITogglable> manager;
    private ScheduledTaskType taskType;
    private ScheduledTask scheduledTask;

    @BeforeEach
    void setUp() {
        device0 = new DefaultTogglable("device", true, new MockEventHandler());
        manager = new Manager<>();
        manager.addDevice(device0);
        taskType = ScheduledTaskType.TOGGLE;
        scheduledTask = new ScheduledTask("toggle", manager, device0.getId(), taskType);
    }

    @Test
    void toStringTest() {
        int taskId = scheduledTask.getId();
        int subjectId = device0.getId();
        String expected = "ScheduledTask{taskId=" + taskId +
                ", subjectId=" + subjectId +
                ", task=TOGGLE, name='toggle'}";

        assertEquals(expected, scheduledTask.toString());
    }

    @Test
    void notEqualsTest() {
        ScheduledTask newTask = new ScheduledTask("toggle", manager, device0.getId(), taskType);

        assertNotEquals(newTask, scheduledTask);
    }

    @Test
    void equalsTest() {
        ScheduledTask newTask = new ScheduledTask("toggle", manager, device0.getId(), taskType);

        newTask.setId(scheduledTask.getId());

        assertEquals(newTask, scheduledTask);
    }

    @Test
    void hashCodeTest() {
        int actualHashCode = scheduledTask.hashCode();
        ScheduledTask newTask = new ScheduledTask("toggle", manager, device0.getId(), taskType);

        newTask.setId(scheduledTask.getId());
        int expectedHashCode = newTask.hashCode();

        assertEquals(expectedHashCode, actualHashCode);
    }

    @Test
    void run() {
        boolean stateBeforeExecution = device0.isOn();

        scheduledTask.run();
        boolean stateAfterExecution = device0.isOn();

        assertNotEquals(stateBeforeExecution, stateAfterExecution);
    }

    @Test
    void getId() {
        Integer expectedId = new Integer(ScheduledTask.getLatestId());

        assertEquals(expectedId, scheduledTask.getId());
    }

    @Test
    void setId() {
        Integer expectedId = scheduledTask.getId() + 1;

        scheduledTask.setId(expectedId);

        assertEquals(expectedId, scheduledTask.getId());
    }

    @Test
    void getName() {
        String expectedName = "toggle";

        assertEquals(expectedName, scheduledTask.getName());
    }

    @Test
    void setName() {
        String expectedName = "new toggle task";

        scheduledTask.setName(expectedName);

        assertEquals(expectedName, scheduledTask.getName());
    }
}