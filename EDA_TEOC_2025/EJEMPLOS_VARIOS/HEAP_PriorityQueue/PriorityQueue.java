import java.util.*;
public class PriorityQueue<T extends Comparable<T>> {
  private Heap<Prioridad<T>> heap;

  public PriorityQueue() {
      heap = new Heap<>();
  }

  public void enqueue(T elemento, int prioridad) {
      heap.offer(new Prioridad<>(elemento, prioridad));
  }

  public T dequeue() {
      return heap.poll() == null? null : heap.poll().getElemento();
  }

  public T front() {
      return heap.getRaiz() == null? null : heap.getRaiz().getElemento();
  }

  public T back() {
      T min = null;
      for (Prioridad<T> elemento : heap.getHeap()) {
          if (min == null || elemento.getElemento().compareTo(min) < 0) {
              min = elemento.getElemento();
          }
      }
      return min;
  }

  public ArrayList<T> getPriorityQueue() {
      ArrayList<T> cola = new ArrayList<>();
      for (Prioridad<T> elemento : heap.getHeap()) {
          cola.add(elemento.getElemento());
      }
      return cola;
  }

  private static class Prioridad<T extends Comparable<T>> implements Comparable<Prioridad<T>> {
      private T elemento;
      private int prioridad;

      public Prioridad(T elemento, int prioridad) {
          this.elemento = elemento;
          this.prioridad = prioridad;
      }

      public T getElemento() {
          return elemento;
      }

      public int compareTo(Prioridad<T> otro) {
          return Integer.compare(prioridad, otro.prioridad);
      }
  }
}