package Ejercicio3;

import java.util.*;

public class Dijkstra {
    private Map<Integer, List<Edge>> grafo;

    public Dijkstra() {
        grafo = new HashMap<>();
    }

    public void agregarArista(int u, int v, int peso) {
        grafo.putIfAbsent(u, new ArrayList<>());
        grafo.putIfAbsent(v, new ArrayList<>());
        grafo.get(u).add(new Edge(v, peso));
        grafo.get(v).add(new Edge(u, peso)); // Si el grafo es no dirigido
    }

    public void dijkstra(int inicio) {
        Map<Integer, Integer> distancias = new HashMap<>();
        PriorityQueue<Edge> cola = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.peso));
        Set<Integer> visitados = new HashSet<>();

        for (int vertice : grafo.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);
        }
        distancias.put(inicio, 0);
        cola.add(new Edge(inicio, 0));

        while (!cola.isEmpty()) {
            Edge actual = cola.poll();
            int verticeActual = actual.vertice;

            if (visitados.contains(verticeActual)) {
                continue;
            }
            visitados.add(verticeActual);

            for (Edge vecino : grafo.getOrDefault(verticeActual, new ArrayList<>())) {
                if (!visitados.contains(vecino.vertice)) {
                    int nuevaDistancia = distancias.get(verticeActual) + vecino.peso;
                    if (nuevaDistancia < distancias.get(vecino.vertice)) {
                        distancias.put(vecino.vertice, nuevaDistancia);
                        cola.add(new Edge(vecino.vertice, nuevaDistancia));
                    }
                }
            }
        }

        System.out.println("Distancias desde el vértice " + inicio + ":");
        for (Map.Entry<Integer, Integer> entrada : distancias.entrySet()) {
            System.out.println("Vértice " + entrada.getKey() + ": " + entrada.getValue());
        }
    }

    private static class Edge {
        int vertice;
        int peso;

        Edge(int vertice, int peso) {
            this.vertice = vertice;
            this.peso = peso;
        }
    }

    public static void main(String[] args) {
        Dijkstra grafo = new Dijkstra();
        grafo.agregarArista(0, 1, 4);
        grafo.agregarArista(0, 2, 1);
        grafo.agregarArista(1, 3, 1);
        grafo.agregarArista(2, 1, 2);
        grafo.agregarArista(2, 3, 5);
        grafo.agregarArista(3, 4, 3);

        System.out.println("Caminos más cortos desde el vértice 0:");
        grafo.dijkstra(0);
    }
}
