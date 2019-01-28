package com.kaniademianchuk.model;

import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.util.DateUtil;
import com.kaniademianchuk.util.MockEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {

    private ITogglable device0;
    private Manager<ITogglable> manager;
    private Scheduler scheduler;
    private ScheduledTaskType taskType;
    private ScheduledTask scheduledTask;

    @BeforeEach
    void setUp() {
        device0 = new DefaultTogglable("device0", true, new MockEventHandler());
        manager = new Manager<>();
        manager.addDevice(device0);
        scheduler = new Scheduler();
        taskType = ScheduledTaskType.TOGGLE;
        scheduledTask = new ScheduledTask("toggle", manager, device0.getId(), taskType);
    }

    @Test
    void scheduleTaskTest() {
        Optional<Integer> interval = Optional.empty();
        boolean stateBeforeSchedulingTask = device0.isOn();

        Date currentTimePlusOneSecond = DateUtil.add(new Date(), Calendar.SECOND, 1);
        scheduler.scheduleTask(currentTimePlusOneSecond, interval, scheduledTask);
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
        boolean stateAfterExecutingTask = device0.isOn();

        assertNotEquals(stateBeforeSchedulingTask, stateAfterExecutingTask);
    }

    @Test
    void scheduleAlreadyScheduledTaskTest() {
        Optional<Integer> interval = Optional.of(DateUtil.DAILY);
        Date currentTimePlusOneSecond = DateUtil.add(new Date(), Calendar.SECOND, 1);
        scheduler.scheduleTask(currentTimePlusOneSecond, interval, scheduledTask);
        String expectedException = "Task already scheduled or cancelled";

        try {
            scheduler.scheduleTask(currentTimePlusOneSecond, interval, scheduledTask);
            fail("exception is not thrown");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
            assertEquals(expectedException, e.getMessage());
        }
    }
}