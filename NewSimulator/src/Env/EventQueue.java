package Env;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by Mishanya on 27.09.2015.
 */
public class EventQueue {

    private ArrayList<Event> eq;

    public EventQueue() {
        eq = new ArrayList<Event>();
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
