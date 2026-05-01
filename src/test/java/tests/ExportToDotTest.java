package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import graph.Graph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Набор тестов для экспорта графа в DOT-формат. */
public class ExportToDotTest {

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5})
  @DisplayName("Занесение матрицы в файл в dot формате")
  void shouldReturnCorrectDotToFile(int fileNumber) {
    int[][] inputMatrix =
        Graph.loadGraphFromFile(
            "build/test/java/resources/exportToDot/input/file_" + fileNumber + ".txt");
    Graph graph = new Graph(inputMatrix);
    graph.exportGraphToDot(
        "build/test/java/resources/exportToDot/output/file_" + fileNumber + ".txt");

    try {
      String actual =
          Files.readString(
              Path.of("build/test/java/resources/exportToDot/output/file_" + fileNumber + ".txt"));
      String expected =
          Files.readString(
              Path.of(
                  "build/test/java/resources/exportToDot/expected/file_"
                      + fileNumber
                      + "_expected.txt"));
      assertEquals(expected, actual, "Вывод файла file_" + fileNumber + " невалиден");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
