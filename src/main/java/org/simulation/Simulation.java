package org.simulation;

public class Simulation {

    public void start() throws InterruptedException {
        World world = new World();
        //world.generate(10, 10);
        world.testGenerate(10, 10);

        Renderer renderer = new Renderer(world);
        renderer.createVisual();

        Action action = new Action(world);

        while (true) {
            //Thread.sleep(1000);
            action.turnAction(renderer);
            //renderer.repaint();
        }
    }
}
