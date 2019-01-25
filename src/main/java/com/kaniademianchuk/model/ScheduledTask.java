package com.kaniademianchuk.model;

import com.kaniademianchuk.api.IIdentifiable;

import java.util.TimerTask;

public abstract class ScheduledTask extends TimerTask implements IIdentifiable {
    private static Integer latestId = 0;
    private Integer id;
    private String name;

    ScheduledTask() {
        this.id = latestId++;
        this.name = String.format("Task %d", this.id);
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
