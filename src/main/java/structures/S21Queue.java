package structures;

import java.util.NoSuchElementException;

/** Реализация очереди (FIFO) на основе циклического массива. */
public class S21Queue {
  private int[] array;
  private int frontIndex;
  private int backIndex;
  private int size;

  /** Создаёт пустую очередь с начальной ёмкостью 4. */
  public static S21Queue queue() {
    return new S21Queue();
  }

  /**
   * Фабричный метод создания очереди.
   */
  public S21Queue() {
    this.array = new int[4];
    this.frontIndex = 0;
    this.backIndex = -1;
    this.size = 0;
  }

  /**
   * Добавляет элемент в конец очереди.
   *
   * @param value добавляемое значение
   */
  public void push(int value) {
    if (size == array.length) {
      int[] newArray = new int[array.length * 2];
      for (int i = 0; i < size; i++) {
        newArray[i] = array[(frontIndex + i) % array.length];
      }
      array = newArray;
      frontIndex = 0;
      backIndex = size - 1;
    }
    backIndex = (backIndex + 1) % array.length;
    array[backIndex] = value;
    size++;
  }

  /**
   * Удаляет и возвращает первый элемент очереди.
   *
   * @return значение из начала очереди
   * @throws NoSuchElementException если очередь пуста
   */
  public int pop() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    int value = array[frontIndex];
    frontIndex = (frontIndex + 1) % array.length;
    size--;
    return value;
  }

  /**
   * Возвращает первый элемент без удаления.
   *
   * @return первый элемент очереди
   * @throws NoSuchElementException если очередь пуста
   */
  public int front() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return array[frontIndex];
  }

  /**
   * Возвращает последний добавленный элемент.
   *
   * @return последний элемент очереди
   * @throws NoSuchElementException если очередь пуста
   */
  public int back() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return array[backIndex];
  }

  /**
   * Проверяет, пуста ли очередь.
   *
   * @return true, если очередь не содержит элементов
   */
  public boolean isEmpty() {
    return size == 0;
  }
}
