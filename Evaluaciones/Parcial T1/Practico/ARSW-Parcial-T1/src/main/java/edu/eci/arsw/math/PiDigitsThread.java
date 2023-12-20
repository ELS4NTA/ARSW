package edu.eci.arsw.math;

import java.util.LinkedList;

public class PiDigitsThread extends Thread {

    private static int DIGITSPERSUM = 8;
    private static double EPSILON = 1e-17;
    private int startDigit, startInterval, endInterval, name;
    private boolean execution;
    private Object lockObject;
    LinkedList<Byte> digits = new LinkedList<>();

    public PiDigitsThread(int startDigit, int startInterval, int endInterval, int name, Object lockObject) {

        this.startDigit = startDigit;
        this.startInterval = startInterval;
        this.endInterval = endInterval;
        this.name = name;
        this.execution = true;
        this.lockObject = lockObject;
    }

    @Override
    public void run() {
        double sum = 0;
        for (int i = startInterval; i < endInterval; i++) {
            if (i % DIGITSPERSUM == 0) {
                sum = 4 * sum(1, startDigit) - 2 * sum(4, startDigit) - sum(5, startDigit) - sum(6, startDigit);
                startDigit += DIGITSPERSUM;
            }
    
            sum = 16 * (sum - Math.floor(sum));
            digits.add((byte) sum);
        }
    }

    /**
     * Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
     * 
     * @param m
     * @param n
     * @return
     */
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < EPSILON) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /**
     * Return 16^p mod m.
     * @param p
     * @param m
     * @return
     */
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }


    public LinkedList<Byte> getDigits() {
        return digits;
    }

    public void setExecution(boolean execution) {
        this.execution = execution;
    }

    


    
}
