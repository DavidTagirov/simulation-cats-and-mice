package org.simulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Renderer extends JPanel {
    private final World world;
    private final Map<String, BufferedImage> emojiMap = new HashMap<>();
    private final Map<Coordinates, BufferedImage> barrierIcons = new HashMap<>(); // Запоминаем иконки барьеров
    private int moveCount = 0;

    public Renderer(World world) {
        this.world = world;
        loadEmojis();
        cacheBarriers();
    }

    private void loadEmojis() {
        try {
            emojiMap.put("Cat", loadImage("/icons/cat.png"));
            emojiMap.put("Mouse", loadImage("/icons/mouse.png"));
            emojiMap.put("Cheese", loadImage("/icons/cheese.png"));
            emojiMap.put("Barrier1", loadImage("/icons/tree1.png"));
            emojiMap.put("Barrier2", loadImage("/icons/brick.png"));
            emojiMap.put("Barrier3", loadImage("/icons/tree2.png"));
            emojiMap.put("Floor", loadImage("/icons/floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось загрузить изображения");
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getResource(path));
    }

    public void createVisual() {
        JFrame frame = new JFrame("Симуляция 'Кошки-Мышки'");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(670, 750);
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
                Coordinates coords = new Coordinates(i, j);
                String nameOfEntity = world.getEntities().get(coords).getClass().getSimpleName();

                BufferedImage emoji = switch (nameOfEntity) {
                    case "Cat" -> emojiMap.get("Cat");
                    case "Mouse" -> emojiMap.get("Mouse");
                    case "Cheese" -> emojiMap.get("Cheese");
                    case "Barrier" -> barrierIcons.getOrDefault(coords, emojiMap.get("Barrier1"));
                    case "Floor" -> emojiMap.get("Floor");
                    default -> null;
                };

                if (emoji != null) {
                    g.drawImage(emoji, j * cellSize, i * cellSize, cellSize, cellSize, null);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Ход: " + moveCount, 20, world.getX() * cellSize + 40);
    }

    private void cacheBarriers() {
        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                Coordinates coords = new Coordinates(i, j);
                if (world.getEntities().get(coords).getClass().getSimpleName().equals("Barrier")) {
                    barrierIcons.put(coords, randomBarrierIcon());
                }
            }
        }
    }

    private BufferedImage randomBarrierIcon() {
        int randomIndex = (int) (Math.random() * 3);
        return switch (randomIndex) {
            case 0 -> emojiMap.get("Barrier1");
            case 1 -> emojiMap.get("Barrier2");
            default -> emojiMap.get("Barrier3");
        };
    }

    public void incrementMoveCount(int count) {
        moveCount = count;
        repaint();
    }
}
