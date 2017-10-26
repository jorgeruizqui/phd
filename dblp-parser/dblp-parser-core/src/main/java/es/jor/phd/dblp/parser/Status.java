package es.jor.phd.dblp.parser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    @JsonProperty("@code")
    String code;
    String text;


    public Status() {
        // TODO Auto-generated constructor stub
    }



    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }



    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }



    /**
     * @return the text
     */
    public String getText() {
        return text;
    }



    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }


}
