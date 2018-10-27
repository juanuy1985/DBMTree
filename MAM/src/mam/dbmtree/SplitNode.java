package mam.dbmtree;

import mam.distances.Distance;
import mam.objects.MetricObject;


public interface SplitNode<T extends MetricObject> {
    SplitNodeResult<T> split(DBMNode<T> node, Distance distanceCalculator);
}
