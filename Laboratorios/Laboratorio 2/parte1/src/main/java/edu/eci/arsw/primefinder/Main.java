package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        List<PrimeFinderThread> threads = new ArrayList<>();
        Object lockObject = new Object();
        boolean anyThreadRunning = true;

        threads.add(new PrimeFinderThread(0, 10000000, 1, lockObject));
        threads.add(new PrimeFinderThread(10000001, 20000000, 2, lockObject));
        threads.add(new PrimeFinderThread(20000001, 30000000, 3, lockObject));

        for (PrimeFinderThread thread : threads) {
            thread.start();
        }

        try {
            while (anyThreadRunning) {
                anyThreadRunning = false;
                for (PrimeFinderThread thread : threads) {
                    if (thread.isAlive()) {
                        anyThreadRunning = true;
                        break;
                    }
                }
                Thread.sleep(5000);
                stopAndExecute(threads, lockObject);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops and executes threads
     * @param threads List of threads
     * @param lockObject Lock object to synchronize threads
     * @throws InterruptedException
     * @author Angie Mojica
     * @author Daniel Santanilla
     */
    private static void stopAndExecute(List<PrimeFinderThread> threads, Object lockObject) throws InterruptedException {
        for (PrimeFinderThread thread : threads) {
            thread.setExecution(false);
        }
        Thread.sleep(500);
        System.out.println("==================== Stopping threads... ====================");
        getTotalPrimes(threads);
        System.out.println("Press ENTER to continue...");
        sc.nextLine();
        System.out.println("==================== Resuming threads... ====================");
        for (PrimeFinderThread thread : threads) {
            thread.setExecution(true);
            synchronized (lockObject) {
                lockObject.notifyAll();
            }
        }
    }

    /**
     * Gets total of primes found by threads
     * @param threads List of threads
     * @return Total of primes found by threads
     * @author Angie Mojica
     * @author Daniel Santanilla
     */
    private static void getTotalPrimes(List<PrimeFinderThread> threads) {
        int totalPrimes = 0;
        for (PrimeFinderThread thread : threads) {
            totalPrimes += thread.getPrimes().size();
        }
        System.out.println("Total of found primes: " + totalPrimes);
    }

}
