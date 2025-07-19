package EDA_LAB9_Hashing;

// Clase principal que contiene la implementación de la tabla hash
class GFG {

    // Función para imprimir el arreglo
    static void printArray(int arr[]) {
        // Iterando e imprimiendo el arreglo
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " "); // Imprimir cada elemento del arreglo
        }
        System.out.println(); // Nueva línea al final de la impresión
    }

    // Función para implementar la prueba cuadrática
    static void hashing(int table[], int arr[]) {
        int tsize = table.length; // Tamaño de la tabla hash
        int n = arr.length; // Número de elementos en el arreglo

        // Iterando a través del arreglo de entrada
        for (int i = 0; i < n; i++) {
            // Calculando el valor hash inicial
            int hv = arr[i] % tsize;

            // Insertar en la tabla si hay 
            // ninguna colisión
            if (table[hv] == -1) {
                table[hv] = arr[i]; // Insertar el valor directamente
            } else {
                // Si hay una colisión, iterar a través de posibles valores cuadráticos
                for (int j = 1; j <= tsize; j++) {
                    // Calculando el nuevo valor hash usando prueba cuadrática
                    int t = (hv + j * j) % tsize;
                    if (table[t] == -1) {
                        // Romper el bucle e insertar el valor en la tabla
                        table[t] = arr[i];
                        break; // Salir del bucle una vez insertado
                    }
                }
            }
        }

        // Imprimir el estado final de la tabla hash
        printArray(table);
    }

    // Método principal
    public static void main(String args[]) {
        // Arreglo de elementos a insertar en la tabla hash
        int arr[] = { 50, 700, 76, 85, 92, 73, 101 };

        int tsize = 11; // Tamaño de la tabla hash
        int hash_table[] = new int[tsize]; // Crear la tabla hash

        // Inicializando la tabla con -1 para indicar espacios vacíos
        for (int i = 0; i < tsize; i++) {
            hash_table[i] = -1; // -1 indica que el espacio está vacío
        }

        // Llamada a la función para insertar elementos en la tabla hash
        hashing(hash_table, arr);
    }
}

