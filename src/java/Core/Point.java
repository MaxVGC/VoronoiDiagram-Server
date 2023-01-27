package Core;

public class Point implements Comparable<Point> {

    double x;
    double y;
    String id;
    double magnitude;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Point(double x, double y, String id) {
        this.x = x;
        this.y = y;
        this.id=id;
        this.magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getMagnitude() {
        return this.magnitude;
    }

    public double distanceBetweenTwoPoints(Point p) {
        return Math.sqrt(Math.pow(this.getX() - p.getX(), 2) + Math.pow(this.getY() - p.getY(), 2));
    }

    @Override
    public int compareTo(Point p) {
        int number = (int) Math.signum(this.getX() - p.getX());
        return number;
    }

}
