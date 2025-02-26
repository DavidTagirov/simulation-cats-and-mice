package org.simulation.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;

@SuperBuilder
@Setter
@Getter
public abstract class Entity {
    protected Coordinates coordinates;
}