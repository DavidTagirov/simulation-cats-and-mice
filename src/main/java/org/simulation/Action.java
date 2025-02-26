package org.simulation;

import lombok.Data;
import org.simulation.entities.Entity;

@Data
public class Action {
    private World entities = new World();

    public void initAction() {
        //расставить существа по карте
    }

    public void turnAction() {
        // действия совершаемые каждый ход
    }
}
