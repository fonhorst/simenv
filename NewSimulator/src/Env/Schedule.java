package Env;

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

    public void addNodes(ArrayList<Node> nodes) {
        for (Node n: nodes) {
            addNode(n);
        }
    }

    public Map<Node, ArrayList<SchedItem>> getSchedule() {
        return schedule;
    }

    public double getNodeLastTime(Node n) {
        ArrayList<SchedItem> nodeTasks = schedule.get(n);
        if (nodeTasks.isEmpty()) {
            return 0;
        }
        return nodeTasks.get(nodeTasks.size() - 1).getEndTime();
    }

}
