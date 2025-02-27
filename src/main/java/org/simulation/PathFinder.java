package org.simulation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simulation.entities.Creature;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PathFinder {
    private World world;

    public List<Coordinates> findTargets(String targetName) {
        List<Coordinates> list = new ArrayList<>();
        Coordinates coordinates;


        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                coordinates = new Coordinates(i, j);

                if (world.getEntities().get(coordinates).getClass().getSimpleName().equals(targetName)) {
                    list.add(coordinates);
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Цели закончились, игра завершена");
            System.exit(0);
        }
        return list;
    }

    public Coordinates getNearestTarget(List<Coordinates> list, Creature creature) {
        int x = creature.getCoordinates().x();
        int y = creature.getCoordinates().y();

        Coordinates nearestCoordinates = list.getFirst();

        for (Coordinates coordinates : list) {
            if (Math.abs(coordinates.x() + coordinates.y() - (x + y))
                    < Math.abs(nearestCoordinates.x() + nearestCoordinates.y() - (x + y))) {
                nearestCoordinates = coordinates;
            }
        }
        return nearestCoordinates;
    }
}
