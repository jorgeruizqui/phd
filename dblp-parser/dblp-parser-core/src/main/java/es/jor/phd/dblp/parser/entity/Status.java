package es.jor.phd.dblp.parser.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Status {
	@JsonProperty("@code")
	private String code;
	private String text;
}
