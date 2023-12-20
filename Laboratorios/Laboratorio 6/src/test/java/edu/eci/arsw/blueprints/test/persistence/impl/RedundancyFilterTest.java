package edu.eci.arsw.blueprints.test.persistence.impl;

import org.junit.Test;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.RedundancyBlueprintFilter;

import static org.junit.Assert.*;

/**
 * 
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
public class RedundancyFilterTest {
    
    @Test
    public void shouldDeleteBlueprintRedundancies() {
        RedundancyBlueprintFilter rft = new RedundancyBlueprintFilter();
        Point[] pts = new Point[]{new Point(0, 0), new Point(0, 0), new Point(10, 10), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        Blueprint filteredBp = rft.filterBlueprint(bp);
        assertEquals(2, filteredBp.getPoints().size());
    }

    @Test
    public void shouldNotDeleteBlueprintRedundancies() {
        RedundancyBlueprintFilter rft = new RedundancyBlueprintFilter();
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10), new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        Blueprint filteredBp = rft.filterBlueprint(bp);
        assertEquals(4, filteredBp.getPoints().size());
    }

    
}
