package Env.Schedulers;

import Env.*;
import Env.Entities.Node;
import Env.Entities.SchedItem;
import Env.Entities.Schedule;
import Env.Entities.Task;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mishanya on 26.09.2015.
 */

public class RandomScheduler implements Scheduler {

    private Random rnd;

    public RandomScheduler(Random rnd) {
        this.rnd = rnd;
    }

    public Schedule schedule(Context ctx, ArrayList<Task> tasks) {
        Schedule curSched = ctx.getSchedule().clone();
        ArrayList<Node> nodes = ctx.getNodes();
        for (Task t : tasks) {
            Node n = nodes.get(rnd.nextInt(nodes.size()));
            double nTime = curSched.getNodeLastTime(ctx, n);
            double endTime = nTime + t.getExecCost();
            curSched.getSchedule().get(n).add(new SchedItem(n, t, nTime, endTime));
        }
        return curSched;
    }

    // Prepare context to scheduling
    public ArrayList<Task> reschedule(Context ctx) {
        Schedule curSched = ctx.getSchedule();
        ArrayList<Task> reschedTasks = new ArrayList<>();
        for (Node node : curSched.getSchedule().keySet()) {
            ArrayList<SchedItem> nodeSched = curSched.getSchedule().get(node);
            for (SchedItem si : nodeSched) {
                reschedTasks.add(si.getTask());
            }
            curSched.getSchedule().put(node, new ArrayList<>());
        }
        return reschedTasks;
    }

}
