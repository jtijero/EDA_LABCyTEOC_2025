package EDA_LAB9_Hashing.ejercicios_propuestos.Problema1;

import java.util.HashSet;

public class Subconjunto {

    // Método para comprobar si arr2 es un subconjunto de arr1
    public static boolean esSubconjunto(int[] arr1, int[] arr2) {
        // Crear un HashSet para almacenar los elementos de arr1
        HashSet<Integer> set = new HashSet<>();

        // Agregar todos los elementos de arr1 al HashSet
        for (int num : arr1) {
            set.add(num);
        }

        // Verificar si todos los elementos de arr2 están en el HashSet
        for (int num : arr2) {
            if (!set.contains(num)) {
                return false; // Si algún elemento no está presente, no es un subconjunto
            }
        }

        return true; // Todos los elementos de arr2 están en arr1
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {2, 3, 4};

        if (esSubconjunto(arr1, arr2)) {
            System.out.println("arr2 es un subconjunto de arr1.");
        } else {
            System.out.println("arr2 NO es un subconjunto de arr1.");
        }

        int[] arr3 = {6, 7};

        if (esSubconjunto(arr1, arr3)) {
            System.out.println("arr3 es un subconjunto de arr1.");
        } else {
            System.out.println("arr3 NO es un subconjunto de arr1.");
        }
    }
}
