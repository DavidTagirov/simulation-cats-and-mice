package org.simulation.entities;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;
import org.simulation.PathFinder;
import org.simulation.World;

import java.util.List;

@Getter
@SuperBuilder
public class Cat extends Creature {
    private final Integer strange;

    @Override
    public void hunting(World world) {
        PathFinder pathFinder = new PathFinder(world);
        Coordinates nearestMouse = pathFinder.getNearestTarget(pathFinder.findTargets("Mouse"), this);

        if (nearestMouse == null) {
            System.out.println("Мышь не найдена — кот не двигается");
            return;
        }

        List<Coordinates> path = pathFinder.findPath(coordinates, nearestMouse, this);

        if (path.isEmpty()) {
            System.out.println("Путь к мышке заблокирован");
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

        this.health -= 10;

        if (this.health == 0) {
            System.out.println("Кот умер от голода");
            world.setEntities(coordinates, Floor.builder().build());
        } else if (nextEntity instanceof Mouse mouse) {
            this.health = 100;
            mouse.health -= strange;
            System.out.println("Кот поел");

            if (mouse.health < strange) {
                mouse.health = 0;
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = newPosition;
                world.setEntities(coordinates, this);
            }
        } else if (nextEntity instanceof Floor) {
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = newPosition;
            world.setEntities(coordinates, this);
        }
    }
}
