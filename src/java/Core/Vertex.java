package Core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Vertex {

    Point root;
    double Ratio;
    ArrayList<Point> points = new ArrayList<>();

    public Vertex(Point Pa, Point Pb, Point Pc) {
        points.add(Pa);
        points.add(Pb);
        points.add(Pc);
        this.root = positionVertex(Pa, Pb, Pc);
        if (root != null) {
            this.Ratio = this.root.distanceBetweenTwoPoints(Pa);
        }
    }

    public Point positionVertex(Point Pa, Point Pb, Point Pc) {
        double a = Pa.getX();
        double b = Pa.getY();
        double c = Pb.getX();
        double d = Pb.getY();
        double f = Pc.getX();
        double g = Pc.getY();
        double determinante = (2 * b * c - 2 * a * d - 2 * b * f + 2 * d * f + 2 * a * g - 2 * c * g);
        double CenterX = (b * c * c + b * d * d - a * a * d - b * b * d - b * f * f + d * f * f - b * g * g + d * g * g
                + a * a * g + b * b * g - c * c * g - d * d * g)
                / (2 * b * c - 2 * a * d - 2 * b * f + 2 * d * f + 2 * a * g - 2 * c * g);
        double CenterY = (-a * c * c + a * a * c + b * b * c - a * d * d + a * f * f - c * f * f - a * a * f - b * b * f
                + c * c * f + d * d * f + a * g * g - c * g * g)
                / (2 * b * c - 2 * a * d - 2 * b * f + 2 * d * f + 2 * a * g - 2 * c * g);
        if (determinante == 0) {
            return null;
        } else {
            return new Point(new BigDecimal(CenterX).setScale(3, RoundingMode.HALF_UP).doubleValue(), new BigDecimal(CenterY).setScale(3, RoundingMode.HALF_UP).doubleValue());
        }

    }

    public boolean condicionDelunayx(Point Pa, Point Pb, Point Pc, Point Pd) {
        double a = Pa.getX() - Pd.getX();
        double b = Pa.getY() - Pd.getX();
        double c = Pb.getX() - Pd.getX();
        double d = Pb.getY() - Pd.getY();
        double g = Pc.getX() - Pd.getX();
        double h = Pc.getY() - Pd.getY();
        double aux = -(b * c * g * g) + (a * d * g * g) + (b * c * c * g) + (b * d * d * g) - (a * a * d * g)
                - (b * b * d * g) - (b * c * h * h) + (a * d * h * h)
                - (a * c * c * h) + (a * a * c * h) + (b * b * c * h) - (a * d * d * h);
        if (aux > 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean condicionDelunay(Point P) {
        if (root == null) {
            return false;
        }
        double eq = Math.pow(P.getX() - root.getX(), 2) + Math.pow(P.getY() - root.getY(), 2);
        if (eq < Math.pow(Ratio, 2)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isPoint(Point a) {
        if (!points.contains(a)) {
            return false;
        }
        return true;
    }

    public Point getPositionVertex() {
        return root;
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public String toString() {
        return "Center (" + root.getX() + "," + root.getY() + ") Points: " + points.get(0).toString() + ", "
                + points.get(1).toString() + ", " + points.get(2).toString();
    }

}
