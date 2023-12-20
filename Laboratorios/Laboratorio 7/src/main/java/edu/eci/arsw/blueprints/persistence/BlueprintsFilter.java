package edu.eci.arsw.blueprints.persistence;

import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 * Interface to filter blueprints
 * 
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
public interface BlueprintsFilter {
    
    /**
     * Filter the given blueprint
     * 
     * @param bp the blueprint to filter
     * @return the blueprint filtered
     */
    public Blueprint filterBlueprint(Blueprint bp);

    /**
     * Filter the given blueprints
     * 
     * @param blueprints the blueprints to filter
     * @return the blueprints filtered
     */
    public Set<Blueprint> filterBlueprints(Set<Blueprint> blueprints);

}
