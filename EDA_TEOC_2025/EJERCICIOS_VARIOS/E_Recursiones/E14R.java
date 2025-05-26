package E_Recursiones;

import java.util.Arrays;

//Dada una matriz de tamaño m x n, la tarea consiste en hallar el número de rutas únicas posibles desde la esquina superior izquierda hasta la esquina inferior derecha, con la restricción de que desde cada celda solo podemos movernos hacia la derecha o hacia abajo.
public class E14R {
    
    private static int[][] memo;
    
    public static int contarRutasUnicas(int filas, int columnas) {
        memo = new int[filas + 1][columnas + 1];
        inicializarMemo();
        return calcularRutasDesdeOrigen(filas, columnas);
    }
    
    private static void inicializarMemo() {
        for (int[] fila : memo) {
            Arrays.fill(fila, -1);
        }
    }
    
    private static int calcularRutasDesdeOrigen(int filaActual, int columnaActual) {
        // Caso base: primera fila o primera columna
        if (filaActual == 1 || columnaActual == 1) {
            return 1;
        }
        
        // Verificar si ya calculamos esta posición
        if (memo[filaActual][columnaActual] != -1) {
            return memo[filaActual][columnaActual];
        }
        
        // Calcular rutas sumando las de arriba e izquierda
        int rutasDesdeArriba = calcularRutasDesdeOrigen(filaActual - 1, columnaActual);
        int rutasDesdeIzquierda = calcularRutasDesdeOrigen(filaActual, columnaActual - 1);
        
        memo[filaActual][columnaActual] = rutasDesdeArriba + rutasDesdeIzquierda;
        return memo[filaActual][columnaActual];
    }

    // Casos de prueba
    public static void main(String[] args) {
        pruebaCaso2x2();
        pruebaCaso2x3();
        pruebaCasoBorde();
    }
    
    private static void pruebaCaso2x2() {
        System.out.println("2x2: " + contarRutasUnicas(2, 2)); // 2
    }
    
    private static void pruebaCaso2x3() {
        System.out.println("2x3: " + contarRutasUnicas(2, 3)); // 3
    }
    
    private static void pruebaCasoBorde() {
        System.out.println("1x5: " + contarRutasUnicas(1, 5)); // 1
    }
}
