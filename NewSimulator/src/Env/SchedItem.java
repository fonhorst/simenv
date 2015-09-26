package Env;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class SchedItem {

    private Node node;
    private Task task;
    private double startTime;
    private double endTime;


    public SchedItem(Node node, Task task, double startTime, double endTime) {
        this.node = node;
        this.task = task;
        this.startTime = startTime;
        this.endTime = endTime;
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

}
