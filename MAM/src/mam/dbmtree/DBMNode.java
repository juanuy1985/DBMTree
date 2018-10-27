package mam.dbmtree;

import mam.distances.Distance;
import mam.objects.MetricObject;

import java.util.ArrayList;
import java.util.List;

class DBMNode<T extends MetricObject> {
    private int c;
    private List<T> entries;
    private List<DBMNode<T>> subtrees;
    private List<Double> distances;
    private List<Double> radiuses;
    private int effectiveStored;
    private int representativeIndex;

    public DBMNode(int c) {
        this.c = c;
        entries = new ArrayList<>();
        subtrees = new ArrayList<>();
        distances = new ArrayList<>();
        radiuses = new ArrayList<>();

        effectiveStored = 0;
        representativeIndex = -1;
    }

    public DBMNode(int c, int representativeIndex) {
        this(c);
        this.representativeIndex = representativeIndex;
    }

    public boolean thereIsSpace() {
        return effectiveStored < c;
    }

    public void removeEntry(int index) {
        entries.remove(index);
        subtrees.remove(index);
        effectiveStored--;
    }

    public void insert(T insertObject, Distance distance) {
        entries.add(insertObject);
        subtrees.add(null);
        radiuses.add(0.0);
        distances.add(distance.dist(insertObject, entries.get(representativeIndex)));
        effectiveStored++;
    }

    public void insert(T insertObject, DBMNode<T> subtree, double radius, boolean isRepresentative) {
        entries.add(insertObject);
        subtrees.add(subtree);
        radiuses.add(radius);
        distances.add(0.0); //TODO @jvilca: ¿Calculate this distance?

        if(isRepresentative) {
            representativeIndex = effectiveStored;
        }
        effectiveStored++;
    }

    public boolean insertPromotedObjects(SplitNodeResult<T> promotions) {
        for (int index = 0; index < promotions.getPromoted().size(); index++) {
            T insertObject = promotions.getPromoted().get(index);
            DBMNode<T> subTree = promotions.getSubTrees().get(index);
            double radius = promotions.getRadius().get(index);

            insert(insertObject, subTree, radius, false);
        }

        for (int index = 0; index<effectiveStored; index++) {
            if (subtrees.get(index) == promotions.getOldNode()) {
                subtrees.remove(index);
                entries.remove(index);
                radiuses.remove(index);
                distances.remove(index);
                effectiveStored--;
                break;
            }
        }

        return effectiveStored > c;
    }

    public int getEffectiveStored() {
        return effectiveStored;
    }

    public DBMNode<T> getSubTree(int index) {
        return subtrees.get(index);
    }

    public T getEntry(int index) {
        return entries.get(index);
    }

    public double getRadius(int index) {
        return radiuses.get(index);
    }

    public int getRepresentativeIndex() {
        return representativeIndex;
    }

    public List<T> getEntries() {
        return entries;
    }
}