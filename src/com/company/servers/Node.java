package com.company.servers;

public class Node implements FallibleWithInners {

    private final int id;
    private boolean failed;

    Node(int id) {
        this.id = id;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public FallibleWithInners getInnerFallible(int number) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public int getId() {
        return id;
    }

    void failNode() {
        this.failed = true;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", failed=" + failed +
                '}';
    }
}
