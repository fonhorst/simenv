package Env;

import java.util.ArrayList;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class Context {

    private Schedule schedule;
    private double time;
    private ArrayList<Node> nodes;

    public Context() {
        this.schedule = new Schedule();
        this.time = 0;
        this.nodes = new ArrayList<Node>();
    }

    public void addNode(Node n){
        nodes.add(n);
        schedule.addNode(n);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public double getTime() {
        return time;
    }
}
