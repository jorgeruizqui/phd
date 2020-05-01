package com.jrq.xvgdl.model.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class DirectionVector {

    Position position = new Position(0, 0, 0);

    public boolean notZero() {
        return position.getX() != 0
                || position.getY() != 0
                || position.getZ() != 0;
    }

    public static DirectionVector parseFromString(String property) {
        String[] values = property.split(",");
        return new DirectionVector(new Position(
                Integer.parseInt(values[0]),
                Integer.parseInt(values[1]),
                Integer.parseInt(values[2])));
    }

    public void invert() {
        position.setX(position.getX() * -1);
        position.setY(position.getY() * -1);
        position.setZ(position.getZ() * -1);
    }
}
