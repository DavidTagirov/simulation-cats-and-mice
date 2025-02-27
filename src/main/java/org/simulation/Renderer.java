package org.simulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Renderer extends JPanel {
    private final World world;
    private final Map<String, BufferedImage> emojiMap = new HashMap<>();

    public Renderer(World world) {
        this.world = world;
        loadEmojis();
    }

    private void loadEmojis() {
        try {
            emojiMap.put("Predator", loadImage("/icons/cat.png"));
            emojiMap.put("Herbivore", loadImage("/icons/mouse.png"));
            emojiMap.put("Cheese", loadImage("/icons/cheese.png"));
            emojiMap.put("Barrier1", loadImage("/icons/tree1.png"));
            emojiMap.put("Barrier2", loadImage("/icons/brick.png"));
            emojiMap.put("Barrier3", loadImage("/icons/tree2.png"));
            emojiMap.put("Floor", loadImage("/icons/floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось загрузить изображения эмодзи!");
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getResource(path));
    }

    public void createVisual() {
        JFrame frame = new JFrame("Симуляция 'Кошки-Мышки'");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(670, 700);
        frame.add(this);
        frame.setVisible(true);
        setBackground(new Color(168, 228, 160));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 65;

        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                String nameOfEntity = world.getEntities().get(new Coordinates(i, j)).getClass().getSimpleName();

                BufferedImage emoji = switch (nameOfEntity) {
                    case "Predator" -> emojiMap.get("Predator");
                    case "Herbivore" -> emojiMap.get("Herbivore");
                    case "Cheese" -> emojiMap.get("Cheese");
                    case "Barrier" -> randomBarrierIcon();
                    case "Floor" -> emojiMap.get("Floor");
                    default -> null;
                };

                if (emoji != null) {
                    g.drawImage(emoji, j * cellSize, i * cellSize, cellSize, cellSize, null);
                }
            }
        }
    }

    private BufferedImage randomBarrierIcon() {
        return switch (new Random().nextInt(3)) {
            case 0 -> emojiMap.get("Barrier1");
            case 1 -> emojiMap.get("Barrier2");
            default -> emojiMap.get("Barrier3");
        };
    }
}