package structures;

import java.util.Arrays;
import java.util.EmptyStackException;

/** Реализация стека (LIFO) на основе динамического массива. */
public class S21Stack {
  private int[] array;
  private int topIndex;

  /** Создаёт пустой стек с начальной ёмкостью 4. */
  public static S21Stack stack() {
    return new S21Stack();
  }

  /** Фабричный метод создания стека. */
  public S21Stack() {
    this.array = new int[4];
    this.topIndex = -1;
  }

  /**
   * Добавляет элемент на вершину стека.
   *
   * @param value добавляемое значение
   */
  public void push(int value) {
    if (topIndex + 1 == array.length) {
      array = Arrays.copyOf(array, array.length * 2);
    }
    array[++topIndex] = value;
  }

  /**
   * Удаляет и возвращает верхний элемент стека.
   *
   * @return верхний элемент
   * @throws EmptyStackException если стек пуст
   */
  public int pop() {
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    return array[topIndex--];
  }

  /**
   * Возвращает верхний элемент без удаления.
   *
   * @return верхний элемент стека
   * @throws EmptyStackException если стек пуст
   */
  public int top() {
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    return array[topIndex];
  }

  /**
   * Проверяет, пуст ли стек.
   *
   * @return true, если стек не содержит элементов
   */
  public boolean isEmpty() {
    return topIndex == -1;
  }
}
