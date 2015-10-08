package Env;

import Env.Entities.Node;
import Env.Entities.SchedItem;
import Env.Entities.Schedule;
import Env.Entities.Task;
import Env.Events.TaskEnd;
import Env.Events.TaskFailed;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class Context {
    // TODO traverse schedule's functions
    private Schedule schedule;
    private double time;
    private ArrayList<Node> nodes;
    public Random rnd;

    public Context(Random rnd) {
        this.rnd = rnd;
        this.schedule = new Schedule();
        this.time = 0;
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node n){
        nodes.add(n);
        schedule.addNode(n);
    }

    public void addNodes(ArrayList<Node> nodes) {
        nodes.forEach(this::addNode);
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
            if (n.isFree() && schedule.getSchedule().get(n).size() > 0) {
                Task curTask = schedule.getSchedule().get(n).get(0).getTask();
                SchedItem si = schedule.getSchedule().get(n).get(0);
                // TODO do it via TaskFailer, which will generate one of these two events
                if (rnd.nextDouble() > 0) {
                    // TODO refactor repeated schedule.getSchedule().get....
                    n.taskExecute(si);
                    schedule.getSchedule().get(n).remove(0);
                    eq.addEvent(new TaskEnd(curTask.getName(), curTask, si.getEndTime(), n));
                } else {
                    eq.addEvent(new TaskFailed(curTask.getName(), curTask, time, n));
                }
            }
        }
        eq.sort();
    }

    public double getTime() {
        return time;
    }
}
