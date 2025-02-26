package org.simulation;

public class Simulation {

    public void start(){
        World world = new World();
        world.setupRandomPositions(3, 10);

        Renderer renderer = new Renderer();
        renderer.createVisual(world);

        System.out.println(world.getEntities());
        System.out.println(world.getEntities().size());

    }
}
