package Env.Events;

import Env.Entities.Node;

/**
 * Created by Mishanya on 24.09.2015.
 */
public class AddNode implements Event {

    private String name;
    private Node node;
    private double time;

    public AddNode(String name, Node node, double time) {
        this.name = name;
        this.node = node;
        this.time = time;
    }

    @Override
    public double getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Node getNode() {
        return node;
    }

}
