package mam;

import mam.dbmtree.QueryResult;
import mam.objects.MetricObject;

import java.util.Set;

public interface MetricAccessMethod<T extends MetricObject> {

    void insert(T insertObject);

    Set<QueryResult<T>> rangeQuery(T queryObject, double radius);

    Set<QueryResult<T>> knnSearch(T queryObject, int k);
}
