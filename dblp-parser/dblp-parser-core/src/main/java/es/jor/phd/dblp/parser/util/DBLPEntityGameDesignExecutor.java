package es.jor.phd.dblp.parser.util;

import es.jor.phd.dblp.parser.DBLPParser;
import es.jor.phd.dblp.parser.entity.DBLPEntity;

public class DBLPEntityGameDesignExecutor {

	private static final String URL_API = "http://dblp.org/search/publ/api?";
	private static final String URL_QUERY = "q=game%20design%20";
	private static final String URL_FORMAT = "&format=json";
	private static final String URL_TOTAL_NUMBER = URL_API + URL_QUERY + URL_FORMAT + "&h=1";
	private static final String URL_RESULTS = URL_API + URL_QUERY + URL_FORMAT + "&h=1000";

	private static final int MAX_NUMBER_OF_HITS = 1_000;

	private DBLPEntityGameDesignExecutor() {
	}

	public static DBLPEntity launch() {

		// Just to know total number of hits
		DBLPEntity entityTotal = DBLPParser.getDBPLEntity(URL_TOTAL_NUMBER);
		int queryIndex = 0;

		while (queryIndex < (1 + (entityTotal.getResult().getHits().getTotal() / MAX_NUMBER_OF_HITS))) {

			DBLPEntity entityResults = DBLPParser.getDBPLEntity(URL_RESULTS + "&f=" + (queryIndex * MAX_NUMBER_OF_HITS));
			// Updating total results
			entityTotal.getResult().getHits().getHit().addAll(entityResults.getResult().getHits().getHit());

			queryIndex++;

		}
		return entityTotal;
	}
}
