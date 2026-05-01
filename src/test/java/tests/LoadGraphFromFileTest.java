package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import graph.Graph;
import org.junit.jupiter.api.Test;

/** Набор тестов для загрузки графа из файла. */
public class LoadGraphFromFileTest {

  // позитивные тесты
  @Test
  void shouldWorkWithUnweightedMatrix() {
    int[][] loadedMatrix =
        Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_1.txt");
    int[][] expected =
        new int[][] {
          {0, 1, 1, 1},
          {0, 0, 1, 1},
          {0, 0, 0, 1},
          {1, 0, 0, 0}
        };
    assertArrayEquals(
        expected,
        loadedMatrix,
        "Стандартные ввод данных для невзвешенных графов работает некорректно");
  }

  @Test
  void shouldWorkWithWeightedMatrix() {
    int[][] loadedMatrix =
        Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_2.txt");
    int[][] expected =
        new int[][] {
          {0, 255, 192, 0},
          {172, 0, 168, 0},
          {11, 11, 0, 127},
          {1, 0, 28, 0}
        };
    assertArrayEquals(
        expected,
        loadedMatrix,
        "Стандартные ввод данных для взвешенных графов работает некорректно");
  }

  @Test
  void shouldWorkWithDifferentSpace() {
    int[][] loadedMatrix =
        Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_3.txt");
    int[][] expected =
        new int[][] {
          {0, 1, 1, 1},
          {0, 0, 1, 1},
          {0, 0, 0, 1},
          {1, 0, 0, 0}
        };
    assertArrayEquals(
        expected, loadedMatrix, "Ввод даных с лишними пробелами отрабатывает некорректно");
  }

  @Test
  void shouldWorkWithOneVertice() {
    int[][] loadedMatrix =
        Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_4.txt");
    int[][] expected = new int[][] {{0}};
    assertArrayEquals(
        expected, loadedMatrix, "Ввод графа из одной вершины отрабатывает некорректно");
  }

  @Test
  void shouldWorkWithExcessSpacesOnEdge() {
    int[][] loadedMatrix =
        Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_5.txt");
    int[][] expected =
        new int[][] {
          {0, 1, 1, 1},
          {0, 0, 1, 1},
          {0, 0, 0, 1},
          {1, 0, 0, 0}
        };
    assertArrayEquals(
        expected, loadedMatrix, "Ввод графа с лишними пробелами на краях отрабатывает некорректно");
  }

  // негативные тесты
  @Test
  void shouldNotWorkIfColsLessThenVertices() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_6.txt"),
        "Должны выбрасывать исключение, если количество столбцов меньше количества вершин");
  }

  @Test
  void shouldNotWorkIfColsMoreThenVertices() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_7.txt"),
        "Выбрасывается исключение, если количество столбцов больше количества вершин");
  }

  @Test
  void shouldNotWorkIfRowsLessThenVertices() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_8.txt"),
        "Выбрасывается исключение, если файл оборвался из-за недостатка строк");
  }

  @Test
  void shouldNotWorkIfFileHasIncorrectSymbol() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Graph.loadGraphFromFile("build/test/java/resources/loadGraphFromFile/input/file_9.txt"),
        "Выбрасывается исключение, если на месте вершин/ребер спецсимвол/текст/дробное число");
  }

  @Test
  void shouldNotWorkIfNumberOfVerticesIsNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Graph.loadGraphFromFile(
                "build/test/java/resources/loadGraphFromFile/input/file_10.txt"),
        "Выбрасывается исключение, если количество вершин отрицательное");
  }

  @Test
  void shouldNotWorkIfNumberOfVerticesIsZero() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Graph.loadGraphFromFile(
                "build/test/java/resources/loadGraphFromFile/input/file_11.txt"),
        "Выбрасывается исключение, если количество вершин равно нулю");
  }

  @Test
  void shouldNotWorkIfFileIsNotExist() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Graph.loadGraphFromFile(
                "build/test/java/resources/loadGraphFromFile/input/file_11.1.txt"),
        "Выбрасывается исключение, если файла не существует");
  }

  @Test
  void shouldNotWorkIfFileIsEmpty() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Graph.loadGraphFromFile(
                "build/test/java/resources/loadGraphFromFile/input/file_12.txt"),
        "Выбрасывается исключение, если файл пуст");
  }
}
