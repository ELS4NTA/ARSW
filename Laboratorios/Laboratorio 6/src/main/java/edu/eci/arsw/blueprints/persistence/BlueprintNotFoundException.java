/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

/**
 *
 * @author hcadavid
 */
public class BlueprintNotFoundException extends Exception {

    public static final String NO_BLUEPRINTS = "There are no blueprints";
    public static final String NO_BLUEPRINTS_BY_AUTHOR = "There are no blueprints by this author";
    public static final String NOT_EXIST = "The given blueprint does not exist: ";

    public BlueprintNotFoundException(String message) {
        super(message);
    }

    public BlueprintNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
