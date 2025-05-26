package E_Recursiones;

import java.util.Arrays;

public class E13R {
    
    private static int[][] memo;
    private static int maxLength;
    
    public static int encontrarSubcadenaComunMasLarga(String cadena1, String cadena2) {
        int longitudCadena1 = cadena1.length();
        int longitudCadena2 = cadena2.length();
        
        memo = new int[longitudCadena1 + 1][longitudCadena2 + 1];
        maxLength = 0;
        
        inicializarMemo(longitudCadena1, longitudCadena2);
        calcularSubcadenas(cadena1, cadena2, longitudCadena1, longitudCadena2);
        
        return maxLength;
    }
    
    private static void inicializarMemo(int filas, int columnas) {
        for (int[] fila : memo) {
            Arrays.fill(fila, -1);
        }
    }
    
    private static int calcularSubcadenas(String str1, String str2, int indice1, int indice2) {
        if (indice1 == 0 || indice2 == 0) {
            return 0;
        }
        
        if (memo[indice1][indice2] != -1) {
            return memo[indice1][indice2];
        }
        
        int longitudActual = 0;
        
        if (str1.charAt(indice1 - 1) == str2.charAt(indice2 - 1)) {
            int longitudPrevia = calcularSubcadenas(str1, str2, indice1 - 1, indice2 - 1);
            longitudActual = longitudPrevia + 1;
            maxLength = Math.max(maxLength, longitudActual);
        } else {
            calcularSubcadenas(str1, str2, indice1 - 1, indice2);
            calcularSubcadenas(str1, str2, indice1, indice2 - 1);
        }
        
        memo[indice1][indice2] = longitudActual;
        return longitudActual;
    }

    public static void main(String[] args) {
        pruebaCasoEjemplo1();
        pruebaCasoEjemplo2();
        pruebaCasoBorde();
    }
    
    private static void pruebaCasoEjemplo1() {
        String entrada1 = "GeeksforGeeks";
        String entrada2 = "GeeksQuiz";
        System.out.println("Caso 1: " + encontrarSubcadenaComunMasLarga(entrada1, entrada2)); // 5
    }
    
    private static void pruebaCasoEjemplo2() {
        String entrada1 = "abcdxyz";
        String entrada2 = "xyzabcd";
        System.out.println("Caso 2: " + encontrarSubcadenaComunMasLarga(entrada1, entrada2)); // 4
    }
    
    private static void pruebaCasoBorde() {
        String entrada1 = "abc";
        String entrada2 = "";
        System.out.println("Caso Borde: " + encontrarSubcadenaComunMasLarga(entrada1, entrada2)); // 0
    }
}
