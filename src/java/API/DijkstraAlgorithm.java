/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package API;

import Core.Dijkstra;
import Core.Graph;
import Core.Node;
import Core.Point;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author carlo
 */
@Path("getDijkstraAlgorithm")
public class DijkstraAlgorithm {

    @Context
    private UriInfo context;

    public DijkstraAlgorithm() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(String segments) {
        Map<Point, Node> nodes = new HashMap<Point, Node>();
        Map<String, Point> points = new HashMap<String, Point>();
        Point from = null, to = null;
        JsonObject jobj = new Gson().fromJson(segments, JsonObject.class);
        JsonArray nodesAux = jobj.get("segments").getAsJsonObject().get("nodes").getAsJsonArray();
        for (int i = 0; i < nodesAux.size(); i++) {
            JsonObject obj = nodesAux.get(i).getAsJsonObject();
            Point aux = new Point(
                    obj.get("root").getAsJsonObject().get("x").getAsDouble(),
                    obj.get("root").getAsJsonObject().get("y").getAsDouble(),
                    obj.get("root").getAsJsonObject().get("name").getAsString());
            if (jobj.get("segments").getAsJsonObject().get("from").getAsJsonObject().get("name").getAsString().equals(aux.getId())) {
                from = aux;
            }
            if (jobj.get("segments").getAsJsonObject().get("to").getAsJsonObject().get("name").getAsString().equals(aux.getId())) {
                to = aux;
            }
            points.put(obj.get("root").getAsJsonObject().get("name").getAsString(), aux);
        }
        for (int i = 0; i < nodesAux.size(); i++) {
            JsonObject obj = nodesAux.get(i).getAsJsonObject();
            Node node = new Node(points.get(obj.get("root").getAsJsonObject().get("name").getAsString()));
            JsonArray pointsA = nodesAux.get(i).getAsJsonObject().get("segment").getAsJsonArray();
            for (int j = 0; j < pointsA.size(); j++) {
                node.addConexion(points.get(pointsA.get(j).getAsJsonObject().get("name").getAsString()));
            }
            nodes.put(points.get(obj.get("root").getAsJsonObject().get("name").getAsString()), node);
        }
        Dijkstra DijAlg = new Dijkstra(new Graph(nodes), from, to);
        JsonObject res = new JsonObject();
        JsonArray pathJson = new JsonArray();
        JsonArray pathArray = new JsonArray();
        Map<Integer, Node> path = DijAlg.getPath();
        List<Integer> keys = new ArrayList<>(path.keySet());
        Collections.reverse(keys);
        double totalDistance = 0;
        for (int i = keys.size() - 2; i >= 0; i--) {
            double aux = path.get(i).getPosition().distanceBetweenTwoPoints(path.get(i + 1).getPosition());
            JsonObject SegmentPath = new JsonObject();
            JsonObject NodeFrom = new JsonObject();
            JsonObject NodeTo = new JsonObject();
            NodeFrom.addProperty("name", path.get(i + 1).getPosition().getId());
            NodeFrom.addProperty("x", path.get(i + 1).getPosition().getX());
            NodeFrom.addProperty("y", path.get(i + 1).getPosition().getY());
            NodeTo.addProperty("name", path.get(i).getPosition().getId());
            NodeTo.addProperty("x", path.get(i).getPosition().getX());
            NodeTo.addProperty("y", path.get(i).getPosition().getY());
            totalDistance += aux;
            SegmentPath.add("from", NodeFrom);
            SegmentPath.add("to", NodeTo);
            SegmentPath.addProperty("distance", aux);
            pathJson.add(SegmentPath);
            pathArray.add(path.get(i).getPosition().getId());
        }
        pathArray.add(path.get(keys.size() - 1).getPosition().getId());
        res.add("mostShortPath", pathJson);
        res.add("pathArray", pathArray);
        res.addProperty("totalDistance", totalDistance);
        return res.toString();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
