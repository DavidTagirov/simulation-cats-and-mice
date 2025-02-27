package org.simulation.entities;

import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;
import org.simulation.PathFinder;
import org.simulation.World;

import java.util.Optional;

@SuperBuilder
public class Herbivore extends Creature {
    private boolean isPredator;
    private boolean isHerbivore;

    @Override
    public void hunting(World world) {
        PathFinder pathFinder = new PathFinder(world);
        Coordinates nearestCheese = pathFinder.getNearestTarget(pathFinder.findTargets("Cheese"), this);

        boolean barrierAbove = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x() - 1, coordinates.y())))
                .map(entity -> entity.getClass().getSimpleName().equals("Barrier"))
                .orElse(false);
        boolean barrierDown = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x() + 1, coordinates.y())))
                .map(entity -> entity.getClass().getSimpleName().equals("Barrier"))
                .orElse(false);
        boolean barrierLeft = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x(), coordinates.y() - 1)))
                .map(entity -> entity.getClass().getSimpleName().equals("Barrier"))
                .orElse(false);
        boolean barrierRight = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x(), coordinates.y() + 1)))
                .map(entity -> entity.getClass().getSimpleName().equals("Barrier"))
                .orElse(false);

        boolean isYLonger = Math.abs(coordinates.x() - nearestCheese.x()) < Math.abs(coordinates.y() - nearestCheese.y());
        boolean caughtTarget = coordinates.x() == nearestCheese.x() && coordinates.y() == nearestCheese.y();
        boolean needGoRight = coordinates.y() < nearestCheese.y();
        boolean needGoDown = coordinates.x() < nearestCheese.x();

        for (int i = 0; i < speed; i++) {
            if (isYLonger) {
                if (needGoRight && !barrierRight) {
                    makeMove(world, 0, 1);
                } else if (!barrierLeft) {
                    makeMove(world, 0, -1);
                } else if (!barrierAbove) {
                    makeMove(world, -1, 0);
                } else if (!barrierDown) {
                    makeMove(world, 1, 0);
                }
            } else {
                if (needGoDown && !barrierDown) {
                    makeMove(world, 1, 0);
                } else if (!barrierAbove) {
                    makeMove(world, -1, 0);
                } else if (!barrierRight) {
                    makeMove(world, 0, 1);
                } else if (!barrierLeft) {
                    makeMove(world, 0, -1);
                }
            }

            if (caughtTarget) {
                world.setEntities(coordinates, this);
                health = 100;
            }
        }
    }

    @Override
    protected void makeMove(World world, int speedX, int speedY) {
        boolean isNextHerbivore = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY)))
                .map(entity -> entity.getClass().getSimpleName().equals("Herbivore"))
                .orElse(false);
        boolean isNextPredator = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY)))
                .map(entity -> entity.getClass().getSimpleName().equals("Predator"))
                .orElse(false);

        if (isHerbivore) {
            world.setEntities((coordinates), Herbivore.builder()
                    .speed(1)
                    .health(100)
                    .build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
            isHerbivore = false;
        } else if (isNextHerbivore) {
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
            isHerbivore = true;
        } else if (isPredator) {
            world.setEntities((coordinates), Predator.builder()
                    .speed(1)
                    .health(100)
                    .strange(50)
                    .build());
            isPredator = false;
        } else if (isNextPredator) {
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
            isPredator = true;
        } else {
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
        }
    }
}


/*else if (isPredator) {
            world.setEntities((coordinates), Predator.builder()
                    .speed(predator.speed)
                    .health(predator.health)
                    .strange(predator.getStrange())
                    .build());
            health = health - predator.getStrange();
            if (health.equals(0)) {
                System.out.println("Мышь была съедена");
            } else {
                coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
                world.setEntities(coordinates, this);
            }
            isPredator = false;
        } else if (isNextPredator) {
            predator = (Predator) world.getEntities().get(new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY));
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
            isPredator = true;
        } */