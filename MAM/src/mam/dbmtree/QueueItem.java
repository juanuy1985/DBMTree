package mam.dbmtree;

import mam.objects.MetricObject;

class QueueItem <T extends MetricObject> {
    private DBMNode<T> node;
    private double radius;
    QueueItem(DBMNode<T> node, double radius) {
        this.node = node;
        this.setRadius(radius);
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public DBMNode<T> getNode() {
        return node;
    }
}