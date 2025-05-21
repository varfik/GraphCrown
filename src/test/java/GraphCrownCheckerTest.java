import ru.leti.wise.task.graph_crown.GraphCrownChecker;
import ru.leti.wise.task.graph.util.FileLoader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
public class GraphCrownCheckerTest {

    @Test
    public void testGraph1() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_odd.json");
        Assertions.assertFalse(new GraphCrownChecker().run(graph)); // Количество вершин нечётно
    }

    @Test
    public void testGraph2() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_two.json");
        Assertions.assertTrue(new GraphCrownChecker().run(graph)); // Граф из двух вершин без рёбер
    }

    @Test
    public void testGraph3() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_four.json");
        Assertions.assertTrue(new GraphCrownChecker().run(graph)); // Граф из четырёх вершин
    }

    @Test
    public void testGraph4() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_unbipartite.json");
        Assertions.assertFalse(new GraphCrownChecker().run(graph)); // Недвудольный граф
    }

    @Test
    public void testGraph5() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_directed.json");
        Assertions.assertFalse(new GraphCrownChecker().run(graph)); // Ориентированный граф
    }

    @Test
    public void testGraph6() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_true8.json");
        Assertions.assertTrue(new GraphCrownChecker().run(graph)); // Граф, соответствующий всем условиям
    }

    @Test
    public void testGraph7() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_true10.json");
        Assertions.assertTrue(new GraphCrownChecker().run(graph)); // Граф, соответствующий всем условиям с 10 вершинами
    }

    @Test
    public void testGraph8() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_big.json");
        Assertions.assertTrue(new GraphCrownChecker().run(graph)); // Большой граф (30 вершин), соответствующий всем условиям
    }

    @Test
    public void testGraph9() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_extra_edge.json");
        Assertions.assertFalse(new GraphCrownChecker().run(graph)); // Граф с лишним ребром
    }

    @Test
    public void testGraph10() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_zero.json");
        Assertions.assertFalse(new GraphCrownChecker().run(graph)); // Нулевой граф
    }

    @Test
    public void testGraph11() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_one.json");
        Assertions.assertFalse(new GraphCrownChecker().run(graph)); // Единичный граф (1 вершина)
    }

    @Test
    public void testGraph12() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_bipartite.json");
        Assertions.assertFalse(new GraphCrownChecker().run(graph)); // Двудольный граф без удаления совершенного паросочетания
    }
}
