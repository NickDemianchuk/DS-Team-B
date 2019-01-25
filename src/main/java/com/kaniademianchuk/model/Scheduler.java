package com.kaniademianchuk.model;

import java.util.Date;
import java.util.Timer;

public class Scheduler {

    private final Timer timer = new Timer();

    public Scheduler() {
    }

    public ScheduledTask scheduleTask(Date date, Integer interval, final Runnable task) {
        ScheduledTask scheduledTask = new ScheduledTask() {
            @Override
            public void run() {
                task.run();
            }
        };
        timer.schedule(scheduledTask, date, interval);
        return scheduledTask;
    }
}
