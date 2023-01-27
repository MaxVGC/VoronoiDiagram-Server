/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package API;

import Core.Graph;
import Core.Node;
import Core.Point;
import Core.Polygon;
import Core.Voronoi;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
@Path("getVoronoiDiagram")
public class VoronoiDiagram {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VoronoiDiagram
     */
    public VoronoiDiagram() {
    }

    /**
     * Retrieves representation of an instance of api.VoronoiDiagram
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(String points) {
        JsonObject jobj = new Gson().fromJson(points, JsonObject.class);
        JsonArray a = jobj.get("points").getAsJsonArray();
        ArrayList<Point> pointsL = new ArrayList<>();
        String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        for (int i = 0; i < a.size(); i++) {
            JsonObject obj = a.get(i).getAsJsonObject();
            int k = i % 26;
            int j = (int) (Math.floor(i / 26) - 1);
            String aux = "";
            for (int b = -1; i < j; b++) {
                aux = aux + letter[j];
            }
            aux = aux + letter[k];
            pointsL.add(new Point(obj.get("x").getAsDouble(), obj.get("y").getAsDouble()));
            pointsL.get(pointsL.size() - 1).setId(aux);
        }

        Collections.sort(pointsL);

        Voronoi v = new Voronoi(pointsL);
        ArrayList<Polygon> polygons = v.getVoronoiPolygons();
        Graph g = new Graph(v.getPoints());
        g.buildGraphDelaunay(v.getVertexs());
        Map<Point, Node> nodes = g.getNodes();

        JsonObject res = new JsonObject();

        JsonArray voronoiPolygons = new JsonArray();
        for (int i = 0; i < polygons.size(); i++) {
            JsonObject polygon = new JsonObject();
            JsonArray vertexs = new JsonArray();
            Iterator it = polygons.get(i).getVertexs().keySet().iterator();
            while (it.hasNext()) {
                Double key = (Double) it.next();
                JsonObject point = new JsonObject();
                point.addProperty("x", polygons.get(i).getVertexs().get(key).getPositionVertex().getX());
                point.addProperty("y", polygons.get(i).getVertexs().get(key).getPositionVertex().getY());
                vertexs.add(point);
            }
            JsonObject pointPolygon = new JsonObject();
            pointPolygon.addProperty("x", polygons.get(i).getRoot().getX());
            pointPolygon.addProperty("y", polygons.get(i).getRoot().getY());
            pointPolygon.addProperty("name", polygons.get(i).getRoot().getId());

            polygon.add("vertexs", vertexs);
            polygon.add("point", pointPolygon);

            voronoiPolygons.add(polygon);
        }

        res.add("voronoiPolygons", voronoiPolygons);

        JsonArray delaunaySegments = new JsonArray();
        Iterator it = g.getNodes().keySet().iterator();
        while (it.hasNext()) {
            JsonObject segment = new JsonObject();
            JsonArray pointsSegment = new JsonArray();
            Point key = (Point) it.next();
            ArrayList<Point> it2 = nodes.get(key).getPoints();
            JsonObject root = new JsonObject();
            root.addProperty("x", key.getX());
            root.addProperty("y", key.getY());
            root.addProperty("name", key.getId());

            JsonObject nearPoint = new JsonObject();
            if (nodes.get(key).getNearPoint() != null) {
                nearPoint.addProperty("x", nodes.get(key).getNearPoint().getX());
                nearPoint.addProperty("y", nodes.get(key).getNearPoint().getY());
                nearPoint.addProperty("name", nodes.get(key).getNearPoint().getId());
                segment.add("nearPoint", nearPoint);
            }

            for (int i = 0; i < it2.size(); i++) {
                JsonObject point = new JsonObject();
                point.addProperty("x", it2.get(i).getX());
                point.addProperty("y", it2.get(i).getY());
                point.addProperty("name", it2.get(i).getId());
                pointsSegment.add(point);
            }
            segment.add("segment", pointsSegment);
            segment.add("root", root);

            delaunaySegments.add(segment);
        }

        res.add("delaunaySegments", delaunaySegments);
        res.addProperty("tamañoP", polygons.size());
        res.addProperty("tamañoV", g.getNodes().size());

        return res.toString();
    }

    /**
     * PUT method for updating or creating an instance of VoronoiDiagram
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
