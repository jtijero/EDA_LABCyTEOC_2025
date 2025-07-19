package EDA_LAB9_Hashing;
import java.util.ArrayList;

public class Problema1 {
    // Número de cubos en la tabla hash
    private final int bucket;
    // Tabla hash del tamaño del cubo, que contiene listas para el encadenamiento
    private final ArrayList<Integer>[] table;

    // Constructor que inicializa la tabla hash
    @SuppressWarnings("unchecked") // Suprimir advertencias de conversión sin verificar
    public Problema1(int bucket) {
        this.bucket = bucket;
        // Inicializar el arreglo de ArrayList con la conversión explícita
        this.table = (ArrayList<Integer>[]) new ArrayList[bucket];
        // Inicializar cada cubo de la tabla como una nueva lista
        for (int i = 0; i < bucket; i++) {
            table[i] = new ArrayList<>();
        }
    }

    // Función hash para asignar valores a la clave
    public int hashFunction(int key) {
        return (key % bucket); // Retorna el índice basado en el módulo del tamaño de la tabla
    }

    // Método para insertar un elemento en la tabla hash
    public void insertItem(int key) {
        // Obtener el índice hash de la clave
        int index = hashFunction(key);
        // Insertar la clave en la tabla hash en ese índice
        table[index].add(key);
    }

    // Método para eliminar un elemento de la tabla hash
    public void deleteItem(int key) {
        // Obtener el índice hash de la clave
        int index = hashFunction(key);

        // Comprobar si la clave está en la tabla hash
        if (!table[index].contains(key)) {
            return; // Si no está, no hacer nada
        }

        // Borrar la clave de la tabla hash
        table[index].remove(Integer.valueOf(key));
    }

    // Método para mostrar la tabla hash
    public void displayHash() {
        // Iterar sobre cada cubo de la tabla
        for (int i = 0; i < bucket; i++) {
            System.out.print(i); // Mostrar el índice del cubo
            // Mostrar cada clave en el cubo
            for (int x : table[i]) {
                System.out.print(" --> " + x);
            }
            System.out.println(); // Nueva línea después de cada cubo
        }
    }

    // Programa principal
    public static void main(String[] args) {
        // Arreglo que contiene las claves para ser asignadas
        int[] a = { 15, 11, 27, 8, 12 };

        // Crear un Hash vacío que tiene BUCKET_SIZE
        Problema1 h = new Problema1(7);

        // Insertar las claves dentro de la tabla hash
        for (int x : a) {
            h.insertItem(x);
        }

        // Eliminar 12 de la tabla hash 
        h.deleteItem(12);

        // Mostrar la tabla hash 
        h.displayHash();
    }
}
