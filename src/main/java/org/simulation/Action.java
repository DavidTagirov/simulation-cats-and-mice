package org.simulation;

import lombok.Data;
import org.simulation.entities.Creature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Data
public class Action {

    public void turnAction(World world, Renderer renderer) throws InterruptedException {
        Coordinates coordinates;

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                coordinates = new Coordinates(i, j);
                String entityName = world.getEntities().get(coordinates).getClass().getSimpleName();

                if (entityName.equals("Herbivore")) {
                    Creature mouse = (Creature) world.getEntities().get(coordinates);
                    tasks.add(() -> {
                        mouse.hunting(world);
                        try {
                            Thread.sleep(800);
                            renderer.repaint();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                } else if (entityName.equals("Predator")) {
                    Creature cat = (Creature) world.getEntities().get(coordinates);
                    tasks.add(() -> {
                        cat.hunting(world);
                        try {
                            Thread.sleep(800);
                            renderer.repaint();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }
            }
        }

        for (Runnable task : tasks) {
            executorService.submit(task);
            try {
                Thread.sleep(800);
                renderer.repaint();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}


/*
package org.simulation;

import lombok.Data;
import org.simulation.entities.Creature;

@Data
public class Action {

    public void turnAction(World world, Renderer renderer) throws InterruptedException {
        Coordinates coordinates;

        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                coordinates = new Coordinates(i, j);
                String entityName = world.getEntities().get(coordinates).getClass().getSimpleName();

                if (entityName.equals("Herbivore")) {
                    Creature mouse = (Creature) world.getEntities().get(coordinates);
                    mouse.hunting(world);

                    Thread.sleep(300);
                    renderer.repaint();
                } else if (entityName.equals("Predator")) {
                    Creature cat = (Creature) world.getEntities().get(coordinates);
                    cat.hunting(world);

                    Thread.sleep(300);
                    renderer.repaint();
                }

            }
        }
    }
}
*/
