package mam.dbmtree;

import mam.objects.MetricObject;

import java.util.List;
import java.util.ArrayList;

class SplitNodeResult<T extends MetricObject> {

    private List<T> promoted;
    private List<Double> radius;
    private List<DBMNode<T>> subTrees;
    private DBMNode<T> oldNode;

    public SplitNodeResult() {
        promoted = new ArrayList<T>();
        radius = new ArrayList<Double>();
        subTrees = new ArrayList<DBMNode<T>>();
    }

    public List<T> getPromoted() {
        return promoted;
    }

    public List<Double> getRadius() {
        return radius;
    }

    public List<DBMNode<T>> getSubTrees() {
        return subTrees;
    }

    public DBMNode<T> getOldNode() {
        return oldNode;
    }

    public void setOldNode(DBMNode<T> oldNode) {
        this.oldNode = oldNode;
    }
}
