/*
 * Модуль получает граф и проверяет, является ли он графом-короной.
 *
 * Граф-корона (crown graph) на 2n вершинах - это полный двудольный граф K(n, n),
 * из которого удалено совершенное паросочетание.
 * Основные этапы алгоритма:
 * 1. Проверка базовых условий (чётность числа вершин, число рёбер n*(n−1));
 * 2. Проверка на двудольность и получение разбиения;
 * 3. Проверка соответствия структуре короны
 */

package ru.leti.wise.task.graph_crown;

import ru.leti.wise.task.graph.model.*;
import ru.leti.wise.task.plugin.graph.GraphProperty;

import java.util.*;

public class GraphCrownChecker implements GraphProperty {

    @Override
    public boolean run(Graph graph) {
        // Построение списка смежности графа
        Map<Integer, Set<Integer>> adjList = buildAdjacencyList(graph);

        // Проверка базовых условий: четность вершин, количество ребер
        if (!checkBasicConditions(graph)) {
            return false;
        }

        // Получение двудольного разбиения графа (иначе null)
        List<List<Integer>> bipartition = getBipartition(graph, adjList);
        // Если граф недвудольный => не корона
        if (bipartition == null) {
            return false;
        }
        // Если размеры долей не равны => не корона
        if (bipartition.get(0).size() != bipartition.get(1).size()) {
            return false;
        }

        // Проверка структуры графа
        return isCrownStructure(adjList, bipartition.get(0), bipartition.get(1));
    }

    // Построение списка смежности (adjacency list)
    private Map<Integer, Set<Integer>> buildAdjacencyList(Graph graph) {
        Map<Integer, Set<Integer>> adjList = new HashMap<>();

        // Для каждой вершины инициализация пустого множества смежных вершин
        for (Vertex v : graph.getVertexList()) {
            adjList.put(v.getId(), new HashSet<>());
        }

        // Добавление всех ребер в список смежности
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
        Arrays.fill(colors, 0); // 0 — не раскрашен, 1 — часть A, 2 — часть B

        List<Integer> partA = new ArrayList<>();
        List<Integer> partB = new ArrayList<>();

        if (n == 2) {
            partA.add(1);
            partB.add(2);
            return Arrays.asList(partA, partB);
        }

            // Запуск BFS (обход в ширину) для каждой нераскрашенной вершины
        for (Vertex v : graph.getVertexList()) {
            int startId = v.getId() - 1; // Получение индекса начальной вершины
            if (colors[startId] != 0) continue; // Уже раскрашена

            Queue<Integer> queue = new LinkedList<>();
            queue.add(startId);
            colors[startId] = 1; // Стартовая вершина принадлежит доле A

            while (!queue.isEmpty()) {
                int current = queue.poll();

                // Сохранение вершины в соответствующую долю
                if (colors[current] == 1) partA.add(current + 1);
                else partB.add(current + 1);

                // Обход всех соседей
                for (int neighbor : adjList.get(current + 1)) {
                    int neighborIndex = neighbor - 1;

                    // Если соседняя вершина ещё не посещена - раскраска в противоположный цвет
                    if (colors[neighborIndex] == 0) {
                        colors[neighborIndex] = 3 - colors[current];
                        queue.add(neighborIndex);
                    } else if (colors[neighborIndex] == colors[current]) {
                        // Если соседние вершины одного цвета — граф недвудольный
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
        if (graph.getVertexCount() % 2 != 0 || graph.getVertexCount() == 0) {
            return false;
        }
        // Проверка, что количество ребер равно n*(n-1)
        int n = graph.getVertexCount() / 2;
        return graph.getEdgeCount() == n * (n - 1);
    }

    // Проверка структуры короны
    // Каждая вершина из A соединена со всеми из B, кроме одной, которая всегда уникальна
    private boolean isCrownStructure(Map<Integer, Set<Integer>> adjList, List<Integer> partA, List<Integer> partB) {
        int n = partA.size();
        Set<Integer> partBSet = new HashSet<>(partB);
        boolean[] usedB = new boolean[n]; // Сохранение уже использованных из B

        for (int a : partA) {
            Set<Integer> neighbors = adjList.get(a);
            if (neighbors.size() != n - 1) {
                return false; // Степень вершины должна быть n-1
            }

            // Получение вершин из partB, у которых нет связи с partA
            Set<Integer> missingB = new HashSet<>(partBSet);
            missingB.removeAll(neighbors); // Разность множеств: partB \ neighbors(A)

            if (missingB.size() != 1) {
                return false; // Должно быть ровно одно отсутствующее ребро
            }
            int missing = missingB.iterator().next();
            int missingIndex = partB.indexOf(missing); // Поиск в partB индекса отсуствующей вершины

            if (usedB[missingIndex]) {
                return false; // Одна и та же вершина не может быть пропущена дважды
            }
            usedB[missingIndex] = true;
        }
        return true;
    }
}
