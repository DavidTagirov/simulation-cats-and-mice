package org.simulation.entities;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.simulation.World;

@SuperBuilder
@Getter
public abstract class Creature extends Entity {
    protected Integer speed;
    protected Integer health;

    abstract public void hunting(World world);
    //abstract protected void makeMove(int speedX, int speedY);
}
