package Experiments;

import Env.*;
import Env.Event;
import Env.EventQueue;
import Env.Schedulers.RandomScheduler;
import Utilities.ScheduleVisualizer;

import javax.swing.*;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class FirstTestRun {

    public static void main(String[] args) throws TransformerException {

        Random rnd = new Random();

        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node("n1"));
        nodes.add(new Node("n2"));
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("t1", 5));
        tasks.add(new Task("t2", 15));
        tasks.add(new Task("t3", 10));
        tasks.add(new Task("t4", 20));

        RandomScheduler rndScheduler = new RandomScheduler(rnd);

        Context ctx = new Context(rnd);
        ctx.addNodes(nodes);

        EventQueue eq = new EventQueue();
        Schedule initSched = rndScheduler.schedule(ctx, tasks);
        ctx.applySchedule(initSched, eq);

        ScheduleVisualizer schedVisual = new ScheduleVisualizer();

        boolean isDebug = true;

        while (!eq.isEmpty()) {

            if (isDebug) {
                schedVisual.schedVisualize(ctx.getSchedule());
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
                EventHandler.taskFail((TaskFailed)curEvent, ctx, eq, rndScheduler);
            }
        }

        System.out.println("====");
        System.out.println("Finish time " + ctx.getTime());
    }
}
