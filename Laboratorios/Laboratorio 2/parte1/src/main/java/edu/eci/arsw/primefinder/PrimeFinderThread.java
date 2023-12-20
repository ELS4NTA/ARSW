package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

/**
 * The class PrimeFinderThread is a thread that finds prime numbers in a range
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
public class PrimeFinderThread extends Thread {

    private int a, b, name;
    private boolean execution;
    private Object lockObject;

    private List<Integer> primes = new LinkedList<Integer>();

    public PrimeFinderThread(int a, int b, int name, Object lockObject) {
        super();
        this.a = a;
        this.b = b;
        this.name = name;
        this.execution = true;
        this.lockObject = lockObject;
    }

    public void run() {
        for (int i = a; i <= b; i++) {
            if (isPrime(i)) {
                primes.add(i);
                System.out.println("Thread " + name + " found prime: " + i);
                synchronized (lockObject) {
                    if (!execution) {
                        try {
                            System.out.println("Thread " + name + " is stoping...");
                            lockObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    boolean isPrime(int n) {
        if (n % 2 == 0)
            return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public void setExecution(boolean execution) {
        this.execution = execution;
    }

}
