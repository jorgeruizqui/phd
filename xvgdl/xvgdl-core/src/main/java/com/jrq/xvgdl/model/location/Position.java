package com.jrq.xvgdl.model.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Position {

    /**
     * x position.
     */
    private int x;
    /**
     * y position.
     */
    private int y;
    /**
     * z position.
     */
    private int z;
}
