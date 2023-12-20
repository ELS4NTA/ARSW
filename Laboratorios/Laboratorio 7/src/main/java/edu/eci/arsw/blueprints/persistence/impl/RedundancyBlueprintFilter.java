package edu.eci.arsw.blueprints.persistence.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintsFilter;

/**
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
@Component
public class RedundancyBlueprintFilter implements BlueprintsFilter {
    
    @Override
    public Blueprint filterBlueprint(Blueprint bp) {
        Blueprint filteredBp = new Blueprint(bp.getAuthor(), bp.getName());
        for (int i = 0; i < bp.getPoints().size() - 1; i++) {
            if (!isRedundant(bp.getPoints().get(i), bp.getPoints().get(i + 1))) {
                filteredBp.addPoint(bp.getPoints().get(i));
            }
        }
        filteredBp.addPoint(bp.getPoints().get(bp.getPoints().size() - 1));
        return filteredBp;
    }

    @Override
    public Set<Blueprint> filterBlueprints(Set<Blueprint> blueprints) {
        Set<Blueprint> filteredBlueprints = new HashSet<Blueprint>();
        for (Blueprint bp : blueprints) {
            filteredBlueprints.add(filterBlueprint(bp));
        }
        return filteredBlueprints;
    }

    public boolean isRedundant(Point p1, Point p2) {
        return p1.getX() == p2.getX() && p1.getY() == p2.getY();
    }

}
