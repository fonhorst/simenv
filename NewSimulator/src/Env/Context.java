package Env;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class Context {

    private Schedule schedule;
    private double time;
    private ArrayList<Node> nodes;
    public Random rnd;

    public Context(Random rnd) {
        this.rnd = rnd;
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

    public void applySchedule(Schedule newSchedule, EventQueue eq) {
        for (Node n : newSchedule.getSchedule().keySet()) {
            ArrayList<SchedItem> curItems = newSchedule.getSchedule().get(n);
            for (SchedItem si : curItems) {
                schedule.getSchedule().get(n).add(si);
            }
            if (n.isFree()) {
                Task curTask = schedule.getSchedule().get(n).get(0).getTask();
                if (rnd.nextDouble() > 0) {
                    n.taskExecute(curTask);
                    schedule.getSchedule().get(n).remove(curTask);
                    eq.addEvent(new TaskEnd(curTask.getName(), curTask, time + curTask.getExecCost(), n));
                } else {
                    eq.addEvent(new TaskFailed(curTask.getName(), curTask, time, n));
                }
            }
        }

    }

    public double getTime() {
        return time;
    }
}
