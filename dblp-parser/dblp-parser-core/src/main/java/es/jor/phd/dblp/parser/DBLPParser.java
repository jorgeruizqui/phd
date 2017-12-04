package es.jor.phd.dblp.parser;

import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.jor.phd.dblp.parser.entity.DBLPEntity;

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
        DBLPEntity entity = getDBPLEntity(wsURI);
        System.out.println(entity);
    }

    /**
     * Gets the entity caught from URL
     * @param anUrl url to parse
     */
    public static DBLPEntity getDBPLEntity(String anUrl) {

    	DBLPEntity rto = null;

        try {
            URL url = new URL(anUrl);
            URLConnection connection = url.openConnection();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            rto = mapper.readValue(connection.getInputStream(), DBLPEntity.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rto;
    }
}
