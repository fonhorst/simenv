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
                ((TaskEnd) curEvent).getNode();
                System.out.println("Task finished " + curEvent.getName());
                Node eNode = ((TaskEnd) curEvent).getNode();
                eNode.taskFinished();
                Task newTask = ctx.getSchedule().getSchedule().get(eNode).get(0).getTask();
                if (rnd.nextDouble() > 0.5) {
                    eNode.taskExecute(newTask);
                    ctx.getSchedule().getSchedule().get(eNode).remove(0);
                    eq.addEvent(new TaskEnd(newTask.getName(), newTask, ctx.getTime() + newTask.getExecCost(), eNode));
                    System.out.println("new Task has been started");
                } else {
                    eq.addEvent(new TaskFailed(newTask.getName(), newTask, ctx.getTime(), eNode));
                    System.out.println("Next Task failed");
                }
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
