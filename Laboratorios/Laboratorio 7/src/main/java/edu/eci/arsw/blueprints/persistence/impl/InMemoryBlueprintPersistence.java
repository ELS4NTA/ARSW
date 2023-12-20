/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */
@Component
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        // load stub data
        Point[] pts = new Point[] { new Point(140, 140), new Point(115, 115) };
        Blueprint bp = new Blueprint("_authorname_", "_bpname_ ", pts);

        Point[] pts1 = new Point[] { new Point(240, 240), new Point(145, 145) };
        Blueprint bp1 = new Blueprint("angie", "bpname1", pts1);

        Point[] ptspes = new Point[] { new Point(368, 212), new Point(208, 52), new Point(64, 212), new Point(368, 212), new Point(368, 404), new Point(64, 404), new Point(64, 112)};
        Blueprint bpmn = new Blueprint("angie", "casa", ptspes);

        Point[] pts2 = new Point[] { new Point(300, 300), new Point(400, 0), new Point(0, 200), new Point(500, 200), new Point(100, 0) , new Point(300, 300)};
        Blueprint bp2 = new Blueprint("angie", "estrella", pts2);

        Point[] pts3 = new Point[] { new Point(440, 440), new Point(125, 125) };
        Blueprint bp3 = new Blueprint("daniel", "bpname3", pts3);

        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bpmn.getAuthor(), bpmn.getName()), bpmn);

    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException(BlueprintPersistenceException.ALREADY_EXISTS + bp);
        } else {
            blueprints.putIfAbsent(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public void updateBlueprint(Blueprint newBp, String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint actualBp = getBlueprint(author, bprintname);
        if (actualBp != null) {
            actualBp.setPoints(newBp.getPoints());
        } else {
            throw new BlueprintNotFoundException(BlueprintNotFoundException.NOT_EXIST + author + bprintname);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint blueprint = blueprints.get(new Tuple<>(author, bprintname));
        if (blueprint == null) {
            throw new BlueprintNotFoundException(BlueprintNotFoundException.NOT_EXIST + author + bprintname);
        }
        return blueprint;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> allBlueprints = new HashSet<Blueprint>();
        blueprints.forEach((key, value) -> allBlueprints.add(value));
        if (allBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException(BlueprintNotFoundException.NO_BLUEPRINTS);
        }
        return allBlueprints;
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintsByAuthor = new HashSet<Blueprint>();
        blueprints.forEach((key, value) -> {
            if (key.getElem1().equals(author)) {
                blueprintsByAuthor.add(value);
            }
        });
        if (blueprintsByAuthor.isEmpty()) {
            throw new BlueprintNotFoundException(BlueprintNotFoundException.NO_BLUEPRINTS_BY_AUTHOR);
        }
        return blueprintsByAuthor;
    }

    @Override
    public void deleteBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint blueprint = blueprints.get(new Tuple<>(author, bprintname));
        if (blueprint == null) {
            throw new BlueprintNotFoundException(BlueprintNotFoundException.NOT_FOUND + author + bprintname);
        }
        blueprints.remove(new Tuple<>(author, bprintname));
    }

}
