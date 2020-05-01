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
