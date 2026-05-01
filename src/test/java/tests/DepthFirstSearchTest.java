package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import algorithms.GraphAlgorithms;
import graph.Graph;
import org.junit.jupiter.api.Test;

/** Набор тестов для алгоритма поиска в глубину (DFS). */
public class DepthFirstSearchTest {
  @Test
  void incorrectGraph() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.depthFirstSearch(null, 1);
        });
  }

  @Test
  void incorrectStartVertex() {
    int[][] matrix = {
      {0, 1, 0},
      {1, 0, 1},
      {0, 1, 0}
    };

    Graph graph = new Graph(matrix);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.depthFirstSearch(graph, -1);
        });

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.depthFirstSearch(graph, graph.getVerticesCount());
        });
  }

  @Test
  void simpleUndirectedGraph() {
    int[][] matrix = {
      {0, 1, 0},
      {1, 0, 1},
      {0, 1, 0}
    };

    int[] correctResult = {1, 0, 2};

    Graph graph = new Graph(matrix);

    assertArrayEquals(correctResult, GraphAlgorithms.depthFirstSearch(graph, 1));
  }

  @Test
  void directedGraph() {
    int[][] matrix = {
      {0, 4, 6, 0},
      {0, 0, 8, 4},
      {0, 0, 0, 2},
      {3, 0, 0, 0},
    };

    int[] correctResult = {0, 1, 2, 3};

    Graph graph = new Graph(matrix);

    assertArrayEquals(correctResult, GraphAlgorithms.depthFirstSearch(graph, 0));
  }

  @Test
  void graphWithLoop() {
    int[][] matrix = {
      {1, 1},
      {1, 0}
    };

    int[] correctResult = {0, 1};

    Graph graph = new Graph(matrix);

    assertArrayEquals(correctResult, GraphAlgorithms.depthFirstSearch(graph, 0));
  }

  @Test
  void singleVertexGraph() {
    int[][] matrix = {{0}};

    int[] correctResult = {0};

    Graph graph = new Graph(matrix);

    assertArrayEquals(correctResult, GraphAlgorithms.depthFirstSearch(graph, 0));
  }
}
