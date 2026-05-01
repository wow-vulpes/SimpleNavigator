package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import algorithms.GraphAlgorithms;
import graph.Graph;
import org.junit.jupiter.api.Test;

/** Набор тестов для алгоритма построения минимального остовного дерева. */
public class LeastSpanningTreeTest {
  @Test
  void nullGraph() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.getLeastSpanningTree(null);
        });
  }

  @Test
  void directedGraph() {
    int[][] matrix = {
      {0, 4, 8, 0},
      {0, 0, 3, 4},
      {0, 0, 0, 2},
      {3, 0, 0, 0},
    };

    Graph graph = new Graph(matrix);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          GraphAlgorithms.getLeastSpanningTree(graph);
        });
  }

  @Test
  void undirectedGraph_1() {
    int[][] matrix = {
      {0, 2, 0, 5, 7},
      {2, 0, 2, 0, 1},
      {0, 2, 0, 3, 4},
      {5, 0, 3, 0, 6},
      {7, 1, 4, 6, 0},
    };

    int[][] correctResult = {
      {0, 2, 0, 0, 0},
      {2, 0, 2, 0, 1},
      {0, 2, 0, 3, 0},
      {0, 0, 3, 0, 0},
      {0, 1, 0, 0, 0}
    };

    Graph graph = new Graph(matrix);

    assertArrayEquals(correctResult, GraphAlgorithms.getLeastSpanningTree(graph));
  }

  @Test
  void undirectedGraph_2() {
    int[][] matrix = {
      {0, 3, 1, 6},
      {3, 0, 5, 0},
      {1, 5, 0, 4},
      {6, 0, 4, 0}
    };

    int[][] correctResult = {
      {0, 3, 1, 0},
      {3, 0, 0, 0},
      {1, 0, 0, 4},
      {0, 0, 4, 0}
    };

    Graph graph = new Graph(matrix);

    assertArrayEquals(correctResult, GraphAlgorithms.getLeastSpanningTree(graph));
  }

  @Test
  void graphWithLoop() {
    int[][] matrix = {
      {0, 1, 0},
      {1, 0, 0},
      {0, 0, 1},
    };

    int[][] correctResult = {
      {0, 1, 0},
      {1, 0, 0},
      {0, 0, 0},
    };

    Graph graph = new Graph(matrix);

    assertArrayEquals(correctResult, GraphAlgorithms.getLeastSpanningTree(graph));
  }

  @Test
  void singleVertexGraph() {
    int[][] matrix = {{0}};

    int[][] correctResult = {
      {0},
    };

    Graph graph = new Graph(matrix);

    assertArrayEquals(correctResult, GraphAlgorithms.getLeastSpanningTree(graph));
  }
}
