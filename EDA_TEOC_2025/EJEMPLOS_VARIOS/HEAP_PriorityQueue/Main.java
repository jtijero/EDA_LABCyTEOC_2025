public class Main {
  public static void main(String[] args) {
      PriorityQueue<Integer> cola = new PriorityQueue<>();

      cola.enqueue(5, 3);
      cola.enqueue(2, 1);
      cola.enqueue(8, 4);
      cola.enqueue(3, 2);
      cola.enqueue(1, 0);

      System.out.println("Orden del heap:");
      for (Integer elemento : cola.getPriorityQueue()) {
          System.out.print(elemento + " ");
      }
      System.out.println();

      System.out.println("Front: " + cola.front());
      System.out.println("Back: " + cola.back());

      System.out.println("Dequeue: " + cola.dequeue());


      System.out.println("Orden del heap después de dequeue:");
      for (Integer elemento : cola.getPriorityQueue()) {
          System.out.print(elemento + " ");
      }
      System.out.println();
    }
  }