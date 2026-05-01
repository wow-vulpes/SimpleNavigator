package app;

/**
 * Класс Menu отвечает за вывод консольного меню приложения. Содержит список доступных операций над
 * графом и приглашение к выбору.
 */
public class Menu {
  private Menu() {}

  /** Выводит на экран меню. */
  public static void print() {
    System.out.println("\n=== GRAPH MENU ===");
    System.out.println("1. Load graph from file");
    System.out.println("2. Depth first search");
    System.out.println("3. Breadth first search");
    System.out.println("4. Shortest path between vertices (Dijkstra)");
    System.out.println("5. Shortest path between all vertices (Floyd)");
    System.out.println("6. Least spanning tree (Prim)");
    System.out.println("7. Traveling Salesman Problem");
    System.out.println("0. Exit");
    System.out.print("Choose: ");
  }
}
