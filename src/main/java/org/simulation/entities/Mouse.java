package org.simulation.entities;

import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;
import org.simulation.PathFinder;
import org.simulation.World;

import java.util.List;

@SuperBuilder
public class Mouse extends Creature {

    @Override
    public void hunting(World world) {
        if (health <= 0) {
            System.out.println("Мышь была съедена");
            return;
        }

        PathFinder pathFinder = new PathFinder(world);
        Coordinates nearestCheese = pathFinder.getNearestTarget(pathFinder.findTargets("Cheese"), this);

        if (nearestCheese == null) {
            System.out.println("Сыр не найден — мышь не двигается");
            return;
        }

        List<Coordinates> path = pathFinder.findPath(coordinates, nearestCheese, this);

        if (path.isEmpty()) {
            System.out.println("Путь к сыру заблокирован");
            return;
        }

        for (int i = 1; i <= Math.min(speed, path.size() - 1); i++) {
            Coordinates nextPosition = path.get(i);
            makeMove(world, nextPosition);
        }
    }

    @Override
    protected void makeMove(World world, Coordinates newPosition) {
        var nextEntity = world.getEntities().get(newPosition);

        if (nextEntity instanceof Floor) {
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = newPosition;
            world.setEntities(coordinates, this);
        }

        if (nextEntity instanceof Cheese) {
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = newPosition;
            world.setEntities(coordinates, this);
            health = 100;
            System.out.println("Мышь съела сыр");
        }
    }
}