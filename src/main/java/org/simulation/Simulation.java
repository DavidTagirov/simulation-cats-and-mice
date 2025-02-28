package org.simulation;

public class Simulation {

    public void start() throws InterruptedException {
        World world = new World();
        //world.generate(10, 10);
         world.testGenerate(10, 10);
        //world.testGenerate2(10, 10);

        Renderer renderer = new Renderer(world);
        renderer.createVisual();

        Action action = new Action();

        int count = 1;
        while (true) {
            Thread.sleep(800);
            action.turnAction(world);
            renderer.incrementMoveCount(count);
            if(count % 10 == 0) {
                world.update();
            }
            count++;
        }
    }
}

/*
сделать меню: выбор скорости, размер мира, пауза, следующий шаг, продолжить
изменение картинок при низком хп

*/