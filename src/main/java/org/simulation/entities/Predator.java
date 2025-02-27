package org.simulation.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;
import org.simulation.PathFinder;
import org.simulation.World;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Predator extends Creature {
    private final Integer strange;
    //private World world;

    @Override
    public void hunting(World world) {
        PathFinder pathFinder = new PathFinder(world);
        Coordinates nearestHerbivore = pathFinder.getNearestTarget(pathFinder.findTargets("Herbivore"), this);

        if (Math.abs(coordinates.x() - nearestHerbivore.x()) < Math.abs(coordinates.y() - nearestHerbivore.y())) {
            if (coordinates.y() < nearestHerbivore.y()) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x(), coordinates.y() + speed);
                world.setEntities(coordinates, this);
            } else {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x(), coordinates.y() - speed);
                world.setEntities(coordinates, this);
            }
        } else if (Math.abs(coordinates.x() - nearestHerbivore.x()) > Math.abs(coordinates.y() - nearestHerbivore.y())) {
            if (coordinates.x() < nearestHerbivore.x()) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x() + speed, coordinates.y());
                world.setEntities(coordinates, this);
            } else {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x() - speed, coordinates.y());
                world.setEntities(coordinates, this);
            }
        } else {
            world.setEntities(coordinates, this);
            health = 100;
        }
    }

    /*@Override
    protected void makeMove(int speedX, int speedY) {
        world.setEntities(coordinates, Floor.builder().build());
        coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
        world.setEntities(coordinates, this);
    }*/
}