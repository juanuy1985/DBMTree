package mam.dbmtree;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import mam.MetricAccessMethod;
import mam.distances.Distance;
import mam.objects.MetricObject;

import static java.lang.Math.abs;

public class DBMTree<T extends MetricObject> implements MetricAccessMethod<T> {

    private DBMNode<T> root;
    private int c;

    private ChooseSubTree<T> chooseSubTree;
    private SplitNode<T> splitNode;
    private Distance distance;

    public DBMTree(int c, ChooseSubTree<T> chooseSubTree, SplitNode<T> splitNode, Distance distance) {
        this.c = c;
        root = null;
        this.chooseSubTree = chooseSubTree;
        this.splitNode = splitNode;
        this.distance = distance;
    }

    private void print(DBMNode<T> node) {
        for (int index=0; index<node.getEffectiveStored(); index++) {
            T entry = node.getEntry(index);
            System.out.println(entry.getAddress() + " - " + (node.getSubTree(index) == null ? "Single" : "SubTree"));
            //System.out.println("type: %s".format(entry.getAddress(), (node.getSubTree(index) == null ? "Single" : "SubTree")));
        }

        System.out.println("----------------------");

        for (int index=0; index<node.getEffectiveStored(); index++) {
            if (node.getSubTree(index) != null) {
                print(node.getSubTree(index));
            }
        }
    }

    public void print() {
        if (root != null) {
            System.out.println("@@@@@@@@@@@@@@@@@@ Printing @@@@@@@@@@@@@@@@@@");
            print(root);
        }
    }

    private Set<QueryResult<T>> rangeQuery(DBMNode node, T queryObject, double queryRadius) {
        Set<QueryResult<T>> answerSet = new TreeSet<>();
        T representative = (T)node.getEntry(node.getRepresentativeIndex());

        for (int index = 0; index < node.getEffectiveStored(); index++) {
            T entry = (T)node.getEntry(index);
            double SrepSq = distance.dist(representative, queryObject);
            double SrepSi = distance.dist(representative, entry);

            if (abs(SrepSq - SrepSi) <= queryRadius + node.getRadius(index)) {
                double dist = distance.dist(entry, queryObject);
                if (dist <= queryRadius + node.getRadius(index)) {
                    if (node.getSubTree(index) != null) {
                        answerSet.addAll( rangeQuery(node.getSubTree(index), queryObject, queryRadius) );
                    } else {
                        answerSet.add(new QueryResult<T>(entry, dist));
                    }
                }
            }
        }

        return answerSet;
    }

    private SplitNodeResult<T> insert(DBMNode node, T insertObject) {
        DBMNode subNode = chooseSubTree.choose(node, insertObject, distance);
        if (subNode != null) {
            SplitNodeResult<T> promotions = insert(subNode, insertObject);
            if (promotions != null) {
                if (node.insertPromotedObjects(promotions) ){
                    return splitNode.split(node, distance);
                }
            }
        } else {
            node.insert(insertObject, distance);
            if (!node.thereIsSpace()) {
                return splitNode.split(node, distance);
            }
        }
        return null;
    }

    @Override
    public void insert(T insertObject) {
        if (root == null) {
            root = new DBMNode<T>(c, 0);
        }
        SplitNodeResult<T> promotions = insert(root, insertObject);
        if (promotions != null) {
            root = new DBMNode<T>(c, 0);
            root.insertPromotedObjects(promotions);
        }
    }

    @Override
    public Set<QueryResult<T>> rangeQuery(T queryObject, double queryRadius) {
        if (root != null) {
            return rangeQuery(root, queryObject, queryRadius);
        }
        return new TreeSet<>();
    }
    
    @Override
    public Set<QueryResult<T>> knnSearch(T queryObject, int k) {
        Double distance = Double.POSITIVE_INFINITY;
        Set<QueryResult<T>> tmp = this.rangeQuery(queryObject, distance);
        Set<QueryResult<T>> answerSet = new TreeSet<>();
        int cont = 0;
        for (QueryResult<T> item : tmp) {
            answerSet.add(item);
            cont++;
            if (cont == k)
                break;
        }
        return answerSet;
    }
}
