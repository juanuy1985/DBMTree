package mam.features;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;

import mam.objects.MetricObject;

public class FeatureExtractorHistogram implements FeatureExtractor {

    private int numberFeatures;

    public FeatureExtractorHistogram(int numberFeatures) {
        this.numberFeatures = numberFeatures;
    }

    public double[] getFeatures(MetricObject object) {
        double[] features = new double[numberFeatures];
        int divisor = 256*256*256/numberFeatures;

        try {
            final BufferedImage image = ImageIO.read(new File(object.getAddress()));

            for (int i=0; i<image.getHeight();i++) {
                for(int j=0; j<image.getWidth();j++) {

                    Color originalColor = new Color(image.getRGB(j,i));
                    int r = originalColor.getRed();
                    int g = originalColor.getGreen();
                    int b = originalColor.getBlue();

                    features[(r*256*256+g*256+b) / divisor]++;
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        mam.MetricUtils.normalizeVector(features);

        return features;
    }
}
