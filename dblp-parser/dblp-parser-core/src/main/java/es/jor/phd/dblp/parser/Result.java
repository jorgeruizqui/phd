package es.jor.phd.dblp.parser;

public class Result {

    private String query;
    private Status status;
    private Time time;
    private Completions completions;
    private Hits hits;

    public Result() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * @return the completions
     */
    public Completions getCompletions() {
        return completions;
    }

    /**
     * @param completions the completions to set
     */
    public void setCompletions(Completions completions) {
        this.completions = completions;
    }

    /**
     * @return the hits
     */
    public Hits getHits() {
        return hits;
    }

    /**
     * @param hits the hits to set
     */
    public void setHits(Hits hits) {
        this.hits = hits;
    }

}
