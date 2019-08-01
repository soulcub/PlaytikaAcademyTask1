package com.company.servers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Cluster implements FallibleWithInners {

    private final static Random RANDOM = new Random();

    private final List<Server> servers = new ArrayList<>();

    private boolean failed;

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
    public boolean isFailed() {
        return failed;
    }

    @Override
    public FallibleWithInners getInnerFallible(int id) {
        return servers.get(id);
    }

    public void sendData() {
        failed = true;
        int failedServer = RANDOM.nextInt(servers.size());
        servers.get(failedServer).failRandomNode();
        for (int i = failedServer + 1; i < servers.size(); i++) {
            servers.get(i).failAll();
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
