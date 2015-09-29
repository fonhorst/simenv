package Env;

/**
 * Created by Mishanya on 29.09.2015.
 */
public class TaskFailed extends Event {


    private String name;
    private Task task;
    private double time;
    private Node node;

    public TaskFailed(String name, Task task, double time, Node node) {
        this.name = name;
        this.task = task;
        this.time = time;
        this.node = node;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public double getTime() {
        return 0;
    }

    public Node getNode() {
        return node;
    }
}
