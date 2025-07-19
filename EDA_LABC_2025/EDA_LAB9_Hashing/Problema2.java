package EDA_LAB9_Hashing;

import java.lang.*;

// Clase que representa un nodo en la tabla hash
class HashNode {
    int key;   // Clave del nodo
    int value; // Valor asociado a la clave

    // Constructor que inicializa la clave y el valor
    public HashNode(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

// Clase que representa la tabla hash
class HashMap {
    HashNode[] arr; // Arreglo que contiene los nodos de la tabla hash
    int capacity;   // Capacidad máxima de la tabla hash
    int size;       // Número actual de elementos en la tabla hash
    HashNode dummy; // Nodo dummy para marcar espacios eliminados

    // Constructor que inicializa la tabla hash
    public HashMap() {
        capacity = 20; // Establecer capacidad inicial
        size = 0;      // Inicializar tamaño a 0
        arr = new HashNode[capacity]; // Crear el arreglo de nodos
        dummy = new HashNode(-1, -1);  // Inicializar el nodo dummy
    }

    // Función hash que calcula el índice basado en la clave
    int hashCode(int key) {
        return key % capacity; // Retorna el índice usando el módulo de la capacidad
    }

    // Método para insertar un par clave-valor en la tabla hash
    void insertNode(int key, int value) {
        HashNode temp = new HashNode(key, value); // Crear un nuevo nodo
        int hashIndex = hashCode(key); // Calcular el índice hash

        // Manejo de colisiones usando prueba lineal
        while (arr[hashIndex] != null && 
               arr[hashIndex].key != key && 
               arr[hashIndex].key != -1) {
            hashIndex++; // Incrementar el índice
            hashIndex %= capacity; // Asegurarse de que el índice esté dentro de los límites
        }

        // Si encontramos un espacio vacío o un nodo eliminado, incrementamos el tamaño
        if (arr[hashIndex] == null || arr[hashIndex].key == -1) {
            size++;
        }
        arr[hashIndex] = temp; // Insertar el nuevo nodo en el índice calculado
    }

    // Método para eliminar un nodo por su clave
    int deleteNode(int key) {
        int hashIndex = hashCode(key); // Calcular el índice hash

        // Buscar el nodo con la clave especificada
        while (arr[hashIndex] != null) {
            if (arr[hashIndex].key == key) { // Si encontramos la clave
                HashNode temp = arr[hashIndex]; // Guardar el nodo a eliminar
                arr[hashIndex] = dummy; // Marcar el espacio como eliminado
                size--; // Decrementar el tamaño
                return temp.value; // Retornar el valor eliminado
            }
            hashIndex++; // Continuar buscando
            hashIndex %= capacity; // Asegurarse de que el índice esté dentro de los límites
        }

        return -1; // Retornar -1 si no se encuentra la clave
    }

    // Método para obtener el valor asociado a una clave
    int get(int key) {
        int hashIndex = hashCode(key); // Calcular el índice hash

        // Buscar el nodo con la clave especificada
        while (arr[hashIndex] != null) {
            if (arr[hashIndex].key == key) { // Si encontramos la clave
                return arr[hashIndex].value; // Retornar el valor correspondiente
            }
            hashIndex++; // Continuar buscando
            hashIndex %= capacity; // Asegurarse de que el índice esté dentro de los límites
        }

        return -1; // Retornar -1 si no se encuentra la clave
    }

    // Método para obtener el tamaño actual del mapa
    int sizeofMap() {
        return size; // Retornar el tamaño actual
    }

    // Método para comprobar si el mapa está vacío
    boolean isEmpty() {
        return size == 0; // Retornar verdadero si el tamaño es 0
    }

    // Método para mostrar todos los pares clave-valor en la tabla hash
    void display() {
        for (int i = 0; i < capacity; i++) {
            if (arr[i] != null && arr[i].key != -1) { // Comprobar si el espacio no está vacío o eliminado
                System.out.println(arr[i].key + " " + arr[i].value); // Mostrar la clave y el valor
            }
        }
    }
}

// Clase principal que contiene el método main
public class Problema2 {
    // Método principal para probar la implementación de la tabla hash
    public static void main(String[] args) {
        HashMap h = new HashMap(); // Crear una nueva instancia de HashMap
        h.insertNode(1, 1); // Insertar clave-valor (1, 1)
        h.insertNode(2, 2); // Insertar clave-valor (2, 2)
        h.insertNode(2, 3); // Insertar clave-valor (2, 3), esto sobrescribirá el valor anterior
        h.display(); // Mostrar los pares clave-valor
        System.out.println("Tamano del mapa: " + h.sizeofMap()); // Mostrar el tamaño del mapa
        System.out.println("Valor eliminado: " + h.deleteNode(2)); // Eliminar la clave 2 y mostrar su valor
        System.out.println("Tamano del mapa después de eliminar: " + h.sizeofMap()); // Mostrar el tamaño después de eliminar
        System.out.println("Esta vacio?: " + h.isEmpty()); // Comprobar si el mapa está vacio
        System.out.println("Valor de la clave 2: " + h.get(2)); // Intentar obtener el valor de la clave 2
    }
}
