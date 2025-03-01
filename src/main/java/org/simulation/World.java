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