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

        // ��������� ��� ����� � ������
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node("n1"));
        nodes.add(new Node("n2"));
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task("t1", 10));
        tasks.add(new Task("t2", 10));
        tasks.add(new Task("t3", 10));
        tasks.add(new Task("t4", 10));

        // ���������� ������� �� ������
        ArrayList<TaskStart> initEvents = new ArrayList<TaskStart>();
        initEvents.addAll(tasks.stream().map(t -> new TaskStart(t.getName(), t, 0)).collect(Collectors.toList()));

        RandomScheduler rndScheduler = new RandomScheduler(rnd);

        // ������������� ��������� � ������
        Context ctx = new Context();
        for (Node n: nodes) {
            ctx.addNode(n);
        }

        // ���������� ��������� ���������� � �� ��� ������ ������ EventQueue. ���� ���������� �� �����������
        Schedule initSched = rndScheduler.initSchedule(ctx, initEvents);
        EventQueue eq = new EventQueue(initSched);

        // ��� �� ��������
        while (!eq.isEmpty()) {
            Event curEvent = eq.next();
            if (ctx.getTime() < curEvent.getTime()) {
                ctx.setTime(curEvent.getTime());
            }

            System.out.println("----");
            System.out.println("Current time = " + ctx.getTime());

            if (curEvent instanceof TaskStart) {
                System.out.println("Schedule task " + curEvent.getName());
                Schedule newSchedule = rndScheduler.schedule(ctx, eq, (TaskStart) curEvent);
                ctx.setSchedule(newSchedule);
                System.out.println("Task has been scheduled");
            }

            if (curEvent instanceof TaskEnd) {
                System.out.println("Task " + curEvent.getName() + " has finished at " + curEvent.getTime());
            }
        }

        System.out.println("====");
        System.out.println("Finish time " + ctx.getTime());
    }
}
