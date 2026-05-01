package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import graph.Graph;
import graph.TsmResult;
import graph.TypeOfGraph;
import structures.S21Queue;
import structures.S21Stack;

/**
 * Утилитный класс с реализациями алгоритмов для работы с графами: поиск в ширину (BFS), поиск в
 * глубину (DFS), алгоритм Дейкстры, Флойда–Уоршелла, Прима и решение задачи коммивояжёра.
 */
public class GraphAlgorithms {

  private static final int NUM_ITERATIONS = 100;
  private static final double ALPHA = 1.0;
  private static final double BETA = 2.0;
  private static final double EVAPORATION_RATE = 0.5;
  private static final double Q = 100.0;

  private GraphAlgorithms() {}

  private static final int INF = Integer.MAX_VALUE / 2;

  /**
   * Выполняет поиск в ширину (BFS).
   *
   * @param graph граф
   * @param startVertex начальная вершина
   * @return массив вершин в порядке обхода
   */
  public static int[] breadthFirstSearch(Graph graph, int startVertex) {
    S21Queue queue = new S21Queue();

    int[][] matrix = graph.getGraph();
    int length = matrix.length;
    boolean[] visited = new boolean[length];
    int[] result = new int[length];

    int index = 0;
    result[index++] = startVertex;
    visited[startVertex] = true;
    queue.push(startVertex);

    while (!queue.isEmpty()) {
      int u = queue.pop();

      for (int v = 0; v < length; v++) {
        if (matrix[u][v] != 0 && !visited[v]) {
          visited[v] = true;
          queue.push(v);
          result[index++] = v;
        }
      }
    }

    return result;
  }

  /**
   * Выполняет поиск в глубину (DFS).
   *
   * @param graph граф
   * @param startVertex начальная вершина
   * @return массив вершин в порядке обхода
   * @throws IllegalArgumentException если входные данные некорректны
   */
  public static int[] depthFirstSearch(Graph graph, int startVertex) {
    if (graph == null || (startVertex < 0 || startVertex >= graph.getVerticesCount())) {
      throw new IllegalArgumentException("Invalid data");
    }

    int verticesCount = graph.getVerticesCount();
    boolean[] visited = new boolean[verticesCount];

    S21Stack vertices = new S21Stack();
    vertices.push(startVertex);

    int[] result = new int[verticesCount];
    int index = 0;
    while (!vertices.isEmpty()) {
      int vertex = vertices.pop();

      if (!visited[vertex]) {
        visited[vertex] = true;
        result[index++] = vertex;

        for (int j = verticesCount - 1; j >= 0; j--) {
          if (graph.getEdge(vertex, j) > 0 && !visited[j]) {
            vertices.push(j);
          }
        }
      }
    }

    return result;
  }

  /**
   * Находит кратчайшее расстояние между двумя вершинами с использованием алгоритма Дейкстры.
   *
   * @param graph граф
   * @param vertex1 начальная вершина
   * @param vertex2 конечная вершина
   * @return длина кратчайшего пути
   * @throws IllegalArgumentException если входные данные некорректны
   */
  public static int getShortestPathBetweenVertices(Graph graph, int vertex1, int vertex2) {
    if (graph == null
        || (vertex1 < 0 || vertex1 >= graph.getVerticesCount())
        || (vertex2 < 0 || vertex2 >= graph.getVerticesCount())) {
      throw new IllegalArgumentException("Invalid data");
    }

    int verticesCount = graph.getVerticesCount();

    int[] dist = new int[verticesCount];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[vertex1] = 0;

    boolean[] visited = new boolean[verticesCount];

    for (int i = 0; i < verticesCount; i++) {
      // поиск минимальной вершины
      int minIndex = -1;
      for (int vertex = 0; vertex < verticesCount; vertex++) {
        if (!visited[vertex] && (minIndex == -1 || dist[vertex] < dist[minIndex])) {
          minIndex = vertex;
        }
      }

      if (minIndex == -1 || minIndex == vertex2) {
        break;
      }

      // посетили ее
      visited[minIndex] = true;

      for (int vertex = 0; vertex < verticesCount; vertex++) {
        if (!visited[vertex]) {
          int weight = graph.getEdge(minIndex, vertex);
          if (weight > 0
              && dist[minIndex] != Integer.MAX_VALUE
              && dist[minIndex] + weight < dist[vertex]) {
            // релаксация
            dist[vertex] = dist[minIndex] + weight;
          }
        }
      }
    }

    return dist[vertex2];
  }

  /**
   * Находит кратчайшие пути между всеми парами вершин с использованием алгоритма Флойда–Уоршелла.
   *
   * @param graph граф
   * @return матрица кратчайших расстояний
   */
  public static int[][] getShortestPathsBetweenAllVertices(Graph graph) {
    int[][] matrix = graph.getGraph();
    int length = matrix.length;
    for (int k = 0; k < length; k++) {
      for (int[] ints : matrix) {
        for (int j = 0; j < length; j++) {
          if (ints[k] != INF && matrix[k][j] != INF) {
            long newDist = (long) ints[k] + matrix[k][j];
            if (newDist < ints[j]) {
              ints[j] = (int) newDist;
            }
          }
        }
      }
    }
    return matrix;
  }

  /**
   * Строит минимальное остовное дерево графа с использованием алгоритма Прима.
   *
   * @param graph граф (неориентированный)
   * @return матрица смежности остовного дерева
   * @throws IllegalArgumentException если граф некорректный или ориентированный
   */
  public static int[][] getLeastSpanningTree(Graph graph) {
    if (graph == null) {
      throw new IllegalArgumentException("Invalid data");
    }

    int[][] matrix = graph.getGraph();
    TypeOfGraph typeOfGraph = new TypeOfGraph(matrix);

    if (typeOfGraph.isDirect()) {
      throw new IllegalArgumentException("Graph must be undirected");
    }

    int verticesCount = graph.getVerticesCount();

    int[] parent = new int[verticesCount];
    int[] minEdge = new int[verticesCount];

    parent[0] = -1;
    Arrays.fill(minEdge, Integer.MAX_VALUE);
    minEdge[0] = 0;

    boolean[] inLst = new boolean[verticesCount];
    for (int n = 0; n < verticesCount; n++) {
      // беру вершину с меньшим ребром
      int v1 = -1;
      for (int i = 0; i < verticesCount; i++) {
        if (!inLst[i] && (v1 == -1 || minEdge[i] < minEdge[v1])) {
          v1 = i;
        }
      }
      inLst[v1] = true;

      // обновляю соседей
      for (int v2 = 0; v2 < verticesCount; v2++) {
        if (!inLst[v2] && matrix[v1][v2] > 0) {
          if (matrix[v1][v2] < minEdge[v2]) {
            minEdge[v2] = matrix[v1][v2];
            parent[v2] = v1;
          }
        }
      }
    }

    int[][] result = new int[verticesCount][verticesCount];
    for (int i = 1; i < verticesCount; i++) {
      int p = parent[i];
      result[i][p] = matrix[i][p];
      result[p][i] = matrix[i][p];
    }

    return result;
  }

  /**
   * Решает задачу коммивояжёра с помощью муравьиного алгоритма.
   *
   * @param graph граф
   * @return результат, содержащий путь и его длину
   * @throws IllegalArgumentException если граф некорректен
   * @throws IllegalStateException если решение не найдено
   */
  public static TsmResult solveTravelingSalesmanProblem(Graph graph) {

    if (Objects.isNull(graph) || Objects.isNull(graph.getGraph())) {
      throw new IllegalArgumentException("Graph cannot be null.");
    }

    int[][] matrix = graph.getGraph();

    if (matrix.length == 0) {
      throw new IllegalArgumentException("Matrix cannot be empty.");
    }

    if (matrix.length == 1 && matrix[0].length == 1) {
      return new TsmResult(new int[1], 0.0);
    }

    int n = matrix.length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != j && matrix[i][j] == 0) {
          throw new IllegalArgumentException("The graph is not fully connected.");
        }
      }
    }

    double[][] pheromones = new double[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        pheromones[i][j] = 1.0;
      }
    }

    TsmResult bestSolution = null;
    double bestDistance = Double.MAX_VALUE;

    for (int iteration = 0; iteration < NUM_ITERATIONS; iteration++) {
      List<int[]> antSolutions = new ArrayList<>();

      for (int i = 0; i < n; i++) {
        int[] path = solveAnt(matrix, pheromones, n, i);
        if (path != null) {
          antSolutions.add(path);
        }
      }

      updatePheromones(pheromones, antSolutions, matrix, n);

      for (int[] path : antSolutions) {
        double distance = calculateDistance(path, matrix);
        if (distance < bestDistance) {
          bestDistance = distance;
          bestSolution = new TsmResult(path.clone(), distance);
        }
      }
    }

    if (bestSolution == null) {
      throw new IllegalStateException("Unable to find TSP solution.");
    }

    return bestSolution;
  }

  /** Строит маршрут для одного "муравья". */
  private static int[] solveAnt(int[][] matrix, double[][] pheromones, int n, int ant) {
    int[] path = new int[n + 1];
    boolean[] visited = new boolean[n];
    path[0] = ant;
    visited[ant] = true;

    for (int i = 1; i < n; i++) {
      int next = chooseNextCity(ant, visited, matrix, pheromones, n);
      if (next == -1) {
        return null;
      }

      path[i] = next;
      visited[next] = true;
      ant = next;
    }

    if (matrix[ant][path[0]] == 0) {
      return null;
    }
    path[n] = path[0];

    return path;
  }

  /** Выбирает следующую вершину. */
  private static int chooseNextCity(
      int current, boolean[] visited, int[][] matrix, double[][] pheromones, int n) {
    double[] probabilities = new double[n];
    double total = 0.0;

    for (int i = 0; i < n; i++) {
      if (!visited[i] && matrix[current][i] != 0) {
        double pheromone = Math.pow(pheromones[current][i], ALPHA);
        double visibility = 1.0 / matrix[current][i];
        double prob = pheromone * Math.pow(visibility, BETA);
        probabilities[i] = prob;
        total += prob;
      }
    }

    if (total == 0) {
      return -1;
    }

    double rand = ThreadLocalRandom.current().nextDouble(total);
    double sum = 0.0;
    for (int i = 0; i < n; i++) {
      sum += probabilities[i];
      if (sum >= rand) {
        return i;
      }
    }

    return -1;
  }

  /** Обновляет матрицу феромонов. */
  private static void updatePheromones(
      double[][] pheromones, List<int[]> solutions, int[][] matrix, int n) {

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        pheromones[i][j] *= (1.0 - EVAPORATION_RATE);
      }
    }

    for (int[] path : solutions) {
      double distance = calculateDistance(path, matrix);
      double pheromoneDeposit = Q / distance;

      for (int i = 0; i < path.length - 1; i++) {
        int from = path[i];
        int to = path[i + 1];
        pheromones[from][to] += pheromoneDeposit;
      }
    }
  }

  /** Вычисляет длину маршрута. */
  private static double calculateDistance(int[] path, int[][] matrix) {
    double total = 0.0;
    for (int i = 0; i < path.length - 1; i++) {
      total += matrix[path[i]][path[i + 1]];
    }
    return total;
  }
}
