package EDA_LAB9_Hashing;

import java.util.ArrayList; // Importar ArrayList para manejar listas dinámicas
import java.util.List; // Importar List para usar la interfaz de lista

public class Problema5 {
    // Número de depósitos (buckets) en la tabla hash
    private int bucketCount;

    // Lista de listas para almacenar las cadenas
    private List<List<Integer>> table;

    // Constructor para inicializar la tabla hash con el número de buckets indicados
    public Problema5(int buckets) {
        bucketCount = buckets;
        table = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            table.add(new ArrayList<>()); // Inicializa cada bucket como una lista vacía
        }
    }

    // Función para insertar una clave en la tabla hash
    public void insert(int key) {
        // Obtener el índice hash de la clave
        int index = getHashIndex(key);

        // Insertar la clave en el bucket correspondiente
        table.get(index).add(key);
    }

    // Función para eliminar una clave de la tabla hash
    public void remove(int key) {
        // Obtener el índice hash de la clave
        int index = getHashIndex(key);

        // Encuentra y elimina la clave del bucket
        table.get(index).remove(Integer.valueOf(key)); // Eliminar el valor específico
    }

    // Función para mostrar la tabla hash
    public void display() {
        for (int i = 0; i < bucketCount; i++) {
            System.out.print(i); // Imprimir el índice del bucket

            // Imprime todas las claves en el bucket actual
            for (int key : table.get(i)) {
                System.out.print(" --> " + key);
            }

            System.out.println(); // Nueva línea después de imprimir el bucket
        }
    }

    // Función hash simple para mapear la clave al índice
    private int getHashIndex(int key) {
        return key % bucketCount; // Calcular el índice usando el módulo
    }

    // Método principal para ejecutar el programa
    public static void main(String[] args) {
        int[] keys = {7, 18, 12, 25}; // Claves a insertar

        Problema5 hashTable = new Problema5(7); // Crear una tabla hash con 7 buckets

        // Insertar claves en la tabla hash
        for (int key : keys) {
            hashTable.insert(key);
        }

        // Eliminar una clave
        hashTable.remove(12);
        
        // Mostrar el estado actual de la tabla hash
        hashTable.display();
    }
}
