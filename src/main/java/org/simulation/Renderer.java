package org.simulation;

import java.util.Random;

public class Renderer {
    private static final String ANSI_BACKGROUND = "\u001B[47m";
    private static final String ANSI_RESET = "\u001B[0m";

    public void createVisual(World world) {
        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                String nameOfEntity = world.getEntities().get(new Coordinates(i, j)).getClass().getSimpleName();

                switch (nameOfEntity) {
                    case "Predator" -> System.out.print(ANSI_BACKGROUND + "\uD83D\uDC08\uFE0F" + ANSI_RESET);
                    case "Herbivore" -> System.out.print(ANSI_BACKGROUND + "\uD83D\uDC00" + ANSI_RESET);
                    case "Cheese" -> System.out.print(ANSI_BACKGROUND + "\uD83E\uDDC0" + ANSI_RESET);
                    case "Barrier" -> {
                        switch (new Random().nextInt(5)) {
                            case 0 -> System.out.print(ANSI_BACKGROUND + "\uD83D\uDDD1\uFE0F" + ANSI_RESET);
                            case 1 -> System.out.print(ANSI_BACKGROUND + "\uD83E\uDEA4" + ANSI_RESET);
                            case 2 -> System.out.print(ANSI_BACKGROUND + "\uD83E\uDE91" + ANSI_RESET);
                            case 3 -> System.out.print(ANSI_BACKGROUND + "⚱\uFE0F" + ANSI_RESET);
                            case 4 -> System.out.print(ANSI_BACKGROUND + "\uD83E\uDDF0" + ANSI_RESET);
                        }
                    }
                    case "Floor" -> System.out.print(ANSI_BACKGROUND + "\uD83C\uDFFB\u001B[47m" + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }
}
