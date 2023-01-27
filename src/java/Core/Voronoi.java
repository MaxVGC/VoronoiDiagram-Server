package Core;

/**
 * Voronoi
 */
import java.util.ArrayList;

public class Voronoi {

    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Vertex> vertexs = new ArrayList<>();
    ArrayList<Polygon> polygons = new ArrayList<>();

    public Voronoi(ArrayList<Point> points) {
        this.points = points;
    }

    public ArrayList<Polygon> getVoronoiPolygons() {
        for (int i = 0; i < points.size() - 2; i++) {
            for (int j = i + 1; j < points.size() - 1; j++) {
                for (int k = j + 1; k < points.size(); k++) {
                    Vertex v = new Vertex(points.get(i), points.get(j), points.get(k));
                    if (verificateVertex(v)) {
                        vertexs.add(v);
                    }
                }
            }
        }
        for (int i = 0; i < points.size(); i++) {
            Polygon p = new Polygon(points.get(i));
            for (int j = 0; j < vertexs.size(); j++) {
                if (vertexs.get(j).getPoints().contains(p.getRoot())) {
                    p.addVertex(vertexs.get(j));
                }
            }
            if (p.getVertexs().size() > 2) {
                polygons.add(p);
            }
        }

        return this.polygons;
    }

    public ArrayList<Vertex> getVertexs() {
        return this.vertexs;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public boolean verificateVertex(Vertex v) {
        for (int i = 0; i < this.points.size(); i++) {
            if (!v.isPoint(this.points.get(i)) && !v.condicionDelunay(this.points.get(i))) {
                return false;
            }
        }
        return true;
    }

}
