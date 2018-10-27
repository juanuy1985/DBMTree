package mam.distances;

import mam.objects.MetricObject;

public class Euclidean implements Distance {
    public double dist(MetricObject obj1, MetricObject obj2) {
        double distance = 0.0;

        double[] features1 = obj1.getFeatures();
        double[] features2 = obj2.getFeatures();

        int numberFeatures = features1.length;

        for (int index=0; index<numberFeatures ;index++) {
            distance += Math.pow(features1[index]-features2[index], 2);
        }

        return Math.sqrt(distance);
    }
}
