package org.simulation;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Simulation {
    private boolean isPaused = false;
    private boolean isEscape = false;

    public void start() throws InterruptedException {
        int[] worldParams = getWorldParameters();
        int x = worldParams[0];
        int y = worldParams[1];
        int worldSpeed = worldParams[2];

        World world = new World();
        world.generate(x, y);

        Renderer renderer = new Renderer(world);
        renderer.createVisual();

        Action action = new Action();

        JFrame frame = renderer.getFrame();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isPaused = !isPaused;
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    isEscape = true;
                }
            }
        });

        int count = 1;
        while (true) {
            Thread.sleep(worldSpeed);
            if (isEscape) {
                JOptionPane.showMessageDialog(null, "Вы вышли из симуляции", "Оповещение", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                System.exit(0);
            }
            if (!isPaused) {
                action.turnAction(world);
                renderer.incrementMoveCount(count);
                if (count % 10 == 0) {
                    world.update();
                }
                count++;
            }
        }
    }

    private int[] getWorldParameters() {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();
        JTextField speedField = new JTextField();

        Object[] message = {
                "Введите размер мира (X):", xField,
                "Введите размер мира (Y):", yField,
                "Введите скорость мира (в мс):", speedField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Настройки мира", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                int speed = Integer.parseInt(speedField.getText());
                return new int[]{x, y, speed};
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Некорректный ввод, попробуйте снова", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return getWorldParameters();
            }
        } else {
            System.exit(0);
            return null;
        }
    }
}