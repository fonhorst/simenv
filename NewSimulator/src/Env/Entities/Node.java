package Env.Entities;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class Node {

    private String name;
    private SchedItem execItem;

    public Node(String name){
        this.name = name;
        execItem = null;
    }

    public String getName() {
        return name;
    }

    public void taskExecute(SchedItem si) {
        execItem = si;
    }

    public void taskFinished() {
        execItem = null;
    }

    public boolean isFree() {
        return execItem == null;
    }

    public SchedItem getExecItem() {
        return execItem;
    }
}
