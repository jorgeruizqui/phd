package es.jor.phd.xvgdl.context.xml;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Data
public class GamePropertiesDefinition extends Properties {
/*
    private Map<String, String> mapOfProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, String> get() {
        return mapOfProperties;
    }

    @JsonAnySetter
    public void set(String name, String value) {
        mapOfProperties.put(name, value);
    }
*/
}
