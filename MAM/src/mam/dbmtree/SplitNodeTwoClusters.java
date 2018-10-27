package mam.dbmtree;

import mam.distances.Distance;
import mam.objects.MetricObject;

public class SplitNodeTwoClusters <T extends MetricObject> implements SplitNode<T> {

    public SplitNodeResult<T> split(DBMNode<T> node, Distance distance) {
        SplitNodeResult<T> result = new SplitNodeResult<T>();

        return result;
    }
}