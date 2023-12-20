/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 *
 * @author hcadavid
 */
public interface BlueprintsPersistence {
    
    /**
     * 
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     *    or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;
    
    /**
     * Update the given blueprint
     * 
     * @param newBp the new blueprint
     * @param author blueprint's author
     * @param bprintname blueprint's name
     * @author Angie Mojica
     * @author Daniel Santanilla
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public void updateBlueprint(Blueprint newBp, String author, String bprintname) throws BlueprintNotFoundException;

    /**
     * 
     * @param author blueprint's author
     * @param bprintname blueprint's author
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String bprintname) throws BlueprintNotFoundException;

    /**
     * Get all the blueprints saved in the system
     * 
     * @return the set of all blueprints
     * @throws BlueprintNotFoundException if there is no such blueprint
     * @author Angie Mojica
     * @author Daniel Santanilla
     */
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException;

    /** 
     * Get all the blueprints saved in the system by author
     * 
     * @param author blueprint's author
     * @return the set of blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     * @author Angie Mojica
     * @author Daniel Santanilla
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;
    
}
