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

                int nextInt = random.nextInt(100);
                if (nextInt <= 10) {
                    setEntities(coordinates, Cheese.builder().build());
                } else if (nextInt <= 20) {
                    setEntities(coordinates, Herbivore.builder()
                            .speed(1)
                            .health(100)
                            .build());
                } else if (nextInt <= 30) {
                    setEntities(coordinates, Predator.builder()
                            .speed(2)
                            .health(100)
                            .strange(50)
                            .build());
                } else if (nextInt <= 40) {
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

        setEntities(new Coordinates(5, 5), Predator.builder()
                .speed(1)
                .health(100)
                .strange(50)
                .build());

        setEntities(new Coordinates(0, 0), Herbivore.builder()
                .speed(1)
                .health(100)
                .build());
        setEntities(new Coordinates(9, 9), Herbivore.builder()
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
}