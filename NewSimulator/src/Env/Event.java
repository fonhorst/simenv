package Env;

/**
 * Created by Mishanya on 24.09.2015.
 */
public abstract class Event {
    private double time;
    private String name;

    public abstract double getTime();
    public abstract String getName();
}
