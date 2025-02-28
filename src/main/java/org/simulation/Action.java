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
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                Coordinates coordinates = new Coordinates(i, j);
                var entity = world.getEntities().get(coordinates);

                if (entity instanceof Creature) {
                    Creature creature = (Creature) entity;
                    tasks.add(() -> {
                        synchronized (world) {
                            creature.hunting(world);
                        }
                    });
                }
            }
        }

        // Выполняем все задачи одновременно
        for (Runnable task : tasks) {
            executorService.submit(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

        // Обновляем отображение после завершения всех ходов
        renderer.incrementMoveCount();
    }
}
