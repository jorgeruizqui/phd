package es.jor.phd.xvgdl.model.location;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DirectionVector {

    /** x vector direction. */
    private double x;
    /** y vector direction. */
    private int y;
    /** z vector direction. */
    private int z;
}
