package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import algorithms.GraphAlgorithms;
import graph.Graph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BreadthFirstSearchTest {

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5})
  @DisplayName("BFS - поиск в ширину")
  void testBfs(int fileNumber) {
    int[][] inputMatrix =
        Graph.loadGraphFromFile("build/test/java/resources/BFS/input/file_" + fileNumber + ".txt");
    Graph graph = new Graph(inputMatrix);

    int[] actualMatrix = GraphAlgorithms.breadthFirstSearch(graph, fileNumber - 1);

    int[] expectedMatrix =
        loadArrayFromFile(
            "build/test/java/resources/BFS/expected/file_" + fileNumber + "_expected.txt");

    assertArrayEquals(
        expectedMatrix,
        actualMatrix,
        "Результат для file_" + fileNumber + ".txt не совпадает с ожидаемым");
  }

  static int[] loadArrayFromFile(String filename) {
    Path path = Path.of(filename);
    int[] array;
    try (BufferedReader br = Files.newBufferedReader(path)) {
      int vertices = Integer.parseInt(br.readLine());
      array = new int[vertices];

      String[] parts = br.readLine().trim().split("\\s+");

      for (int i = 0; i < vertices; i++) {
        array[i] = Integer.parseInt(parts[i]);
      }
      return array;
    } catch (IOException e) {
      throw new java.io.UncheckedIOException("Errors" + path.toAbsolutePath(), e);
    }
  }
}
