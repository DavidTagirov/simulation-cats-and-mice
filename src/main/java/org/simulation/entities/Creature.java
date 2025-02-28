package org.simulation.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;
import org.simulation.World;

@Getter
@SuperBuilder
public abstract class Creature extends Entity {
    protected Integer speed;
    protected Integer health;

    abstract public void hunting(World world);

    abstract protected void makeMove(World world, Coordinates newPosition);
}
