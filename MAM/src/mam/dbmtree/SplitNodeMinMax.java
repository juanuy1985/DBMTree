package mam.dbmtree;

import mam.objects.MetricObject;

public class SplitNodeMinMax<T extends MetricObject> extends SplitNodeMin<T> {
    public double getMaxDistance(double dist1, double dist2, double maxDistance) {
        return Math.max(Math.max(dist1, dist2),maxDistance);
    }
}
