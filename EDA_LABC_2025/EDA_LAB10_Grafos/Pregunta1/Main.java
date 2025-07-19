package EDA_LABC_2025.EDA_LAB10_Grafos.Pregunta1;

public class Main {
    public static void main(String[] args) {
        // Crear y poblar el árbol-grafo
        ArbolGrafo arbolGrafo = new ArbolGrafo();
        for (int i = 1; i <= 1000; i++) {
            arbolGrafo.insertar(i);
        }

        // Conectar nodos en el grafo
        for (int i = 1; i < 1000; i++) {
            arbolGrafo.agregarArista(i, i + 1); // Conectando en línea
        }

        // Medir tiempo de búsqueda en el árbol AVL
        long inicioAVL = System.nanoTime();
        boolean encontradoAVL = arbolGrafo.buscar(499);
        long tiempoAVL = System.nanoTime() - inicioAVL;

        // Medir tiempo de búsqueda en el grafo usando Dijkstra
        long inicioGrafo = System.nanoTime();
        int distancia = arbolGrafo.dijkstra(1, 500);
        long tiempoGrafo = System.nanoTime() - inicioGrafo;

        // Resultados
        System.out.println("Búsqueda en árbol AVL (500): " + (encontradoAVL ? "Encontrado" : "No encontrado"));
        System.out.println("Tiempo de búsqueda en árbol AVL: " + tiempoAVL + " nanosegundos");
        System.out.println("Distancia desde 1 a 500 en grafo: " + distancia);
        System.out.println("Tiempo de búsqueda en grafo con Dijkstra: " + tiempoGrafo + " nanosegundos");
    }
}
