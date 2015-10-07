package Env.Schedulers;

import Env.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mishanya on 26.09.2015.
 */

// TODO create interface for Schedulers, and implement that
public class RandomScheduler {

    private Random rnd;

    public RandomScheduler(Random rnd) {
        this.rnd = rnd;
    }

    public Schedule schedule(Context ctx, ArrayList<Task> tasks) {
        Schedule curSched = ctx.getSchedule().clone();
        ArrayList<Node> nodes = ctx.getNodes();
        for (Task t : tasks) {
            Node n = nodes.get(rnd.nextInt(nodes.size()));
            double nTime = curSched.getNodeLastTime(n);
            double endTime = nTime + t.getExecCost();
            curSched.getSchedule().get(n).add(new SchedItem(n, t, nTime, endTime));
        }
        return curSched;
    }

    // Prepare context to scheduling
    public ArrayList<Task> reschedule(Context ctx) {
        Schedule curSched = ctx.getSchedule();
        Double curTime = ctx.getTime();
        ArrayList<Task> reschedTasks = new ArrayList<Task>();
        for (Node node : curSched.getSchedule().keySet()) {
            ArrayList<SchedItem> nodeSched = curSched.getSchedule().get(node);
            for (SchedItem si : nodeSched) {
                reschedTasks.add(si.getTask());
            }
            curSched.getSchedule().put(node, new ArrayList<SchedItem>());
        }
        return reschedTasks;
    }

}
