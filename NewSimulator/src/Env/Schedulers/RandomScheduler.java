package Env.Schedulers;

import Env.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mishanya on 26.09.2015.
 */
public class RandomScheduler {

    private Random rnd;

    public RandomScheduler(Random rnd) {
        this.rnd = rnd;
    }

    // Случайно распихивает все таски из списка по нодам
    public Schedule initSchedule(Context ctx, ArrayList<TaskStart> events) {
        Schedule sched = new Schedule();
        ArrayList<Node> nodes = ctx.getNodes();
        sched.addNodes(nodes);
        while (!events.isEmpty()) {
            TaskStart ts = events.remove(rnd.nextInt(events.size()));
            Node n = nodes.get(rnd.nextInt(nodes.size()));
            double nTime = sched.getNodeLastTime(n);
            sched.getSchedule().get(n).add(new SchedItem(n, ts.getTask(), nTime, nTime + ts.getTask().getExecCost()));
        }
        return sched;
    }

    // Назначает заданную таску на случайный нод и добавляет событие завершения таски в очередь
    public Schedule schedule(Context ctx, EventQueue eq, TaskStart ts) {
        Schedule curSched = ctx.getSchedule();
        ArrayList<Node> nodes = ctx.getNodes();
        Node n = nodes.get(rnd.nextInt(nodes.size()));
        double nTime = curSched.getNodeLastTime(n);
        double endTime = nTime + ts.getTask().getExecCost();
        curSched.getSchedule().get(n).add(new SchedItem(n, ts.getTask(), nTime, endTime));
        eq.addEvent(new TaskEnd(ts.getName(), ts.getTask(), endTime));
        return curSched;
    }

}
