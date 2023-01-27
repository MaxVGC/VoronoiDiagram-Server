/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author carlo
 */
class Step implements Comparable<Step> {

    double distance;
    Node node;

    public Step(double distance, Node node) {
        this.distance = distance;
        this.node = node;
    }

    @Override
    public int compareTo(Step p) {
        int number = (int) Math.signum(this.distance - p.distance);
        return number;
    }
}

public class Dijkstra {

    Map<Point, Node> nodes = new HashMap<>();
    Map<Integer, Node> path = new HashMap<>();
    Set<Node> flagedNodes = new HashSet<>();
    Map<Node, Step> steps = new HashMap<>();
    Point from, to;
    Node flag;

    public Dijkstra(Graph graph, Point from, Point to) {
        this.nodes = graph.getNodes();
        this.from = from;
        this.to = to;
    }

    public Map<Integer, Node> eraseIterations(Map<Node, Step> conexion) {
        path = new HashMap<Integer, Node>();
        int i = 0;
        return recursiveEraseIterations(conexion, nodes.get(to), i);
    }

    private Map<Integer, Node> recursiveEraseIterations(Map<Node, Step> conexion, Node currentNode, int i) {
        path.put(i, currentNode);
        i++;
        if (currentNode.compareTo(nodes.get(from)) == 0) {
            return path;
        } else {
            return recursiveEraseIterations(conexion, conexion.get(currentNode).node, i);
        }
    }

    public Map<Integer, Node> getPath() {
        flag = nodes.get(from);
        flagedNodes.add(flag);
        path.put(0, flag);
        Map<Node, Step> conexion = new HashMap<Node, Step>();
        Boolean flagA = true;
        Double distance = 0.0;
        while (flagA) {
            Map<Point, Double> nodesAdy = flag.getNodesAdy();
            for (Map.Entry<Point, Double> entry : nodesAdy.entrySet()) {
                if (!flagedNodes.contains(nodes.get(entry.getKey()))) {
                    if (steps.get(nodes.get(entry.getKey())) == null || ((distance + entry.getValue()) < steps.get(nodes.get(entry.getKey())).distance)) {
                        steps.put(nodes.get(entry.getKey()), new Step(distance + entry.getValue(), flag));
                    }
                }
            }
            flag = Collections.min(steps.entrySet(), Map.Entry.comparingByValue()).getKey();
            distance = steps.get(flag).distance;
            flagedNodes.add(flag);
            path.put(path.size(), flag);
            conexion.put(flag, steps.get(flag));
            steps.remove(flag);
            if (flag.compareTo(nodes.get(to)) == 0) {
                flagA = false;
            }
        }

        conexion = conexion.entrySet().stream()
                .sorted(Collections.reverseOrder(Entry.comparingByValue()))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        path = eraseIterations(conexion);

        return this.path;
    }

}
