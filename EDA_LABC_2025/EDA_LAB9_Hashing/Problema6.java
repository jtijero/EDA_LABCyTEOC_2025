package EDA_LAB9_Hashing;

import java.util.ArrayList; // Importar ArrayList para manejar listas dinámicas
import java.util.List; // Importar List para usar la interfaz de lista

public class Problema6 {
    private int bucketCount; // Número de depósitos (buckets) en la tabla hash
    private int numOfElements; // Número actual de elementos en la tabla
    private List<List<Integer>> table; // Tabla hash que almacena las claves

    // Constructor para inicializar la tabla hash
    public Problema6(int buckets) {
        bucketCount = buckets;
        numOfElements = 0;
        table = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            table.add(new ArrayList<>()); // Inicializa cada bucket como una lista vacía
        }
    }

    // Insertar una clave en la tabla hash
    public void insert(int key) {
        // Si el factor de carga supera 0.5, realizar un rehash
        while (getLoadFactor() > 0.5) {
            rehash();
        }

        // Calcular el índice de la clave
        int index = getHashIndex(key);

        // Insertar clave dentro del bucket correspondiente
        table.get(index).add(key);

        // Aumentar el número de elementos
        numOfElements++;
    }

    // Eliminar una clave de la tabla hash
    public void remove(int key) {
        // Obtener el índice de la clave
        int index = getHashIndex(key);

        // Eliminar la clave de su bucket si existe
        table.get(index).remove((Integer) key);
        numOfElements--; // Decrementar el número de elementos
    }

    // Mostrar todos los depósitos y elementos
    public void display() {
        for (int i = 0; i < bucketCount; i++) {
            System.out.print(i); // Imprimir el índice del bucket
            for (int key : table.get(i)) {
                System.out.print(" --> " + key); // Imprimir las claves en el bucket
            }
            System.out.println(); // Nueva línea después de imprimir el bucket
        }
    }

    // Función hash para mapear la clave al índice
    private int getHashIndex(int key) {
        return key % bucketCount; // Calcular el índice usando el módulo
    }

    // Calcular el factor de carga actual
    private float getLoadFactor() {
        return (float) numOfElements / bucketCount; // Calcular el factor de carga
    }

    // Rehash: duplicar el tamaño y volver a insertar todos los elementos
    private void rehash() {
        List<List<Integer>> oldTable = table; // Guardar la tabla antigua
        bucketCount *= 2; // Duplicar el tamaño de la tabla
        table = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            table.add(new ArrayList<>()); // Inicializar nuevos buckets
        }
        numOfElements = 0; // Reiniciar el contador de elementos

        // Reinsertar todos los elementos de la tabla antigua
        for (List<Integer> bucket : oldTable) {
            for (int key : bucket) {
                insert(key); // Insertar cada clave en la nueva tabla
            }
        }
    }

    // Método principal para ejecutar el programa
    public static void main(String[] args) {
        int[] keys = {15, 11, 27}; // Claves a insertar

        Problema6 hashTable = new Problema6(5); // Crear una tabla hash con 5 buckets

        // Insertar claves en la tabla hash
        for (int key : keys) {
            hashTable.insert(key);
        }

        // Eliminar una clave
        hashTable.remove(11);
        
        // Mostrar el estado actual de la tabla hash
        hashTable.display();
        
        // Insertar otra clave que causará rehashing
        hashTable.insert(19);
        
        System.out.println("\nDespues de rehashing:");
        hashTable.display(); // Mostrar la tabla hash después del rehashing
    }
}
