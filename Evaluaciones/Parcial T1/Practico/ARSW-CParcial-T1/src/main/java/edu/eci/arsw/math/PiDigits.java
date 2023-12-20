package edu.eci.arsw.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An implementation of the Bailey-Borwein-Plouffe formula for calculating
 * hexadecimal digits of pi.
 * https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
 * *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
 */
public class PiDigits {

    private static Scanner sc = new Scanner(System.in);
    
    /**
     * Calcula los digitos de pi en un intervalo dado
     * @param start Inicio del intervalo
     * @param count Cantidad de digitos
     * @param N Cantidad de threads
     * @return Arreglo de bytes con los digitos de pi
     */
    public static byte[] getDigits(int start, int count, int N) {
        
        List<PiDigitsThread> threads = new ArrayList<>();
        Object lockObject = new Object();
        byte[] digits = new byte[count];
        int digitsPerThread = count / N;
        boolean anyThreadRunning = true;

        // Valida el intervalo
        isValidInterval(start, count); 

        // Crea los threads
        createThreads(start, N, threads, digitsPerThread, lockObject); 

        // Revisa si algun thread esta corriendo y detiene la ejecucion
        try {
            while (anyThreadRunning) {
                anyThreadRunning = false;
                for (PiDigitsThread thread : threads) {
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

        // Obtiene los resultados
        getResults(threads, digits, digitsPerThread);
        getTotalDigits(threads);
        return digits;
    }

    /**
     * Detiene y ejecuta los threads
     * @param threads Lista de threads
     * @param lockObject Objeto de sincronizacion
     * @throws InterruptedException
     * @author Daniel Santanilla
     * 
     */
    private static void stopAndExecute(List<PiDigitsThread> threads, Object lockObject) throws InterruptedException {
        for (PiDigitsThread thread : threads) {
            thread.setExecution(false);
        }
        System.out.println("==================== Stopping threads... ====================");
        getTotalDigits(threads);
        System.out.println("Press ENTER to continue...");
        sc.nextLine();
        System.out.println("==================== Resuming threads... ====================");
        for (PiDigitsThread thread : threads) {
            thread.setExecution(true);
            synchronized (lockObject) {
                lockObject.notifyAll();
            }
        }
    }

    /**
     * Imprime el total de digitos procesados
     * @param threads Lista de threads
     */
    private static void getTotalDigits(List<PiDigitsThread> threads) {
        int totalDigits = 0;
        for (PiDigitsThread thread : threads) {
            totalDigits += thread.getProcessedDigits();
        }
        System.out.println("Total of processed digits: " + totalDigits);
    }

    /**
     * Obtiene los resultados de los threads
     * @param threads Lista de threads
     * @param digits Arreglo de bytes
     * @param digitsPerThread Cantidad de digitos por thread
     */
    private static void getResults(List<PiDigitsThread> threads, byte[] digits, int digitsPerThread) {
        for (PiDigitsThread thread : threads) {
            try {
                thread.join();
                getThreadDigits(digits, digitsPerThread, thread);
            } catch (InterruptedException ex) {
                System.out.println("Thread interrupted");
            }
        }
    }

    /**
     * Crea los threads
     * @param start Inicio del intervalo
     * @param N Cantidad de threads
     * @param threads Lista de threads
     * @param digitsPerThread Cantidad de digitos por thread
     * @param lockObject Objeto de sincronizacion
     */
    private static void createThreads(int start, int N, List<PiDigitsThread> threads, int digitsPerThread, Object lockObject) {
        for (int i = 0; i < N; i++) {
            PiDigitsThread thread = new PiDigitsThread(start, digitsPerThread, i, lockObject);
            start += digitsPerThread; // Actualiza el inicio del intervalo
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Obtiene los digitos de un thread
     * @param digits Arreglo de bytes
     * @param digitsPerThread Cantidad de digitos por thread
     * @param thread Thread
     */
    private static void getThreadDigits(byte[] digits, int digitsPerThread, PiDigitsThread thread) {
        byte[] threadDigits = thread.getDigits();
        for (int i = 0; i < threadDigits.length; i++) {
            // Actualiza los digitos en el arreglo por los digitos del thread
            digits[i + thread.getThreadId() * digitsPerThread] = threadDigits[i];
        }
    }

    /**
     * Valida el intervalo
     * @param start Inicio del intervalo
     * @param count Cantidad de digitos
     */
    private static void isValidInterval(int start, int count) {
        if (start < 0 || count < 0) {
            throw new RuntimeException("Invalid Interval");
        }
    }

}
