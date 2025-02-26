package org.simulation.entities;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class Creature extends Entity {
    protected Integer speed;
    protected Integer health;

    abstract public void makeMove();
}
