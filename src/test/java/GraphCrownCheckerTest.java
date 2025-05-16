import ru.leti.GraphCrownChecker;
import ru.leti.wise.task.graph.util.FileLoader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GraphCrownCheckerTest {

    @Test
    public void testGraph1() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_odd.json");
        assertThat(new IsMaximallyPlanar().run(graph)).isFalse(); // Количество вершин нечётно
    }

    @Test
    public void testGraph2() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_two.json");
        assertThat(new IsMaximallyPlanar().run(graph)).isTrue(); // Граф из двух вершин без рёбре
    }

    @Test
    public void testGraph3() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_four.json");
        assertThat(new IsMaximallyPlanar().run(graph)).isTrue(); // Граф из четырёх вершин
    }

    @Test
    public void testGraph4() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_unbipartite.json");
        assertThat(new IsMaximallyPlanar().run(graph)).isFalse(); // Недвудольный граф
    }

    @Test
    public void testGraph5() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_directed.json");
        assertThat(new IsMaximallyPlanar().run(graph)).isFalse(); // Ориентированный граф
    }

    @Test
    public void testGraph6() throws FileNotFoundException {
        var graph = FileLoader.loadGraphFromJson("src/test/resources/graph_true.json");
        assertThat(new IsMaximallyPlanar().run(graph)).isTrue(); // Граф, соответствующий всем условиям
    }
}
