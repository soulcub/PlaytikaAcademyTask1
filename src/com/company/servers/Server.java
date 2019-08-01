package com.company.servers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import com.company.exceptions.FailedTransactionException;

public class Server implements FallibleWithInners, Transactionable {

    private static final Random RANDOM = new Random();

    private final int id;
    private final List<Node> nodes = new ArrayList<>();

    private boolean transactionPassed;

    Server(int id, int maxNumberOfItems) {
        this.id = id;
        int numberOfNodes = RANDOM.nextInt(maxNumberOfItems - 1) + 1;
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
    public boolean isTransactionPassed() {
        return transactionPassed;
    }

    @Override
    public FallibleWithInners getInnerFallible(int number) {
        return nodes.get(number);
    }

    @Override
    public void doTransaction() {
        try {
            for (Node node : nodes) {
                node.doTransaction();
            }
        } catch (FailedTransactionException e) {
            throw new FailedTransactionException(MessageFormat.format("Server №{0} has transactionPassed", id), e);
        }
        transactionPassed = true;
    }
}
