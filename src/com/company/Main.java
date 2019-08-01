package com.company;

import com.company.exceptions.FailedTransactionException;
import com.company.search.FailSearchEngine;
import com.company.servers.Cluster;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Cluster cluster = new Cluster(10);

        System.out.println(cluster);
        System.out.println("---------------------------------------------");

        try {
            cluster.sendData();

            transactionSuccessful(cluster);
        } catch (FailedTransactionException e) {
            transactionFailed(cluster, e);
        }

    }

    private static void transactionFailed(Cluster cluster, FailedTransactionException e) throws InterruptedException {
        System.out.println(cluster);

        System.out.println("Detected fail:");
        e.printStackTrace();
        Thread.sleep(10); // To guarantee stacktrace printing

        System.out.println("Searching transaction to confirm fail:");
        FailSearchEngine engine = new FailSearchEngine();

        System.out.println("\n" + engine.findFail(cluster));
    }

    private static void transactionSuccessful(Cluster cluster) {
        System.out.println(cluster);
        System.out.println("Transaction has passed successfully!");
    }

}
