package graph;

/** Определяет тип графа: взвешенный/невзвешенный и ориентированный/неориентированный. */
public class TypeOfGraph {
  private final boolean weight;
  private final boolean direct;

  /**
   * Определяет свойства графа по его матрице смежности.
   *
   * @param graph матрица смежности
   */
  public TypeOfGraph(int[][] graph) {
    this.weight = isWeighted(graph);
    this.direct = isDirected(graph);
  }

  /**
   * Проверяет, является ли граф ориентированным.
   *
   * @param graph матрица смежности
   * @return true, если граф ориентированный
   */
  private boolean isDirected(int[][] graph) {
    int size = graph.length;
    for (int row = 1; row < size; row++) {
      for (int col = 0; col < row; col++) {
        if (graph[row][col] != graph[col][row]) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Проверяет, является ли граф взвешенным.
   *
   * @param graph матрица смежности
   * @return true, если граф содержит веса
   */
  private boolean isWeighted(int[][] graph) {
    for (int[] row : graph) {
      for (int val : row) {
        if (val > 1 || val < 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Возвращает информацию о наличии весов в графе.
   *
   * @return true, если граф взвешенный
   */
  public boolean isWeight() {
    return weight;
  }

  /**
   * Возвращает информацию об ориентированности графа.
   *
   * @return true, если граф ориентированный
   */
  public boolean isDirect() {
    return direct;
  }

  /**
   * Возвращает строковое представление типа графа.
   *
   * @return описание типа графа
   */
  @Override
  public String toString() {
    return "TypeOfGraph{" + "weight=" + weight + ", direct=" + direct + '}';
  }
}
