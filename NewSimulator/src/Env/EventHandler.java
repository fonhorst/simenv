package Env;

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
        if (ctx.rnd.nextDouble() > 0) {
            eNode.taskExecute(nextTask);
            ctx.getSchedule().getSchedule().get(eNode).remove(0);
            eq.addEvent(new TaskEnd(nextTask.getName(), nextTask, ctx.getTime() + nextTask.getExecCost(), eNode));
        } else {
            eq.addEvent(new TaskFailed(nextTask.getName(), nextTask, ctx.getTime(), eNode));
        }
        eq.sort();
    }
}
