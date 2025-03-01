package org.simulation.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.simulation.Coordinates;

@Setter
@Getter
@SuperBuilder
public abstract class Entity {
    protected Coordinates coordinates;
}