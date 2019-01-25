package com.kaniademianchuk.model;

import java.util.Date;
import java.util.Optional;
import java.util.Timer;

public class Scheduler {

    private final Timer timer = new Timer();

    public Scheduler() {
    }

    public ScheduledTask scheduleTask(Date date, Optional<Integer> interval, final ScheduledTask task) {
        if (interval.isPresent()) {
            timer.schedule(task, date, interval.get());
        } else {
            timer.schedule(task, date);
        }
        return task;
    }
}
