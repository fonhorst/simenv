package Env;

import Env.Entities.Node;
import Env.Entities.SchedItem;
import Env.Entities.Schedule;
import Env.Entities.Task;
import Env.Events.TaskEnd;
import Env.Events.TaskFailed;
import Env.Schedulers.RandomScheduler;
import Env.Schedulers.Scheduler;

import java.util.ArrayList;

/**
 * Created by Mishanya on 01.10.2015.
 */
public class EventHandler {

    public static void taskEnd(TaskEnd event, Context ctx, EventQueue eq) {
        Node eNode = event.getNode();
        eNode.taskFinished();

        ArrayList<SchedItem> nodeSched = ctx.getSchedule().getSchedule().get(eNode);
        if (nodeSched.isEmpty()) {
            return;
        }
        Task nextTask = nodeSched.get(0).getTask();
        SchedItem si = nodeSched.get(0);
        // TODO do it via TaskFailer, which will generate one of these two events
        if (ctx.rnd.nextDouble() > 0.5) {
            eNode.taskExecute(si);
            ctx.getSchedule().getSchedule().get(eNode).remove(0);
            eq.addEvent(new TaskEnd(nextTask.getName(), nextTask, si.getEndTime(), eNode));
        } else {
            eq.addEvent(new TaskFailed(nextTask.getName(), nextTask, ctx.getTime(), eNode));
        }
        // TODO Insert only this event in right place, without sorting
        eq.sort();
    }

    public static void taskFail(TaskFailed event, Context ctx, EventQueue eq, Scheduler scheduler) {
        System.out.println("Rescheduling...");
        ArrayList<Task> tasks = scheduler.reschedule(ctx);
        Schedule newSched = scheduler.schedule(ctx, tasks);
        ctx.applySchedule(newSched, eq);
    }
}
