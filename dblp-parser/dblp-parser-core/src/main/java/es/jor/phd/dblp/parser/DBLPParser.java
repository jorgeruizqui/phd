package es.jor.phd.dblp.parser;

import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.jor.phd.dblp.parser.entity.DBPLEntity;

/**
 * DBLP Parser
 * @author Jor
 *
 */
public class DBLPParser {

    /**
     * Main method
     * @param args Arguments
     */
    public static void main(String[] args) {
        String wsURI = "http://dblp.org/search/publ/api?q=game%20design%20&format=json";
        try {
            URL url = new URL(wsURI);
            URLConnection connection = url.openConnection();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            DBPLEntity myPojo = mapper.readValue(connection.getInputStream(), DBPLEntity.class);
            System.out.println(myPojo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
