package mam.dbmtree;

import mam.distances.Distance;
import mam.objects.MetricObject;
import mam.MetricUtils;


public abstract class SplitNodeMin<T extends MetricObject> implements SplitNode<T> {

    public SplitNodeResult<T> split(DBMNode<T> node, Distance distance) {
        SplitNodeResult<T> result = new SplitNodeResult<T>();
        int c = node.getEntries().size();
        double[][] distances = MetricUtils.getAllDistances(node.getEntries(), node.getEffectiveStored(), distance);

        double minDistance = Double.POSITIVE_INFINITY, maxDistance = 0.0, radiusRep1 = 0.0, radiusRep2 = 0.0;
        int rep1 = -1, rep2 = -1;

        for (int i = 0; i<distances.length; i++) {
            for (int j = i+1; j<distances.length; j++) {
                for(int k = 0; k<distances.length; k++)
                {
                    if( k != i && k != j) {
                        maxDistance = getMaxDistance(distances[i][k], distances[j][k], maxDistance);
                        radiusRep1 = Math.max(distances[i][k], radiusRep1);
                        radiusRep2 = Math.max(distances[j][k], radiusRep2);
                    }
                }

                if (maxDistance < minDistance) {
                    minDistance = maxDistance;
                    rep1 = i;
                    rep2 = j;
                }
            }
        }

        DBMNode<T> node1 = new DBMNode<>(c), node2 = new DBMNode<>(c);

        for (int k = 0; k<distances.length; k++)
        {
            if (distances[rep1][k] < distances[rep2][k]) {
                node1.insert(node.getEntry(k), node.getSubTree(k), node.getRadius(k), rep1 == k);
            } else {
                node2.insert(node.getEntry(k), node.getSubTree(k), node.getRadius(k), rep2 == k);
            }
        }

        result.getPromoted().add(node.getEntry(rep1));
        result.getPromoted().add(node.getEntry(rep2));
        result.getSubTrees().add(node1);
        result.getSubTrees().add(node2);
        result.getRadius().add(radiusRep1);
        result.getRadius().add(radiusRep2);
        result.setOldNode(node);
        return result;
    }

    public abstract double getMaxDistance(double dist1, double dist2, double maxDistance);
}
