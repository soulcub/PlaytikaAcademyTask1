package com.company.servers;

import java.text.MessageFormat;
import java.util.Random;

import com.company.exceptions.FailedTransactionException;

public class Node implements FallibleWithInners, Transactionable {

    private static final Random RANDOM = new Random();

    private final int id;
    private boolean transactionPassed;

    Node(int id) {
        this.id = id;
    }

    @Override
    public boolean isTransactionPassed() {
        return transactionPassed;
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

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", transactionPassed=" + transactionPassed +
                '}';
    }

    @Override
    public void doTransaction() {
        int number = RANDOM.nextInt(30); // generating some chance to fail transaction 1/30
        if (number == 0) {
            throw new FailedTransactionException(MessageFormat.format("Node №{0} has transactionPassed", id));
        }
        transactionPassed = true;
    }

}
