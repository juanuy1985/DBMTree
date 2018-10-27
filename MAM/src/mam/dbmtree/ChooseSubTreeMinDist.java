package mam.dbmtree;

import mam.distances.Distance;
import mam.objects.MetricObject;

public class ChooseSubTreeMinDist<T extends MetricObject> implements ChooseSubTree<T> {

    public DBMNode<T> choose(DBMNode<T> node, T insertObject, Distance distanceCalculator) {
        double minDistance = Double.POSITIVE_INFINITY;
        DBMNode<T> result = null;

        for (int index=0; index < node.getEffectiveStored(); index++) {
            T entry = node.getEntry(index);
            double distance = distanceCalculator.dist(entry, insertObject);
            if (node.getSubTree(index) != null && distance < minDistance && distance <= node.getRadius(index)) {
                minDistance = distance;
                result = node.getSubTree(index);
            }
        }

        return result;
    }
}
