package com.jrq.xvgdl.model.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class DirectionVector {

    /**
     * x vector direction.
     */
    private int x;
    /**
     * y vector direction.
     */
    private int y;
    /**
     * z vector direction.
     */
    private int z;

    public boolean notZero() {
        return x != 0 || y != 0 || z != 0;
    }

    /**
     * Parse property in format x,y,z
     *
     * @param property value
     * @return
     */
    public static DirectionVector parseFromString(String property) {
        String[] values = property.split(",");
        DirectionVector dv = new DirectionVector(
                Integer.parseInt(values[0]),
                Integer.parseInt(values[1]),
                Integer.parseInt(values[2]));
        return dv;
    }

    public void invert() {
        x *= -1;
        y *= -1;
        z *= -1;
    }
}
