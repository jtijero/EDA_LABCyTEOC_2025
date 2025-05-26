package E_Recursiones;

import java.util.Arrays;
//Dado un array entero de monedas[] de tamaño n que representan diferentes tipos de denominaciones y una suma entera, la tarea consiste en contar todas las combinaciones de monedas para obtener una suma de valores dada.

class ContadorCombinacionesOptimizado {
    
    static int contarCombinaciones(int[] denominaciones, int sumaObjetivo) {
        int[][] memo = new int[denominaciones.length][sumaObjetivo + 1];
        for (int[] fila : memo) {
            Arrays.fill(fila, -1);
        }
        return calcularCombinacionesMemo(denominaciones, 0, sumaObjetivo, memo);
    }
    
    private static int calcularCombinacionesMemo(int[] denominaciones, int indiceActual, 
                                                int sumaRestante, int[][] memo) {
        // Caso base 1: Suma exactamente alcanzada
        if (sumaRestante == 0) {
            return 1;
        }
        
        // Caso base 2: Suma negativa o índice fuera de rango
        if (sumaRestante < 0 || indiceActual >= denominaciones.length) {
            return 0;
        }
        
        // Revisar si ya tenemos el valor calculado
        if (memo[indiceActual][sumaRestante] != -1) {
            return memo[indiceActual][sumaRestante];
        }
        
        // Calcular ambas opciones
        int usarMonedaActual = calcularCombinacionesMemo(
            denominaciones, 
            indiceActual, 
            sumaRestante - denominaciones[indiceActual], 
            memo
        );
        
        int pasarSiguienteMoneda = calcularCombinacionesMemo(
            denominaciones, 
            indiceActual + 1, 
            sumaRestante, 
            memo
        );
        
        // Almacenar y retornar resultado
        memo[indiceActual][sumaRestante] = usarMonedaActual + pasarSiguienteMoneda;
        return memo[indiceActual][sumaRestante];
    }

    public static void main(String[] args) {
        int[] monedasEjemplo = {1, 2, 3};
        System.out.println("Combinaciones para suma 4: " + 
            contarCombinaciones(monedasEjemplo, 4));  // Output: 4
    }
}
