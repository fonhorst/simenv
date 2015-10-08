package Env.Entities;

import Env.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mishanya on 26.09.2015.
 */
public class Schedule {

    private Map<Node, ArrayList<SchedItem>> schedule;

    public Schedule() {
        this.schedule = new HashMap<Node, ArrayList<SchedItem>>();
    }

    public void addNode(Node n) {
        schedule.put(n, new ArrayList<SchedItem>());
    }

    public Map<Node, ArrayList<SchedItem>> getSchedule() {
        return schedule;
    }

    public double getNodeLastTime(Context ctx, Node n) {
        //TODO refactor this chaos
        ArrayList<SchedItem> nodeTasks = schedule.get(n);
        double schedTime = ctx.getTime();
        if (!nodeTasks.isEmpty()) {
            schedTime = Math.max(nodeTasks.get(nodeTasks.size() - 1).getEndTime(), ctx.getTime());
        }
        double nodeExecEnd = ctx.getTime();
        if (n.getExecItem() != null) {
            nodeExecEnd = Math.max(n.getExecItem().getEndTime(), ctx.getTime());
        }
        return Math.max(nodeExecEnd, schedTime);
    }

    // TODO Java should has some standard clone methods
    public Schedule clone() {
        Schedule copy = new Schedule();
        for (Node n : schedule.keySet()) {
            copy.addNode(n);
            for (SchedItem si : schedule.get(n)) {
                copy.schedule.get(n).add(si);
            }
        }
        return copy;
    }

}
