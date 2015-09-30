package Experiments;

import Env.*;
import Env.Event;
import Env.EventQueue;
import Env.Schedulers.RandomScheduler;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class FirstTestRun {

    public static void main(String[] args) {

        Random rnd = new Random();

        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node("n1"));
        nodes.add(new Node("n2"));
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("t1", 10));
        tasks.add(new Task("t2", 10));
        tasks.add(new Task("t3", 10));
        tasks.add(new Task("t4", 10));

        RandomScheduler rndScheduler = new RandomScheduler(rnd);

        Context ctx = new Context(rnd);
        for (Node n: nodes) {
            ctx.addNode(n);
        }

        EventQueue eq = new EventQueue();
        Schedule initSched = rndScheduler.schedule(ctx, tasks);
        ctx.applySchedule(initSched, eq);

        while (!eq.isEmpty()) {
            Event curEvent = eq.next();
            if (ctx.getTime() < curEvent.getTime()) {
                ctx.setTime(curEvent.getTime());
            }

            System.out.println("----");
            System.out.println("Current time = " + ctx.getTime());

            if (curEvent instanceof TaskEnd) {
                EventHandler.taskEnd((TaskEnd)curEvent, ctx, eq);
            }

            if (curEvent instanceof TaskFailed) {
                System.out.println("Task " + curEvent.getName() + " has failed at " + curEvent.getTime());
                // reschedule
            }
        }

        System.out.println("====");
        System.out.println("Finish time " + ctx.getTime());
    }
}
