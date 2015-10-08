package Env.Events;

import Env.Entities.Node;
import Env.Entities.Task;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class TaskEnd implements Event {

    private String name;
    private Task task;
    private double time;
    private Node node;

    public TaskEnd(String name, Task task, double time, Node node) {
        this.name = name;
        this.task = task;
        this.time = time;
        this.node = node;
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

    public Node getNode() {
        return node;
    }
}
