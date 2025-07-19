package EDA_LAB9_Hashing;

import java.util.BitSet; // Importar BitSet para manejar números primos
import java.util.Vector; // Importar Vector para almacenar la tabla hash

// Clase que implementa el Doble Hashing
class DoubleHash {
    private int TABLE_SIZE, keysPresent, PRIME; // Tamaño de la tabla, cantidad de claves presentes y un número primo
    private Vector<Integer> hashTable; // Tabla hash
    private BitSet isPrime; // BitSet para almacenar números primos
    private static final long MAX_SIZE = 10000001L; // Tamaño máximo para la criba de Eratosthenes

    // Función para inicializar la criba de Eratosthenes
    private void setSieve() {
        isPrime.set(0, true); // Marcar 0 como no primo
        isPrime.set(1, true); // Marcar 1 como no primo
        for (long i = 2; i * i <= MAX_SIZE; i++)
            if (!isPrime.get((int) i)) // Si no es primo
                for (long j = i * i; j <= MAX_SIZE; j += i)
                    isPrime.set((int) j); // Marcar múltiplos como no primos
    }

    // Función hash principal
    private int hash1(int value) {
        return value % TABLE_SIZE; // Valor hash primario
    }

    // Función hash secundaria
    private int hash2(int value) {
        return PRIME - (value % PRIME); // Valor hash secundario
    }

    // Verifica si la tabla está llena
    private boolean isFull() {
        return (TABLE_SIZE == keysPresent);
    }

    // Constructor de la clase
    public DoubleHash(int n) {
        isPrime = new BitSet((int) MAX_SIZE); // Inicializar BitSet
        setSieve(); // Llenar el BitSet con números primos
        TABLE_SIZE = n; // Establecer el tamaño de la tabla

        // Encontrar el número primo más grande menor que el tamaño de la tabla
        PRIME = TABLE_SIZE - 1;
        while (isPrime.get(PRIME))
            PRIME--;

        keysPresent = 0; // Inicializar la cantidad de claves presentes

        // Llenar la tabla hash con -1 (entradas vacías)
        hashTable = new Vector<>();
        for (int i = 0; i < TABLE_SIZE; i++)
            hashTable.add(-1); // -1 indica que el espacio está vacío
    }

    // Función para imprimir números primos hasta n
    private void printPrime(long n) {
        for (long i = 0; i <= n; i++)
            if (!isPrime.get((int) i))
                System.out.print(i + ", ");
        System.out.println();
    }

    // Función para insertar un valor en la tabla hash
    public void insert(int value) {
        if (value == -1 || value == -2) {
            System.out.println("ERROR : -1 and -2 can't be inserted in the table");
            return; // No se pueden insertar valores -1 o -2
        }

        if (isFull()) {
            System.out.println("ERROR : Hash Table Full");
            return; // Si la tabla está llena, no se puede insertar
        }

        int probe = hash1(value), offset = hash2(value); // Calcular índices de hash

        // Proceso de inserción con doble hashing
        while (hashTable.get(probe) != -1) {
            if (-2 == hashTable.get(probe))
                break; // Insertar en la ubicación de un elemento eliminado
            probe = (probe + offset) % TABLE_SIZE; // Calcular nueva posición
        }

        hashTable.set(probe, value); // Insertar el valor
        keysPresent += 1; // Incrementar el contador de claves presentes
    }

    // Función para eliminar un valor de la tabla hash
    public void erase(int value) {
        // Retorna si el elemento no está presente
        if (!search(value))
            return;

        int probe = hash1(value), offset = hash2(value);

        // Proceso de eliminación
        while (hashTable.get(probe) != -1) {
            if (hashTable.get(probe) == value) {
                hashTable.set(probe, -2); // Marcar el elemento como eliminado
                keysPresent--;
                return; // Salir después de eliminar
            } else
                probe = (probe + offset) % TABLE_SIZE; // Calcular nueva posición
        }
    }

    // Función para buscar un valor en la tabla hash
    public boolean search(int value) {
        int probe = hash1(value), offset = hash2(value), initialPos = probe;
        boolean firstItr = true;

        // Proceso de búsqueda
        while (true) {
            if (hashTable.get(probe) == -1) // Detener si se encuentra -1
                break;
            else if (hashTable.get(probe) == value) // Detener si se encuentra el valor
                return true;
            else if (probe == initialPos && !firstItr) // Completar un recorrido de la tabla
                return false;
            else
                probe = ((probe + offset) % TABLE_SIZE); // Actualizar el índice

            firstItr = false;
        }
        return false; // Valor no encontrado
    }

    // Función para mostrar la tabla hash
    public void print() {
        for (int i = 0; i < TABLE_SIZE; i++)
            System.out.print(hashTable.get(i) + ", ");
        System.out.println();
    }
}

// Clase principal para ejecutar el programa
public class Problema4 {
    public static void main(String[] args) {
        DoubleHash myHash = new DoubleHash(13); // Crear una tabla hash vacía de tamaño 13

        // Inserta elementos en la tabla hash
        int[] insertions = { 115, 12, 87, 66, 123 };
        int n1 = insertions.length;

        for (int i = 0; i < n1; i++)
            myHash.insert(insertions[i]);

        System.out.print("Estado de la tabla hash después de las inserciones iniciales: ");
        myHash.print();

        // Buscar elementos en la tabla hash
        int[] queries = { 1, 12, 2, 3, 69, 88, 115 };
        int n2 = queries.length;

        System.out.println("\nOperacion de busqueda despues de la insercion: ");
        for (int i = 0; i < n2; i++)
            if (myHash.search(queries[i]))
                System.out.println(queries[i] + " presente");

        // Eliminar elementos de la tabla hash
        int[] deletions = { 123, 87, 66 };
        int n3 = deletions.length;

        for (int i = 0; i < n3; i++)
            myHash.erase(deletions[i]);

        System.out.print("Estado de la tabla hash despues de eliminar elementos: ");
        myHash.print();
    }
}
