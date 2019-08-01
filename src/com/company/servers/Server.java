package com.company.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Server implements FallibleWithInners {

    private static final Random RANDOM = new Random();

    private final int id;
    private final List<Node> nodes;

    private boolean failed;

    Server(int id, int maxNumberOfItems) {
        this.id = id;
        int numberOfNodes = RANDOM.nextInt(maxNumberOfItems - 1) + 1;
        nodes = new ArrayList<>(numberOfNodes);
        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new Node(i));
        }
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", nodes=" + nodes.stream()
                                  .map(Objects::toString)
                                  .map(string -> string + "\n")
                                  .collect(Collectors.toList()) +
                '}';
    }

    @Override
    public int getSize() {
        return nodes.size();
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
        return nodes.get(number);
    }

    void failRandomNode() {
        failed = true;
        int failedNode = RANDOM.nextInt(nodes.size());
        for (int i = failedNode; i < nodes.size(); i++) {
            nodes.get(i).failNode();
        }
    }

    void failAll() {
        failed = true;
        for (Node node : nodes) {
            node.failNode();
        }
    }
}
