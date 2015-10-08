package Env;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class Node {

    private String name;
    // TODO left only SchedItem
    private Task execTask;
    private SchedItem execItem;

    public Node(String name){
        this.name = name;
        execTask = null;
        execItem = null;
    }

    public String getName() {
        return name;
    }

    public void taskExecute(Task t, double currTime) {
        execTask = t;
        //TODO work only with SchedItem, and with true times
        execItem = new SchedItem(this, t, currTime - 5, currTime);
    }

    public void taskFinished() {
        execTask = null;
        execItem = null;
    }

    public boolean isFree() {
        return execTask == null;
    }

    public SchedItem getExecItem() {
        return execItem;
    }
}
