package tests;

import static algorithms.GraphAlgorithms.solveTravelingSalesmanProblem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import graph.Graph;
import graph.TsmResult;
import org.junit.jupiter.api.Test;

/** Набор тестов для решения задачи коммивояжёра (TSP). */
public class SolveTravelingSalesmanProblemTest {
  private boolean checkVertices(int[] path) {
    boolean[] result = new boolean[path.length - 1];

    if (path[0] != path[path.length - 1]) {
      return false;
    }

    for (int i = 0; i < path.length - 1; i++) {
      if (result[i]) {
        return false;
      }
      result[i] = true;
    }

    for (boolean bool : result) {
      if (!bool) {
        return false;
      }
    }
    return true;
  }

  @Test
  void testSolveTravelingSalesmanProblemGraphNull() {
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> solveTravelingSalesmanProblem(null));

    assertEquals("Graph cannot be null.", thrown.getMessage());
  }

  @Test
  void testSolveTravelingSalesmanProblemMatrixNull() {
    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class, () -> solveTravelingSalesmanProblem(new Graph(null)));

    assertEquals("Graph cannot be null.", thrown.getMessage());
  }

  @Test
  void testSolveTravelingSalesmanProblemMatrixEmpty() {
    int[][] matrix = new int[0][0];
    Graph graph = new Graph(matrix);
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> solveTravelingSalesmanProblem(graph));

    assertEquals("Matrix cannot be empty.", thrown.getMessage());
  }

  @Test
  void testSolveTravelingsSalesmanProblemMatrixBadFormat1() {
    int[][] matrix = {
      {0, 10, 15, 20},
      {0, 0, 35, 25},
      {15, 35, 0, 30},
      {20, 25, 30, 0}
    };

    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class, () -> solveTravelingSalesmanProblem(new Graph(matrix)));

    assertEquals("The graph is not fully connected.", thrown.getMessage());
  }

  @Test
  void testSolveTravelingsSalesmanProblemMatrixBadFormat2() {
    int[][] matrix = {
      {0, 10, 15, 20},
      {10, 0, 0, 25},
      {15, 35, 0, 30},
      {20, 25, 30, 0}
    };

    IllegalArgumentException thrown =
        assertThrows(
            IllegalArgumentException.class, () -> solveTravelingSalesmanProblem(new Graph(matrix)));

    assertEquals("The graph is not fully connected.", thrown.getMessage());
  }

  @Test
  void testSolveTravelingSalesmanProblemMatrixOne() {
    int[][] matrix = {{0}};
    TsmResult tsmResult = solveTravelingSalesmanProblem(new Graph(matrix));

    assertNotNull(tsmResult);
    assertEquals(0.0, tsmResult.distance());
    assertEquals(1, tsmResult.vertices().length);
    assertEquals(0, tsmResult.vertices()[0]);
  }

  @Test
  void testSolveTravelingsSalesmanProblemMatrixTwo() {
    int[][] matrix = {
      {0, 15},
      {15, 0}
    };

    TsmResult tsmResult = solveTravelingSalesmanProblem(new Graph(matrix));

    assertNotNull(tsmResult);
    assertTrue(checkVertices(tsmResult.vertices()));
    assertEquals(30.0, tsmResult.distance());
    assertEquals(3, tsmResult.vertices().length);
  }

  @Test
  void testSolveTravelingsSalesmanProblemMatrixThree() {
    int[][] matrix = {
      {0, 15, 10},
      {15, 0, 5},
      {10, 5, 0}
    };

    TsmResult tsmResult = solveTravelingSalesmanProblem(new Graph(matrix));

    assertNotNull(tsmResult);
    assertTrue(checkVertices(tsmResult.vertices()));
    assertEquals(30.0, tsmResult.distance());
    assertEquals(4, tsmResult.vertices().length);
  }

  @Test
  void testSolveTravelingSalesmanProblemMatrixFour() {
    int[][] matrix = {
      {0, 10, 15, 20},
      {10, 0, 35, 25},
      {15, 35, 0, 30},
      {20, 25, 30, 0}
    };

    Graph graph = new Graph(matrix);

    TsmResult tsmResult = solveTravelingSalesmanProblem(graph);

    assertTrue(checkVertices(tsmResult.vertices()));
    assertNotNull(tsmResult);
    assertEquals(80.0, tsmResult.distance());
  }

  @Test
  void testSolveTravelingSalesmanProblemMatrixTen() {
    int[][] matrix = {
      {0, 2, 9, 10, 15, 15, 15, 9, 15, 15},
      {2, 0, 6, 4, 7, 7, 2, 7, 7, 7},
      {9, 6, 0, 8, 10, 10, 10, 10, 10, 15},
      {10, 4, 8, 0, 11, 6, 11, 11, 10, 7},
      {15, 7, 10, 11, 0, 3, 2, 9, 7, 11},
      {15, 7, 10, 6, 3, 0, 2, 7, 12, 12},
      {15, 2, 10, 11, 2, 2, 0, 9, 20, 15},
      {9, 7, 10, 11, 9, 7, 9, 0, 9, 9},
      {15, 7, 10, 10, 7, 12, 20, 9, 0, 15},
      {15, 7, 15, 7, 11, 12, 15, 9, 15, 0}
    };

    TsmResult tsmResult = solveTravelingSalesmanProblem(new Graph(matrix));

    assertNotNull(tsmResult);
    assertTrue(checkVertices(tsmResult.vertices()));
    assertEquals(11, tsmResult.vertices().length);
  }
}
