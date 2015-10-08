package Env;

import Env.Entities.Node;
import Env.Entities.Schedule;
import Env.Entities.Task;
import Env.Events.Event;
import Env.Events.TaskEnd;
import Env.Events.TaskFailed;
import Env.Schedulers.Scheduler;
import Utilities.ScheduleVisualizer;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mishanya on 08.10.2015.
 */
public class Simulator {

    private Scheduler scheduler;
    private ArrayList<Node> nodes;
    private ArrayList<Task> tasks;
    private Random rnd;

    public Simulator(Scheduler scheduler, ArrayList<Node> nodes, ArrayList<Task> tasks, Random rnd) {
        this.scheduler = scheduler;
        this.nodes = nodes;
        this.tasks = tasks;
        this.rnd = rnd;
    }

    public Context simualate() {
        Context ctx = new Context(rnd);
        ctx.addNodes(nodes);

        EventQueue eq = new EventQueue();
        Schedule initSched = scheduler.schedule(ctx, tasks);
        ctx.applySchedule(initSched, eq);

        boolean isDebug = true;
        ScheduleVisualizer schedVisual = new ScheduleVisualizer();

        while (!eq.isEmpty()) {
            // Create Jedule picture of current schedule
            if (isDebug) {
                try {
                    schedVisual.schedVisualize(ctx.getSchedule());
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }

            Event curEvent = eq.next();
            if (ctx.getTime() < curEvent.getTime()) {
                ctx.setTime(curEvent.getTime());
            }

            System.out.println("----");
            System.out.println("Current time = " + ctx.getTime());

            if (curEvent instanceof TaskEnd) {
                // Launch next task from event's node schedule, and generate new event for this new task
                System.out.println("Task " + curEvent.getName() + " has been finished at " + curEvent.getTime());
                EventHandler.taskEnd((TaskEnd)curEvent, ctx, eq);
            }

            if (curEvent instanceof TaskFailed) {
                // Task fail launches rescheduling
                System.out.println("Task " + curEvent.getName() + " has been failed at " + curEvent.getTime());
                EventHandler.taskFail((TaskFailed)curEvent, ctx, eq, scheduler);
            }
        }
        return ctx;
    }
}
