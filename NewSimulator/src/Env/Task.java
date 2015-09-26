package Env;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class Task {

    private String name;
    private int execCost;

    public Task(String name, int execCost) {
        this.name = name;
        this.execCost = execCost;
    }

    public String getName() {
        return name;
    }

    public int getExecCost() {
        return execCost;
    }
}
