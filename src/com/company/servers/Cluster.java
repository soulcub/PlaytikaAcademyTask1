package com.company.servers;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class Cluster implements FallibleWithInners {

    private final static Random RANDOM = new Random();
    private final Server[] servers;

    private boolean failed;

    public Cluster(int maxNumberOfItems) {
        servers = createServers(maxNumberOfItems);
        fillServers(maxNumberOfItems);
    }

    @Override
    public int getSize() {
        return servers.length;
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
        return servers[id];
    }

    public void sendData() {
        failed = true;
        int failedServer = RANDOM.nextInt(servers.length);
        servers[failedServer].failRandomNode();
        for (int i = failedServer + 1; i < servers.length; i++) {
            servers[i].failAll();
        }
    }

    @Override
    public String toString() {
        return "Cluster{" + Arrays.stream(servers)
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

    private Server[] createServers(int maxNumberOfItems) {
        int numberOfServers = RANDOM.nextInt(maxNumberOfItems - 1) + 1;
        return new Server[numberOfServers];
    }

    private void fillServers(int maxNumberOfItems) {
        for (int i = 0; i < servers.length; i++) {
            servers[i] = new Server(i, maxNumberOfItems);
        }
    }

}
