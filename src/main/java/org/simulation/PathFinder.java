package org.simulation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simulation.entities.*;

import java.util.*;

@Getter
@AllArgsConstructor
public class PathFinder {
    private World world;

    public List<Coordinates> findTargets(String targetName) {
        List<Coordinates> list = new ArrayList<>();

        for (int i = 0; i < world.getX(); i++) {
            for (int j = 0; j < world.getY(); j++) {
                Coordinates coordinates = new Coordinates(i, j);
                var entity = world.getEntities().get(coordinates);

                if (entity != null && entity.getClass().getSimpleName().equals(targetName)) {
                    list.add(coordinates);
                }
            }
        }
        return list;
    }

    public Coordinates getNearestTarget(List<Coordinates> targets, Creature creature) {
        if (targets.isEmpty()) return null;

        Coordinates start = creature.getCoordinates();
        Coordinates nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Coordinates target : targets) {
            List<Coordinates> path = findPath(start, target, creature);

            if (!path.isEmpty() && path.size() < minDistance) {
                minDistance = path.size();
                nearest = target;
            }
        }

        return nearest;
    }

    public List<Coordinates> findPath(Coordinates start, Coordinates goal, Creature creature) {
        Queue<List<Coordinates>> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();

        queue.add(Collections.singletonList(start));
        visited.add(start);

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            List<Coordinates> path = queue.poll();
            Coordinates current = path.get(path.size() - 1);

            if (current.equals(goal)) {
                return path;
            }

            for (int[] dir : directions) {
                Coordinates next = new Coordinates(current.x() + dir[0], current.y() + dir[1]);

                if (isWalkable(next, creature) && !visited.contains(next)) {
                    visited.add(next);
                    List<Coordinates> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    queue.add(newPath);
                }
            }
        }

        return Collections.emptyList();
    }

    private boolean isWalkable(Coordinates coordinates, Creature creature) {
        if (coordinates.x() < 0 || coordinates.y() < 0 || coordinates.x() >= world.getX() || coordinates.y() >= world.getY()) {
            return false;
        }

        var entity = world.getEntities().get(coordinates);

        if (creature instanceof Cat) {
            return !(entity instanceof Barrier || entity instanceof Cheese || entity instanceof Cat);

        } else if (creature instanceof Mouse) {
            return !(entity instanceof Barrier || entity instanceof Cat || entity instanceof Mouse);
        } else return false;
    }
}
