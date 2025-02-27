package org.simulation;

import lombok.Data;
import org.simulation.entities.Creature;

@Data
public class Action {
    private World world;

    public Action(World world) {
        this.world = world;
    }

    public void turnAction(Renderer renderer) throws InterruptedException {
        Coordinates coordinates;

        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                coordinates = new Coordinates(i, j);
                String entityName = world.getEntities().get(coordinates).getClass().getSimpleName();

                if (entityName.equals("Herbivore")) {
                    Creature mouse = (Creature) world.getEntities().get(coordinates);
                    mouse.hunting(world);
                    renderer.repaint();
                    Thread.sleep(400);
                    renderer.repaint();
                } /*else if (getWorld().isPositionPredator(coordinates)) {
                    Creature cat = (Creature) world.getEntities().get(coordinates);
                    cat.makeMove(world);
                }*/

            }
        }
    }
}
