package Env.Events;

import Env.Entities.Task;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class TaskStart implements Event {

    private String name;
    private Task task;
    private double time;

    public TaskStart(String name, Task task, double time) {
        this.name = name;
        this.task = task;
        this.time = time;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public double getTime() {
        return time;
    }

    @Override
    public String getName() {
        return name;
    }
}
