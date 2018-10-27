package mam.features;

import mam.objects.MetricObject;

public interface FeatureExtractor {
    double[] getFeatures(MetricObject object);
}
