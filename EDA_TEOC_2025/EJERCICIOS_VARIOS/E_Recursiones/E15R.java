package E_Recursiones;

import java.util.Arrays;

public class E15R {
    private static int[][] memo;

    public static int contarPermutacionesConInversiones(int elementosTotales, int inversionesObjetivo) {
        int maxInversiones = elementosTotales * (elementosTotales - 1) / 2;
        if (inversionesObjetivo < 0 || inversionesObjetivo > maxInversiones) return 0;
        
        memo = new int[elementosTotales + 1][inversionesObjetivo + 1];
        inicializarMemo();
        return calcularPermutaciones(elementosTotales, inversionesObjetivo);
    }

    private static void inicializarMemo() {
        for (int[] fila : memo) {
            Arrays.fill(fila, -1);
        }
    }

    private static int calcularPermutaciones(int elementosRestantes, int inversionesPermitidas) {
        // Casos base
        if (elementosRestantes == 0 && inversionesPermitidas == 0) return 1;
        if (elementosRestantes <= 0 || inversionesPermitidas < 0) return 0;
        // Verificar si ya calculamos este caso
        if (memo[elementosRestantes][inversionesPermitidas] != -1) {
            return memo[elementosRestantes][inversionesPermitidas];
        }

        int total = 0;
        int maxPosiciones = elementosRestantes - 1;
        
        // Calcular para todas las posibles posiciones de inserción
        for (int nuevasInversiones = 0; nuevasInversiones <= Math.min(maxPosiciones, inversionesPermitidas); nuevasInversiones++) {
            total += calcularPermutaciones(elementosRestantes - 1, inversionesPermitidas - nuevasInversiones);
        }

        memo[elementosRestantes][inversionesPermitidas] = total;
        return total;
    }

    public static void main(String[] args) {
        System.out.println("Caso 3 elementos, 1 inversión: " + contarPermutacionesConInversiones(3, 1));  // Debe ser 2
        System.out.println("Caso 3 elementos, 3 inversiones: " + contarPermutacionesConInversiones(3, 3)); // Debe ser 1
        System.out.println("Caso 4 elementos, 2 inversiones: " + contarPermutacionesConInversiones(4, 2)); // Debe ser 5
    }
}