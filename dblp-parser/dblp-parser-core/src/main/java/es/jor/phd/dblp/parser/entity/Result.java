package es.jor.phd.dblp.parser.entity;

import lombok.Data;

@Data
public class Result {

    private String query;
    private Status status;
    private Time time;
    private Completions completions;
    private Hits hits;

}
