package org.simulation.entities;

import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;
import org.simulation.PathFinder;
import org.simulation.World;

@SuperBuilder
public class Herbivore extends Creature {

    @Override
    public void hunting(World world) {
        PathFinder pathFinder = new PathFinder(world);
        Coordinates nearestHerbivore = pathFinder.getNearestTarget(pathFinder.findTargets("Cheese"), this);

        if (Math.abs(coordinates.x() - nearestHerbivore.x()) < Math.abs(coordinates.y() - nearestHerbivore.y())) {
            if (coordinates.y() < nearestHerbivore.y() && !world.getEntities().get(new Coordinates(coordinates.x(), coordinates.y() + 1)).getClass().getSimpleName().equals("Barrier")) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x(), coordinates.y() + speed);
                world.setEntities(coordinates, this);
            } else if (!world.getEntities().get(new Coordinates(coordinates.x(), coordinates.y() - 1)).getClass().getSimpleName().equals("Barrier")) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x(), coordinates.y() - speed);
                world.setEntities(coordinates, this);
            } else if (!world.getEntities().get(new Coordinates(coordinates.x() - 1, coordinates.y())).getClass().getSimpleName().equals("Barrier")) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x() - speed, coordinates.y());
                world.setEntities(coordinates, this);
            } else if (!world.getEntities().get(new Coordinates(coordinates.x() + 1, coordinates.y())).getClass().getSimpleName().equals("Barrier")) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x() + speed, coordinates.y());
                world.setEntities(coordinates, this);
            }
        } else {
            if (coordinates.x() < nearestHerbivore.x()
                    && !world.getEntities().get(new Coordinates(coordinates.x() + 1, coordinates.y())).getClass().getSimpleName().equals("Barrier")) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x() + speed, coordinates.y());
                world.setEntities(coordinates, this);
            } else if (!world.getEntities().get(new Coordinates(coordinates.x() - 1, coordinates.y())).getClass().getSimpleName().equals("Barrier")) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x() - speed, coordinates.y());
                world.setEntities(coordinates, this);
            } else if (!world.getEntities().get(new Coordinates(coordinates.x(), coordinates.y() + 1)).getClass().getSimpleName().equals("Barrier")) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x(), coordinates.y() + speed);
                world.setEntities(coordinates, this);
            } else if (!world.getEntities().get(new Coordinates(coordinates.x(), coordinates.y() - 1)).getClass().getSimpleName().equals("Barrier")) {
                world.setEntities(coordinates, Floor.builder().build());
                coordinates = new Coordinates(coordinates.x(), coordinates.y() - speed);
                world.setEntities(coordinates, this);
            }
        }
        if (coordinates.x() == nearestHerbivore.x() && coordinates.y() == nearestHerbivore.y()) {
            world.setEntities(coordinates, this);
            health = 100;
        }
    }
}


/*package org.simulation.entities;

import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;
import org.simulation.PathFinder;
import org.simulation.World;

@SuperBuilder
public class Herbivore extends Creature {
private World world;

    @Override
    public void hunting() {
        PathFinder pathFinder = new PathFinder(world);
        Coordinates nearestHerbivore = pathFinder.getNearestTarget(pathFinder.findTargets("Cheese"), this);

        boolean noBarrierAbove = !world.getEntities().get(new Coordinates(coordinates.x() - 1, coordinates.y())).getClass().getSimpleName().equals("Barrier");
        boolean noBarrierDown = !world.getEntities().get(new Coordinates(coordinates.x() + 1, coordinates.y())).getClass().getSimpleName().equals("Barrier");
        boolean noBarrierLeft = !world.getEntities().get(new Coordinates(coordinates.x(), coordinates.y() - 1)).getClass().getSimpleName().equals("Barrier");
        boolean noBarrierRight = !world.getEntities().get(new Coordinates(coordinates.x(), coordinates.y() + 1)).getClass().getSimpleName().equals("Barrier");

        boolean isYLonger = Math.abs(coordinates.x() - nearestHerbivore.x()) < Math.abs(coordinates.y() - nearestHerbivore.y());
        boolean caughtTarget = coordinates.x() == nearestHerbivore.x() && coordinates.y() == nearestHerbivore.y();
        boolean needGoRight = coordinates.y() < nearestHerbivore.y();
        boolean needGoDown = coordinates.x() < nearestHerbivore.x();

        if (isYLonger) {
            if (needGoRight && noBarrierRight) {
                makeMove(0, speed);
            } else if (noBarrierLeft) {
                makeMove(0, -speed);
            } else if (noBarrierAbove) {
                makeMove(-speed, 0);
            } else if (noBarrierDown) {
                makeMove(speed, 0);
            }
        } else {
            if (needGoDown && noBarrierDown) {
                makeMove(speed, 0);
            } else if (noBarrierAbove) {
                makeMove(-speed, 0);
            } else if (noBarrierRight) {
                makeMove(0, speed);
            } else if (noBarrierLeft) {
                makeMove(0, -speed);
            }
        }

        if (caughtTarget) {
            world.setEntities(coordinates, this);
            health = 100;
        }
    }

    @Override
    protected void makeMove(int speedX, int speedY) {
        world.setEntities(coordinates, Floor.builder().build());
        coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
        world.setEntities(coordinates, this);
    }
}*/