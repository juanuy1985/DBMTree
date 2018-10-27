package mam.distances;

import mam.objects.MetricObject;

public interface Distance {
    double dist(MetricObject obj1, MetricObject obj2);
}
