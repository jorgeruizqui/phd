package es.jor.phd.dblp.parser.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hits {

    @JsonProperty("@total")
    private Integer total;
    @JsonProperty("@computed")
    private Integer computed;
    @JsonProperty("@sent")
    private String sent;
    @JsonProperty("@first")
    private String first;
    @JsonProperty("hit")
    private List<Hit> hit;

}
