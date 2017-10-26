package es.jor.phd.dblp.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

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
            DBPLEntity myPojo = mapper.readValue(connection.getInputStream(), DBPLEntity.class);
            System.out.println(myPojo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
