package Core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

public class Polygon {

    Map<Double, Vertex> vertexs = new TreeMap<Double, Vertex>();

    Point root;

    public Polygon(Point root) {
        this.root = root;
    }

    public void addVertex(Vertex v) {
        double x = v.getPositionVertex().getX() - this.root.getX();
        double y = v.getPositionVertex().getY() - this.root.getY();
        double tetha = Math.toDegrees(Math.atan2(y, x));
        tetha = (tetha + 360) % 360;
        vertexs.put(new BigDecimal(tetha).setScale(6, RoundingMode.HALF_UP).doubleValue(), v);
    }

    public Map<Double, Vertex> getVertexs() {
        return this.vertexs;
    }

    public Point getRoot() {
        return this.root;
    }

}
