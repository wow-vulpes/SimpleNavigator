package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import algorithms.GraphAlgorithms;
import graph.Graph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Набор тестов для алгоритма Флойда–Уоршелла (все кратчайшие пути). */
public class FloydWarshallTest {

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5})
  @DisplayName("Floyd-Warshall - поиск всех кратчайших путей между вершинами")
  void testShortestPathsForAllFiles(int fileNumber) {

    int[][] inputMatrix =
        Graph.loadGraphFromFile("build/test/java/resources/FW/input/file_" + fileNumber + ".txt");
    Graph graph = new Graph(inputMatrix);

    int[][] actualMatrix = GraphAlgorithms.getShortestPathsBetweenAllVertices(graph);

    int[][] expectedMatrix =
        Graph.loadGraphFromFile(
            "build/test/java/resources/FW/expected/file_" + fileNumber + "_expected.txt");

    assertArrayEquals(
        expectedMatrix,
        actualMatrix,
        "Результат для file_" + fileNumber + ".txt не совпадает с ожидаемым");
  }
}
