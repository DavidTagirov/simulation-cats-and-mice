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

    public void setupRandomPositions(int x, int y) {
        Random random = new Random();
        this.x = x;
        this.y = y;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int nextInt = random.nextInt(100);

                if (nextInt == 0) {
                    setEntities(new Coordinates(i, j), Predator.builder()
                            .strange(50)
                            .speed(2)
                            .health(100)
                            .build());
                } else if (nextInt == 1) {
                    setEntities(new Coordinates(i, j), Herbivore.builder()
                            .speed(1)
                            .health(100)
                            .build());
                } else if (nextInt <= 12) {
                    setEntities(new Coordinates(i, j), Cheese.builder().build());
                } else if (nextInt <= 40) {
                    setEntities(new Coordinates(i, j), Barrier.builder().build());
                } else {
                    setEntities(new Coordinates(i, j), Floor.builder().build());
                }
            }
        }
    }
}