package Env.Entities;

import Env.Entities.Node;
import Env.Entities.Task;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class SchedItem {

    private Node node;
    private Task task;
    private double startTime;
    private double endTime;
    private boolean isFailed;


    public SchedItem(Node node, Task task, double startTime, double endTime) {
        this.node = node;
        this.task = task;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isFailed = false;
    }

    public Node getNode() {
        return node;
    }

    public Task getTask() {
        return task;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setStatus(boolean status) {
        isFailed = status;
    }

    public boolean isFailed(){
        return this.isFailed;
    }

}
