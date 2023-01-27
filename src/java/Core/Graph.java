package Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    Map<Point, Node> nodes = new HashMap<Point, Node>();

    public Graph(ArrayList<Point> points) {
        Iterator it = points.iterator();
        while (it.hasNext()) {
            Point elementoLista = (Point) it.next();
            nodes.put(elementoLista, new Node(elementoLista));
        }
    }
    
    public Graph(Map<Point, Node> nodes){
        this.nodes=nodes;
    }

    public void buildGraphDelaunay(ArrayList<Vertex> vertexs) {
        Iterator it = vertexs.iterator();
        while (it.hasNext()) {
            Vertex elementoLista = (Vertex) it.next();
            nodes.get(elementoLista.getPoints().get(0)).addConexion(elementoLista.getPoints().get(1));
            nodes.get(elementoLista.getPoints().get(0)).addConexion(elementoLista.getPoints().get(2));
            nodes.get(elementoLista.getPoints().get(1)).addConexion(elementoLista.getPoints().get(0));
            nodes.get(elementoLista.getPoints().get(1)).addConexion(elementoLista.getPoints().get(2));
            nodes.get(elementoLista.getPoints().get(2)).addConexion(elementoLista.getPoints().get(0));
            nodes.get(elementoLista.getPoints().get(2)).addConexion(elementoLista.getPoints().get(1));
        }
    }

    public Map<Point, Node> getNodes(){
        return this.nodes;
    }

}
