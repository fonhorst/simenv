package Experiments;

import Env.*;
import Env.Entities.Node;
import Env.Entities.Schedule;
import Env.Entities.Task;
import Env.Events.Event;
import Env.EventQueue;
import Env.Events.TaskEnd;
import Env.Events.TaskFailed;
import Env.Schedulers.RandomScheduler;
import Env.Schedulers.Scheduler;
import Utilities.ScheduleVisualizer;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.Random;

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
        tasks.add(new Task("t1", 5));
        tasks.add(new Task("t2", 15));
        tasks.add(new Task("t3", 10));
        tasks.add(new Task("t4", 20));
        tasks.add(new Task("t5", 5));
        tasks.add(new Task("t6", 10));

        Scheduler rndScheduler = new RandomScheduler(rnd);
        Simulator sim = new Simulator(rndScheduler, nodes, tasks, rnd);
        Context ctx = sim.simualate();

        System.out.println("====");
        System.out.println("Finish time " + ctx.getTime());
    }
}
