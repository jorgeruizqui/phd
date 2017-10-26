package es.jor.phd.dblp.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Time {
    double text;


    public Time() {
        // TODO Auto-generated constructor stub
    }



    /**
     * @return the text
     */
    public double getText() {
        return text;
    }



    /**
     * @param text the text to set
     */
    public void setText(double text) {
        this.text = text;
    }

}
