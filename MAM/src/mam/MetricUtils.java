package mam;

import java.util.List;

import mam.distances.Distance;
import mam.objects.MetricObject;

public class MetricUtils {

    public static <T extends MetricObject> double[][] getAllDistances(List<T> objects, int n, Distance distanceCalculator) {
        double[][] distances = new double[n][n];

        for (int i = 0; i<n; i++) {
            for (int j = i+1; j<n; j++) {
                distances[i][j] = distanceCalculator.dist(objects.get(i), objects.get(j));
                distances[j][i] = distances[i][j];
            }
        }

        return distances;
    }

    public static void normalizeVector(double[] vector) {
        double total = 0.0;
        for (double value : vector) {
            total += value * value;
        }

        total = Math.sqrt(total);

        for (int index=0; index < vector.length ;index++) {
            vector[index] = vector[index]/total;
        }
    }
}