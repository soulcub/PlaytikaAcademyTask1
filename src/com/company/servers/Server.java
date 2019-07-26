package com.company.servers;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Server implements FallibleWithInners {

    private static final Random RANDOM = new Random();

    private final int id;
    private final Node[] nodes;

    private boolean failed;

    Server(int id, int maxNumberOfItems) {
        this.id = id;
        int numberOfNodes = RANDOM.nextInt(maxNumberOfItems - 1) + 1;
        nodes = new Node[numberOfNodes];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i);
        }
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", nodes=" + Arrays.stream(nodes)
                                   .map(Objects::toString)
                                   .map(string -> string + "\n")
                                   .collect(Collectors.toList()) +
                '}';
    }

    @Override
    public int getSize() {
        return nodes.length;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public FallibleWithInners getInnerFallible(int number) {
        return nodes[number];
    }

    void failRandomNode() {
        failed = true;
        int failedNode = RANDOM.nextInt(nodes.length);
        for (int i = failedNode; i < nodes.length; i++) {
            nodes[i].failNode();
        }
    }

    void failAll() {
        failed = true;
        for (Node node : nodes) {
            node.failNode();
        }
    }
}
