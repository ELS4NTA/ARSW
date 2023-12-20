package edu.eci.arsw.blueprints.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintsFilter;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
public class Main {

    
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
        BlueprintsFilter bpf = ac.getBean(BlueprintsFilter.class);
        
        // Prueba 1
        Blueprint bp = new Blueprint("Angie", "plano1");
        try {
            System.out.println("==== Prueba 1 ====");
            bps.addNewBlueprint(bp);
            System.out.println(bps.getBlueprint("Angie", "plano1"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Prueba 2
        Blueprint bp2 = new Blueprint("Angie", "plano2");
        try {
            System.out.println("==== Prueba 2 ====");
            bps.addNewBlueprint(bp2);
            System.out.println(bps.getBlueprintsByAuthor("Angie"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Prueba 3
        Blueprint bp3 = new Blueprint("Daniel", "plano3");
        bp3.addPoint(new Point(1, 1));
        bp3.addPoint(new Point(2, 2));
        bp3.addPoint(new Point(3, 3));
        try {
            System.out.println("==== Prueba 3 ====");
            bps.addNewBlueprint(bp3);
            System.out.println(bps.getAllBlueprints());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Prueba 4 - Filtrados
        Blueprint bp4 = new Blueprint("Daniel", "plano4");
        bp4.addPoint(new Point(1, 1));
        bp4.addPoint(new Point(1, 1));
        bp4.addPoint(new Point(2, 2));
        bp4.addPoint(new Point(2, 2));
        bp4.addPoint(new Point(3, 3));
        bp4.addPoint(new Point(4, 4));
        try {
            System.out.println("==== Prueba 3 - Filtros ====");
            System.out.println("~~ BEFORE ~~");
            System.out.println(bp4.getPoints());
            bps.addNewBlueprint(bp4);
            System.out.println("~~ AFTER ~~");
            System.out.println(bpf.filterBlueprint(bp4).getPoints());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        

    }
    
}
