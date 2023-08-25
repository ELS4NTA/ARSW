/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
public class Main {

    public static void main(String a[]) {
        HostBlackListsValidator hblv = new HostBlackListsValidator();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        // Hilos: 1
        // List<Integer> blackListOcurrences = hblv.checkHost("202.24.34.55", 1);
        // Hilos: Nucleos de procesamiento
        // List<Integer> blackListOcurrences = hblv.checkHost("202.24.34.55", availableProcessors);
        // Hilos: Doble Nucleos de procesamiento
        // List<Integer> blackListOcurrences = hblv.checkHost("202.24.34.55", availableProcessors * 2);
        // Hilos: 50
        // List<Integer> blackListOcurrences = hblv.checkHost("202.24.34.55", 50);
        // Hilos: 100
        List<Integer> blackListOcurrences = hblv.checkHost("202.24.34.55", 100);
        System.out.println("The host was found in the following blacklists:" + blackListOcurrences);
    }

}
