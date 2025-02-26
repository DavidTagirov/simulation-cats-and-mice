package org.simulation.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Predator extends Creature {
    private final Integer strange;

    @Override
    public void makeMove() {
        //охотится за зайцем, тут нужен алгоритм
        //нельзя пересекать границы карты, нельзя пересекать горы и деревья
        //выбирает ближайшего зайца и делает ход в его направлении, если он уже на клетке с зайцем, он его ест и восполняет здоровье
        Coordinates coordinatesOfHerbivore = chooseHerbivore();
        if (isAllowedMove(coordinatesOfHerbivore)) {

        }
    }

    private boolean isAllowedMove(Coordinates coordinatesOfHerbivore) {

        return false;
    }

    private Coordinates chooseHerbivore() {

        return null;
    }
}