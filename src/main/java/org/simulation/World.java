package org.simulation;

import lombok.Getter;
import org.simulation.entities.*;

import java.util.HashMap;
import java.util.Random;

@Getter
public class World {
    private final HashMap<Coordinates, Entity> entities = new HashMap<>();
    private int x;
    private int y;

    public void setEntities(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public void generate(int x, int y) {
        Random random = new Random();
        Coordinates coordinates;
        this.x = x;
        this.y = y;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                coordinates = new Coordinates(i, j);

                int randomNumber = random.nextInt(100);
                if (randomNumber <= 3) {
                    setEntities(coordinates, Cheese.builder().build());
                } else if (randomNumber <= 6) {
                    setEntities(coordinates, Mouse.builder()
                            .speed(1)
                            .health(100)
                            .build());
                } else if (randomNumber <= 9) {
                    setEntities(coordinates, Cat.builder()
                            .speed(2)
                            .health(100)
                            .strange(50)
                            .build());
                } else if (randomNumber <= 19) {
                    setEntities(coordinates, Barrier.builder().build());
                } else {
                    setEntities(coordinates, Floor.builder().build());
                }
            }
        }
    }

    public void testGenerate(int x, int y) {
        Random random = new Random();
        Coordinates coordinates;
        this.x = x;
        this.y = y;

        setEntities(new Coordinates(5, 5), Cat.builder()
                .speed(1)
                .health(100)
                .strange(50)
                .build());

        setEntities(new Coordinates(0, 0), Mouse.builder()
                .speed(1)
                .health(100)
                .build());
        setEntities(new Coordinates(9, 9), Mouse.builder()
                .speed(1)
                .health(100)
                .build());

        setEntities(new Coordinates(3, 5), Cheese.builder().build());
        setEntities(new Coordinates(4, 5), Barrier.builder().build());
        setEntities(new Coordinates(7, 7), Cheese.builder().build());
        setEntities(new Coordinates(6, 5), Barrier.builder().build());
        setEntities(new Coordinates(7, 5), Barrier.builder().build());

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                coordinates = new Coordinates(i, j);

                if ((i == 0 && j == 0) || (i == 7 && j == 7) || (i == 9 && j == 9)
                        || (i == 3 && j == 5) || (i == 4 && j == 5) || (i == 5 && j == 5) || (i == 6 && j == 5) || (i == 7 && j == 5))
                    continue;

                setEntities(coordinates, Floor.builder().build());

            }
        }
    }

    public void testGenerate2(int x, int y) {
        Random random = new Random();
        Coordinates coordinates;
        this.x = x;
        this.y = y;

        setEntities(new Coordinates(7, 7), Cat.builder()
                .speed(1)
                .health(100)
                .strange(50)
                .build());

        setEntities(new Coordinates(0, 0), Mouse.builder()
                .speed(1)
                .health(100)
                .build());
        setEntities(new Coordinates(4, 3), Cheese.builder().build());

        setEntities(new Coordinates(3, 5), Barrier.builder().build());
        setEntities(new Coordinates(4, 5), Barrier.builder().build());
        setEntities(new Coordinates(5, 5), Barrier.builder().build());
        setEntities(new Coordinates(6, 5), Barrier.builder().build());
        setEntities(new Coordinates(7, 5), Barrier.builder().build());

        setEntities(new Coordinates(3, 4), Barrier.builder().build());
        setEntities(new Coordinates(3, 3), Barrier.builder().build());
        setEntities(new Coordinates(3, 2), Barrier.builder().build());

        setEntities(new Coordinates(6, 2), Barrier.builder().build());
        setEntities(new Coordinates(4, 2), Barrier.builder().build());
        setEntities(new Coordinates(5, 2), Barrier.builder().build());

        setEntities(new Coordinates(5, 3), Barrier.builder().build());

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                coordinates = new Coordinates(i, j);

                if ((i == 0 && j == 0) || (i == 7 && j == 7) || (i == 4 && j == 3)
                        || (i == 3 && j == 5) || (i == 4 && j == 5) || (i == 5 && j == 5) || (i == 6 && j == 5) || (i == 7 && j == 5)
                        || (i == 3 && j == 4) || (i == 3 && j == 3) || (i == 3 && j == 2)
                        || (i == 6 && j == 2) || (i == 4 && j == 2) || (i == 5 && j == 2)
                        || (i == 5 && j == 3))
                    continue;

                setEntities(coordinates, Floor.builder().build());

            }
        }
    }

    public void update() {
        Random random = new Random();
        Coordinates coordinates;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                coordinates = new Coordinates(i, j);

                int randomNumber = random.nextInt(100);
                if (getEntities().get(coordinates) instanceof Floor && randomNumber <= 3) {
                    setEntities(coordinates, Cheese.builder().build());
                } else if (getEntities().get(coordinates) instanceof Floor && randomNumber <= 6) {
                    setEntities(coordinates, Mouse.builder()
                            .speed(1)
                            .health(100)
                            .build());
                } else if (getEntities().get(coordinates) instanceof Floor && randomNumber <= 8) {
                    setEntities(coordinates, Cat.builder()
                            .speed(1)
                            .health(100)
                            .strange(50)
                            .build());
                }
            }
        }
    }
}