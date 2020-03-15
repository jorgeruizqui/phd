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
    private Integer x;
    /**
     * y position.
     */
    private Integer y;
    /**
     * z position.
     */
    private Integer z;
}
