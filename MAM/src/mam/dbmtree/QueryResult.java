package mam.dbmtree;

public class QueryResult<T> implements Comparable<QueryResult<T>> {
    private T result;
    private Double distance;

    public QueryResult(T result, Double distance) {
        this.result = result;
        this.distance = distance;
    }

    public int compareTo(QueryResult<T> other) {
        return distance.compareTo(other.distance);
    }

    public Double getDistance() {
        return distance;
    }

    public T getResult() {
        return result;
    }
}
