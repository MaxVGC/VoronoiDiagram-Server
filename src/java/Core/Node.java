package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node implements Comparable<Node>{

    Map<Point, Double> nodesAdy = new HashMap<Point, Double>();
    ArrayList<Point> points = new ArrayList<>();
    Point nearPoint = null;
    double DistanceToNearPoint;
    Point position;

    public Node(Point position) {
        this.position = position;
    }

    public void addConexion(Point p) {
        if (nodesAdy.get(p) == null) {
            double distance = this.position.distanceBetweenTwoPoints(p);
            this.nodesAdy.put(p, distance);
            this.points.add(p);
            if (nearPoint == null) {
                this.nearPoint = p;
                this.DistanceToNearPoint = distance;
            } else if (distance < DistanceToNearPoint) {
                this.nearPoint = p;
                this.DistanceToNearPoint = distance;
            }
        }
    }

    public Point getNearPoint() {
        return this.nearPoint;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public Map<Point, Double> getNodesAdy() {
        return this.nodesAdy;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    
    @Override
    public int compareTo(Node p) {
        int number = this.position.getId().compareTo(p.position.getId());
        return number;
    }
}
