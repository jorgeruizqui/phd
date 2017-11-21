package es.jor.phd.dblp.parser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hit {

    @JsonProperty("@score")
    private Integer total;
    @JsonProperty("@id")
    private String id;
    @JsonProperty("info")
    private HitInfo info;


}
