package Env;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by Mishanya on 27.09.2015.
 */
public class EventQueue {

    private ArrayList<Event> eq;

    public EventQueue(Schedule initSchedule) {
        eq = new ArrayList<Event>();
        scheduleToEventQueue(initSchedule);
    }

    public void scheduleToEventQueue(Schedule sched) {
        for (Node n: sched.getSchedule().keySet()) {
            eq.addAll(sched.getSchedule().get(n).stream().map(si -> new TaskStart(si.getTask().getName(), si.getTask(), si.getStartTime())).collect(Collectors.toList()));
        }
        eq.sort(new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return Double.compare(o1.getTime(), o2.getTime());
            }
        });
    }

    public Event next() {
        return eq.remove(0);
    }

    public boolean isEmpty() {
        return eq.isEmpty();
    }

    public void addEvent(Event e) {
        eq.add(e);
    }
}
