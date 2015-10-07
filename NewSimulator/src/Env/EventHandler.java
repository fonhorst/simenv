package Env;

import Env.Schedulers.RandomScheduler;

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
        // TODO do it via TaskFailer, which will generate one of these two events
        if (ctx.rnd.nextDouble() > 0.5) {
            eNode.taskExecute(nextTask);
            ctx.getSchedule().getSchedule().get(eNode).remove(0);
            eq.addEvent(new TaskEnd(nextTask.getName(), nextTask, ctx.getTime() + nextTask.getExecCost(), eNode));
        } else {
            eq.addEvent(new TaskFailed(nextTask.getName(), nextTask, ctx.getTime(), eNode));
        }
        // TODO Insert only this event in right place, without sorting
        eq.sort();
    }

    // TODO change RandoScheduler to interface Scheduler
    public static void taskFail(TaskFailed event, Context ctx, EventQueue eq, RandomScheduler scheduler) {
        System.out.println("Rescheduling...");
        ArrayList<Task> tasks = scheduler.reschedule(ctx);
        Schedule newSched = scheduler.schedule(ctx, tasks);
        ctx.applySchedule(newSched, eq);
    }
}
