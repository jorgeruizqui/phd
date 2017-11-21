package es.jor.phd.dblp.parser.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HitInfo {

    @JsonProperty("title")
    private String title;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("type")
    private String type;
    @JsonProperty("key")
    private String key;
    @JsonProperty("url")
    private String url;
    @JsonProperty("authors")
    private Authors authors;

}
