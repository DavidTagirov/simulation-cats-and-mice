package org.simulation.entities;

import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;
import org.simulation.PathFinder;
import org.simulation.World;

import java.util.Optional;

@SuperBuilder
public class Predator extends Creature {
    private final Integer strange;
    private boolean isCheese;
    private boolean isPredator;

    @Override
    public void hunting(World world) {
        PathFinder pathFinder = new PathFinder(world);
        Coordinates nearestHerbivore = pathFinder.getNearestTarget(pathFinder.findTargets("Herbivore"), this);

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

        boolean isYLonger = Math.abs(coordinates.x() - nearestHerbivore.x()) < Math.abs(coordinates.y() - nearestHerbivore.y());
        boolean caughtTarget = coordinates.x() == nearestHerbivore.x() && coordinates.y() == nearestHerbivore.y();
        boolean needGoRight = coordinates.y() < nearestHerbivore.y();
        boolean needGoDown = coordinates.x() < nearestHerbivore.x();

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
        boolean isNextCheese = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY)))
                .map(entity -> entity.getClass().getSimpleName().equals("Cheese"))
                .orElse(false);
        boolean isNextPredator = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY)))
                .map(entity -> entity.getClass().getSimpleName().equals("Predator"))
                .orElse(false);

        if (isCheese) {
            world.setEntities((coordinates), Cheese.builder().build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
            isCheese = false;
        } else if (isNextCheese) {
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
            isCheese = true;
        } else if (isPredator) {
            world.setEntities((coordinates), Predator.builder()
                    .speed(1)
                    .health(100)
                    .strange(50)
                    .build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
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

/*
boolean isNextHerbivore = Optional
                .ofNullable(world.getEntities().get(new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY)))
                .map(entity -> entity.getClass().getSimpleName().equals("Herbivore"))
                .orElse(false);

else if (isHerbivore) {
            world.setEntities((coordinates), Herbivore.builder()
                    .speed(herbivore.speed)
                    .health(herbivore.health - strange)
                    .build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
            isHerbivore = false;
            if (herbivore.health == 50) System.out.println("Получилось");
        } else if (isNextHerbivore) {
            herbivore = (Herbivore) world.getEntities().get(new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY));
            world.setEntities(coordinates, Floor.builder().build());
            coordinates = new Coordinates(coordinates.x() + speedX, coordinates.y() + speedY);
            world.setEntities(coordinates, this);
            isHerbivore = true;
        }*/