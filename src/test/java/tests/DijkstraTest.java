package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import algorithms.GraphAlgorithms;
import graph.Graph;
import org.junit.jupiter.api.Test;

/** Набор тестов для алгоритма Дейкстры (поиск кратчайшего пути). */
public class DijkstraTest {
  @Test
  void incorrectGraph() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.getShortestPathBetweenVertices(null, 1, 2);
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
          GraphAlgorithms.getShortestPathBetweenVertices(graph, -1, 0);
        });

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.getShortestPathBetweenVertices(graph, graph.getVerticesCount(), 0);
        });
  }

  @Test
  void incorrectEndVertex() {
    int[][] matrix = {
      {0, 1, 0},
      {1, 0, 1},
      {0, 1, 0}
    };

    Graph graph = new Graph(matrix);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.getShortestPathBetweenVertices(graph, 0, -1);
        });

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.getShortestPathBetweenVertices(graph, 0, graph.getVerticesCount());
        });
  }

  @Test
  void simpleUndirectedGraph() {
    int[][] matrix = {
      {0, 2, 0},
      {2, 0, 1},
      {0, 1, 0}
    };

    int correctResult = 2;

    Graph graph = new Graph(matrix);

    assertEquals(correctResult, GraphAlgorithms.getShortestPathBetweenVertices(graph, 0, 1));
  }

  @Test
  void complexUndirectedGraph() {
    int[][] matrix = {
      {0, 10, 1},
      {10, 0, 1},
      {1, 1, 0}
    };

    int correctResult = 2;

    Graph graph = new Graph(matrix);

    assertEquals(correctResult, GraphAlgorithms.getShortestPathBetweenVertices(graph, 0, 1));
  }

  @Test
  void directedGraph() {
    int[][] matrix = {
      {0, 4, 8, 0},
      {0, 0, 3, 4},
      {0, 0, 0, 2},
      {3, 0, 0, 0},
    };

    int correctResult = 7;

    Graph graph = new Graph(matrix);

    assertEquals(correctResult, GraphAlgorithms.getShortestPathBetweenVertices(graph, 0, 2));
  }

  @Test
  void graphWithLoop() {
    int[][] matrix = {
      {1, 1},
      {1, 0}
    };

    int correctResult = 0;

    Graph graph = new Graph(matrix);

    assertEquals(correctResult, GraphAlgorithms.getShortestPathBetweenVertices(graph, 0, 0));
  }

  @Test
  void singleVertexGraph() {
    int[][] matrix = {{0}};

    int correctResult = 0;

    Graph graph = new Graph(matrix);

    assertEquals(correctResult, GraphAlgorithms.getShortestPathBetweenVertices(graph, 0, 0));
  }
}
