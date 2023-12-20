package edu.eci.arsw.math;

import java.util.LinkedList;

/**
 * An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
 * digits of pi.
 * https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
 * *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
 * 
 */
public class PiDigits {

    
    private static LinkedList<PiDigitsThread> threads = new LinkedList<>();
    private static int DIGITSPERSUM = 8;
    
    /**
     * Returns a range of hexadecimal digits of pi.
     * 
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @param N The number of threads to use.
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) {
        
        Object lockObject = new Object();
        LinkedList<Integer> digits = new LinkedList<>();

        checkInvalidInterval(start, count);
        createThreads(start, count, N, lockObject);
        for (PiDigitsThread thread : threads) {
            try {
                thread.join();
                digits.addAll(thread.getDigits());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        byte[] digitsArray = linkedListTobyteArray(count, digits);
        return digitsArray;
    }

    

    private static void checkInvalidInterval(int start, int count) {
        if (start < 0 || count < 0) {
            throw new RuntimeException("Invalid Interval");
        }
    }

    /**
     * Creates threads
     * 
     * @param N Number of threads to create
     */
    private static void createThreads(int start, int count, int N, Object lockObject) {
        int digitsPerThread = count / N;
        for (int i = 0; i < N; i++) {
            int startDigit = i * DIGITSPERSUM;
            int startInterval = i * digitsPerThread;
            int endInterval = (i == N-1) ? count : start + digitsPerThread;
            PiDigitsThread threadI = new PiDigitsThread(startDigit, startInterval, endInterval, i, lockObject);
            threads.add(threadI);
            threadI.start();
        }
    }

    public static byte[] linkedListTobyteArray(int count, LinkedList<Byte> digits) {
        byte[] bytes = new byte[count];
        int i = 0;
        for (Byte b : digits) {
            bytes[i++] = b;
        }
        return bytes;
    }

    

}
