package mam.dbmtree;

import mam.distances.Distance;
import mam.objects.MetricObject;


public interface ChooseSubTree<T extends MetricObject> {
    DBMNode<T> choose(DBMNode<T> node, T insertObject, Distance distanceCalculator);
}
