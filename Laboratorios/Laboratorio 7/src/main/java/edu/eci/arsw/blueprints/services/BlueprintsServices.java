/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 * @author Angie Mojica
 * @author Daniel Santanilla
 */
@Service
public class BlueprintsServices {

    @Autowired
    BlueprintsPersistence bpp;

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    /**
     * 
     * @param bp blueprint to update
     * @param author blueprint's author
     * @param name blueprint's name
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public void updateBlueprint(Blueprint bp, String author, String name) throws BlueprintNotFoundException {
        bpp.updateBlueprint(bp, author, name);
    }

    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        return bpp.getAllBlueprints();
    }

    /**
     * 
     * @param author blueprint's author
     * @param name   blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author);
    }

    /**
     * 
     * @param author blueprint's author
     * @param name   blueprint's name
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public void deleteBlueprint(String author, String name) throws BlueprintNotFoundException {
        bpp.deleteBlueprint(author, name);
    }

}
