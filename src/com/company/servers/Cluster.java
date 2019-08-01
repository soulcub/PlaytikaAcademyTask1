package com.company.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import com.company.exceptions.FailedTransactionException;

public class Cluster implements FallibleWithInners {

    private final static Random RANDOM = new Random();

    private final List<Server> servers = new ArrayList<>();

    private boolean transactionPassed;

    public Cluster(int maxNumberOfItems) {
        fillServers(maxNumberOfItems);
    }

    @Override
    public int getSize() {
        return servers.size();
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isTransactionPassed() {
        return transactionPassed;
    }

    @Override
    public FallibleWithInners getInnerFallible(int id) {
        return servers.get(id);
    }

    public void sendData() {
        try {
            for (Server server : servers) {
                server.doTransaction();
            }
            transactionPassed = true;
        } catch (FailedTransactionException e) {
            throw new FailedTransactionException("Cluster has failed transaction", e);
        }
    }

    @Override
    public String toString() {
        return "Cluster{" + servers.stream()
                                  .map(Objects::toString)
                                  .map(string -> string + "\n")
                                  .map(string -> string.replaceAll("\\[", "\n"))
                                  .collect(Collectors.toList()).toString()
                                  .replaceAll("\n, ", "\n")
                                  .replaceAll("\n]}", "\n")
                                  .replaceAll("\\[", "[\n\n")
                +
                '}';
    }

    private void fillServers(int maxNumberOfItems) {
        int numberOfServers = RANDOM.nextInt(maxNumberOfItems - 1) + 1;
        for (int i = 0; i < numberOfServers; i++) {
            servers.add(new Server(i, maxNumberOfItems));
        }
    }

}
