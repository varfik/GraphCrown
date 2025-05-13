/*
 * Модуль получает граф и проверяет, является ли он графом-короной.
 */

package ru.leti.wise.task.graph_crown;

import ru.leti.wise.task.graph.model.*;
import ru.leti.wise.task.plugin.graph.GraphProperty;

import java.util.*;

public class GraphCrownChecker implements GraphProperty {

    @Override
    public boolean run(Graph graph) {
        Map<Integer, Set<Integer>> adjList = buildAdjacencyList(graph);
        if (!checkBasicConditions(graph)) {
            return false;
        }

        List<List<Integer>> bipartition = getBipartition(graph, adjList);
        if (bipartition != null && bipartition.get(0).size() != bipartition.get(1).size()) {
            return false;
        }

        return isCrownStructure(adjList, bipartition.get(0), bipartition.get(1));
    }

    // Построение списка смежности
    private Map<Integer, Set<Integer>> buildAdjacencyList(Graph graph) {
        Map<Integer, Set<Integer>> adjList = new HashMap<>();
        for (Vertex v : graph.getVertexList()) {
            adjList.put(v.getId(), new HashSet<>());
        }
        for (Edge e : graph.getEdgeList()) {
            adjList.get(e.getSource()).add(e.getTarget());
            if (!graph.isDirect()) {
                adjList.get(e.getTarget()).add(e.getSource());
            }
        }
        return adjList;
    }

    // Проверка на двудольность, получение двух долей
    public static List<List<Integer>> getBipartition(Graph graph, Map<Integer, Set<Integer>> adjList) {
        int n = graph.getVertexCount();
        int[] colors = new int[n];
        Arrays.fill(colors, 0);

        List<Integer> partA = new ArrayList<>();
        List<Integer> partB = new ArrayList<>();

        for (Vertex v : graph.getVertexList()) {
            int startId = v.getId();
            if (colors[startId] != 0) continue;

            Queue<Integer> queue = new LinkedList<>();
            queue.add(startId);
            colors[startId] = 1;

            while (!queue.isEmpty()) {
                int current = queue.poll();
                if (colors[current] == 1) partA.add(current);
                else partB.add(current);

                for (int neighbor : adjList.get(current)) {
                    if (colors[neighbor] == 0) {
                        colors[neighbor] = 3 - colors[current];
                        queue.add(neighbor);
                    } else if (colors[neighbor] == colors[current]) {
                        return null;
                    }
                }
            }
        }
        return Arrays.asList(partA, partB);
    }

    // Проверка базовых условий
    private boolean checkBasicConditions(Graph graph) {
        // Проверка, что граф содержит четное количество вершин
        if (graph.getVertexCount() % 2 != 0) {
            return false;
        }
        // Проверка, что количество ребер равно n*(n-1)
        int n = graph.getVertexCount() / 2;
        return graph.getEdgeCount() == n * (n - 1);
    }

    // Проверка структуры короны (вершины, отсутствующие ребра)
    private boolean isCrownStructure(Map<Integer, Set<Integer>> adjList, List<Integer> partA, List<Integer> partB) {
        int n = partA.size();
        Set<Integer> partBSet = new HashSet<>(partB);
        boolean[] usedB = new boolean[n]; // Сохранение уже использованных из B

        for (int a : partA) {
            Set<Integer> neighbors = adjList.get(a);
            if (neighbors.size() != n - 1) {
                return false; // Степень вершины должна быть n-1
            }

            // Поиск единственного b из partB, у которого нет связи с A
            Set<Integer> missingB = new HashSet<>(partBSet);
            missingB.removeAll(neighbors); // Разность множеств: partB \ neighbors(A)

            if (missingB.size() != 1) {
                return false; // Ровно одно отсутствующее ребро
            }
            int missing = missingB.iterator().next();
            int missingIndex = partB.indexOf(missing); // Поиск индекса b в partB

            if (usedB[missingIndex]) {
                return false; // Если b уже использован, граф не корона
            }
            usedB[missingIndex] = true;
        }
        return true;
    }
}
