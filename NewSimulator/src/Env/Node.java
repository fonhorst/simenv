package Env;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class Node {

    private String name;
    private Task execTask;

    public Node(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void taskExecute(Task t) {
        execTask = t;
    }

    public void taskFinished() {
        execTask = null;
    }

    public boolean isFree() {
        return execTask == null;
    }
}
