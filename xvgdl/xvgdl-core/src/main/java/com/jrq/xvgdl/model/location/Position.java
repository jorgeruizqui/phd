package com.jrq.xvgdl.model.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Builder
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
