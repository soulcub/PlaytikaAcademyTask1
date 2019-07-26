package com.company;

import com.company.search.FailSearchEngine;
import com.company.servers.Cluster;

public class Main {

    public static void main(String[] args) {
        Cluster cluster = new Cluster(10);

        System.out.println(cluster);
        System.out.println("---------------------------------------------");

        cluster.sendData();

        System.out.println(cluster);

        FailSearchEngine engine = new FailSearchEngine();

        System.out.println("\n" + engine.findFail(cluster));
    }

}
