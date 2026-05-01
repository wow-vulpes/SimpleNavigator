package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * Класс Graph представляет граф в виде матрицы смежности. Поддерживает загрузку графа из файла,
 * экспорт в DOT-формат и доступ к базовым операциям над графом.
 */
public class Graph {
  private final int[][] graph;

  /** Создаёт граф на основе матрицы смежности. */
  public Graph(int[][] graph) {
    this.graph = graph;
  }

  /**
   * Загружает граф из файла и возвращает матрицу смежности.
   *
   * @param filename путь к файлу
   * @return матрица смежности графа
   * @throws IllegalArgumentException если файл некорректный или пустой
   */
  public static int[][] loadGraphFromFile(String filename) {
    if (filename.isEmpty()) {
      throw new IllegalArgumentException("File is empty");
    }

    Path path = Path.of(filename);
    int[][] adjacencyMatrix;

    try (BufferedReader br = Files.newBufferedReader(path)) {
      String firstLine = br.readLine();

      if (firstLine == null || firstLine.isEmpty()) {
        throw new IllegalArgumentException(
            "First line is empty or has invalid value: " + path.toAbsolutePath());
      }

      int vertices;

      try {
        vertices = Integer.parseInt(firstLine);
      } catch (NumberFormatException e) {
        throw new NumberFormatException("First line is not a valid value");
      }

      if (vertices <= 0) {
        throw new NumberFormatException("Number of vertices must be positive");
      }

      adjacencyMatrix = new int[vertices][vertices];

      for (int row = 0; row < vertices; row++) {
        String line = br.readLine();

        if (line == null) {
          throw new IllegalArgumentException("Unexpected end of file");
        }
        String[] parts = line.trim().split("\\s+");

        if (parts.length != vertices) {
          throw new IllegalArgumentException(
              String.format(
                  "Row %d has %d columns, expected %d: %s",
                  row + 1, parts.length, vertices, path.toAbsolutePath()));
        }
        for (int col = 0; col < vertices; col++) {
          try {
            adjacencyMatrix[row][col] = Integer.parseInt(parts[col]);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                String.format(
                    "Invalid number at row %d, column %d: '%s'", row + 1, col + 1, parts[col]),
                e);
          }
        }
      }
      return adjacencyMatrix;
    } catch (NoSuchFileException e) {
      throw new IllegalArgumentException("File is not found: " + path.toAbsolutePath(), e);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Incorrect data in the file: " + path.toAbsolutePath(), e);
    } catch (IOException e) {
      throw new java.io.UncheckedIOException("Other errors" + path.toAbsolutePath(), e);
    }
  }

  /**
   * Экспортирует граф в DOT-формат в указанный файл.
   *
   * @param filename имя выходного файла
   */
  public void exportGraphToDot(String filename) {

    TypeOfGraph type = new TypeOfGraph(graph);
    String edgeDirection = type.isDirect() ? "->" : "--";

    int size = graph.length;

    try (PrintWriter writer = new PrintWriter(new File(filename))) {

      if (type.isDirect()) {
        writer.println("digraph graphname {");

        for (int row = 0; row < size; row++) {
          for (int col = 0; col < size; col++) {
            if (graph[row][col] != 0) {
              writeToDot(writer, type.isWeight(), graph[row][col], row, col, edgeDirection);
            }
          }
        }

      } else {
        writer.println("graph graphname {");

        for (int row = 0; row < size; row++) {
          for (int col = row + 1; col < size; col++) {
            if (graph[row][col] != 0) {
              writeToDot(writer, type.isWeight(), graph[row][col], row, col, edgeDirection);
            }
          }
        }
      }

      writer.print("}");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Записывает ребро графа в DOT-файл.
   *
   * @param writer поток записи
   * @param isWeight есть ли веса у рёбер
   * @param weight вес ребра
   * @param first начальная вершина
   * @param second конечная вершина
   * @param direction направление ребра
   */
  private static void writeToDot(
      PrintWriter writer, boolean isWeight, int weight, int first, int second, String direction) {
    if (isWeight) {
      writer.printf("v%d %s v%d [label=\"%d\"];%n", first, direction, second, weight);
    } else {
      writer.printf("v%d %s v%d;%n", first, direction, second);
    }
  }

  /**
   * Возвращает матрицу смежности графа.
   *
   * @return матрица графа
   */
  public int[][] getGraph() {
    return graph;
  }

  /**
   * Возвращает количество вершин в графе.
   *
   * @return число вершин
   */
  public int getVerticesCount() {
    return graph.length;
  }

  /**
   * Возвращает значение ребра между вершинами i и j.
   *
   * @param i индекс первой вершины
   * @param j индекс второй вершины
   * @return вес ребра или 0, если ребра нет
   */
  public int getEdge(int i, int j) {
    return graph[i][j];
  }
}
