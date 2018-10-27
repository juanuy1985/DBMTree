package mam.objects;

import mam.features.FeatureExtractor;

public class MetricObject {
    protected double[] features;
    protected String address;

    public MetricObject(String address, FeatureExtractor extractor) {
        this.address = address;
        features = extractor.getFeatures(this);
    }

    public double[] getFeatures() {
        return features;
    }

    public String getAddress() {
        return address;
    }
}
