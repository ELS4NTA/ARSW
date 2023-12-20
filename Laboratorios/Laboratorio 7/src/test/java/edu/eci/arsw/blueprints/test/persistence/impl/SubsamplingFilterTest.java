package edu.eci.arsw.blueprints.test.persistence.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.SubsamplingBlueprintFilter;

/**
 * 
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
public class SubsamplingFilterTest {
    
    @Test
    public void shouldSubsamplingBlueprintEvenPoints() {
        SubsamplingBlueprintFilter rft = new SubsamplingBlueprintFilter();
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10), new Point(20, 20), new Point(30, 30)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        Blueprint filteredBp = rft.filterBlueprint(bp);
        assertEquals(2, filteredBp.getPoints().size());
    }

    @Test
    public void shouldSubsamplingBlueprintOddPoints() {
        SubsamplingBlueprintFilter rft = new SubsamplingBlueprintFilter();
        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10), new Point(20, 20)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);
        Blueprint filteredBp = rft.filterBlueprint(bp);
        assertEquals(2, filteredBp.getPoints().size());
    }

}
