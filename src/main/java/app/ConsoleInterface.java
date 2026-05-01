package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import algorithms.GraphAlgorithms;
import graph.Graph;
import graph.TsmResult;

/**
 * Консольный интерфейс для работы с графом и алгоритмами.
 *
 * <p>Позволяет загружать граф и выполнять основные операции над ним.
 */
public class ConsoleInterface {

  /** Создаёт экземпляр интерфейса. */
  public ConsoleInterface() {}

  private Graph graph = null;
  private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  /** Запускает основной цикл программы. */
  public void start() {
    while (true) {
      clearScreen();
      Menu.print();

      try {
        int choice = readInt();

        switch (choice) {
          case 1 -> loadGraph();
          case 2 -> dfs();
          case 3 -> bfs();
          case 4 -> shortestPath();
          case 5 -> allVerticesShortestPaths();
          case 6 -> lst();
          case 7 -> tsp();
          case 0 -> {
            System.out.println("Exit...");
            return;
          }
          default -> System.out.println("Invalid option");
        }

        pressEnterToContinue();
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        if (!pressEnterToContinue()) {
          return;
        }
      }
    }
  }

  private int readInt() throws Exception {
    return Integer.parseInt(reader.readLine().trim());
  }

  private boolean pressEnterToContinue() {
    try {
      System.out.println("\nPress ENTER to continue...");
      reader.readLine();
      return true;
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  /**
   * Загружает граф из файла.
   *
   * @throws Exception при ошибке чтения файла
   */
  private void loadGraph() throws Exception {
    System.out.print("Enter filename: ");
    String filename = reader.readLine().trim();

    graph = new Graph(Graph.loadGraphFromFile(filename));
    System.out.println("Graph loaded successfully");
  }

  /**
   * Выполняет обход в глубину.
   *
   * @throws Exception при ошибке ввода или отсутствии графа
   */
  private void dfs() throws Exception {
    checkGraph();

    System.out.print("Start vertex: ");
    int start = readInt();
    checkVertex(start);

    int[] res = GraphAlgorithms.depthFirstSearch(graph, start);
    printArray(res);
  }

  /**
   * Выполняет обход в ширину.
   *
   * @throws Exception при ошибке ввода или отсутствии графа
   */
  private void bfs() throws Exception {
    checkGraph();

    System.out.print("Start vertex: ");
    int start = readInt();
    checkVertex(start);

    int[] res = GraphAlgorithms.breadthFirstSearch(graph, start);
    printArray(res);
  }

  /**
   * Находит кратчайший путь между вершинами.
   *
   * @throws Exception при ошибке ввода или отсутствии графа
   */
  private void shortestPath() throws Exception {
    checkGraph();

    System.out.print("Start: ");
    int v1 = readInt();
    checkVertex(v1);

    System.out.print("End: ");
    int v2 = readInt();
    checkVertex(v2);

    System.out.println(GraphAlgorithms.getShortestPathBetweenVertices(graph, v1, v2));
  }

  /** Вычисляет кратчайшие пути между всеми вершинами. */
  private void allVerticesShortestPaths() {
    checkGraph();

    printMatrix(GraphAlgorithms.getShortestPathsBetweenAllVertices(graph));
  }

  /** Строит минимальное остовное дерево. */
  private void lst() {
    checkGraph();

    printMatrix(GraphAlgorithms.getLeastSpanningTree(graph));
  }

  /** Решает задачу коммивояжёра. */
  private void tsp() {
    checkGraph();

    TsmResult result = GraphAlgorithms.solveTravelingSalesmanProblem(graph);

    printArray(result.vertices());
    System.out.println("Distance: " + result.distance());
  }

  private void checkGraph() {
    if (graph == null) {
      throw new IllegalStateException("Graph is not loaded");
    }
  }

  private void checkVertex(int vertex) {
    if (vertex < 0 || vertex >= graph.getVerticesCount()) {
      throw new IllegalArgumentException("Incorrect vertex");
    }
  }

  private void printArray(int[] arr) {
    for (int v : arr) {
      System.out.print(v + " ");
    }
    System.out.println();
  }

  private void printMatrix(int[][] m) {
    for (int[] row : m) {
      for (int v : row) {
        System.out.print(v + " ");
      }
      System.out.println();
    }
  }

  private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
