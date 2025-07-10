package Pregunta3;

public class Principal {
    public static void main(String[] args) {
        ArbolB arbol = new ArbolB(3); // Grado mínimo 3

        int[] claves = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int c : claves) {
            arbol.insertar(c);
        }
        System.out.println("Recorrido del árbol B:");
        arbol.recorrer();

        System.out.println("\nEliminando 6:");
        arbol.eliminar(6);
        arbol.recorrer();
        System.out.println("\nEliminando 13 (no existe):");
        arbol.eliminar(13);
        arbol.recorrer();

        System.out.println("\nEliminando 7:");
        arbol.eliminar(7);
        arbol.recorrer();
    }
}
