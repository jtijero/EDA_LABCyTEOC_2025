package Ejercicio2;
import java.util.HashMap;
import java.util.*;

public class Grafo {
    private Map<Integer, List<Integer>> grafo;

    public Grafo() {
        grafo = new HashMap<>();
    }

    public void agregarArista(int u, int v) {
        grafo.putIfAbsent(u, new ArrayList<>());
        grafo.putIfAbsent(v, new ArrayList<>());
        grafo.get(u).add(v);
        grafo.get(v).add(u); // Si el grafo es no dirigido
    }

    public void bfs(int inicio) {
        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> cola = new LinkedList<>();

        visitados.add(inicio);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            int vertice = cola.poll();
            System.out.print(vertice + " "); // Procesar el vértice

            for (int vecino : grafo.getOrDefault(vertice, new ArrayList<>())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        grafo.agregarArista(1, 3);
        grafo.agregarArista(1, 4);
        grafo.agregarArista(2, 5);
        grafo.agregarArista(2, 6);

        System.out.println("Recorrido por anchura (BFS) comenzando desde el vértice 0:");
        grafo.bfs(0);
    }
}

