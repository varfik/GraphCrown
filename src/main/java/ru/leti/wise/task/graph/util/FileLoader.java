package ru.leti.wise.task.graph.util;

import ru.leti.wise.task.graph.model.Color;
import ru.leti.wise.task.graph.model.Edge;
import ru.leti.wise.task.graph.model.Graph;
import ru.leti.wise.task.graph.model.Vertex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class FileLoader {

    private static final String NULL_VALUE = "null";

    public static Graph loadGraphFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            List<Vertex> vertices = new ArrayList<>();
            List<Edge> edges = new ArrayList<>();

            boolean isDirected = scanner.nextBoolean();
            int verticesCount = scanner.nextInt();
            int edgeCount = scanner.nextInt();

            readVertices(scanner, vertices, verticesCount);
            readEdges(scanner, edges, edgeCount, vertices);

            return Graph
                    .builder()
                    .vertexCount(verticesCount)
                    .edgeCount(edgeCount)
                    .isDirect(isDirected)
                    .vertexList(vertices)
                    .edgeList(edges)
                    .build();
        } catch (Exception e) {
            //TODO: придумать нормальные ошибки при ошибке чтения графа
            throw new RuntimeException("Error while reading graph from file");
        }
    }

    private static void readEdges(Scanner scanner, List<Edge> edges, int edgeCount, List<Vertex> vertices) {
        for (int i = 0; i < edgeCount; i++) {
            int source = scanner.nextInt();
            int target = scanner.nextInt();
            Color color = Color.valueOf(scanner.next());
            String weight = scanner.next();
            String label = scanner.next();
            Edge edge = Edge
                    .builder()
                    .source(source)
                    .target(target)
                    .color(color)
                    .weight(weight.equals(NULL_VALUE) ? null : parseInt(weight))
                    .label(label)
                    .build();
            edges.add(edge);
        }
    }

    private static void readVertices(Scanner scanner, List<Vertex> vertices, int verticesCount) {
        for (int i = 0; i < verticesCount; i++) {
            int id = scanner.nextInt();
            Color color = Color.valueOf(scanner.next());
            String weight = scanner.next();
            String label = scanner.next();
            Double xCoordinate = scanner.nextDouble();
            Double yCoordinate = scanner.nextDouble();
            vertices.add(Vertex
                    .builder()
                    .id(id)
                    .color(color)
                    .weight(weight.equals(NULL_VALUE) ? null : parseInt(weight))
                    .label(label)
                    .xCoordinate(xCoordinate.intValue())
                    .yCoordinate(yCoordinate.intValue())
                    .build());
        }
    }
}
