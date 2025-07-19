package EDA_LAB9_Hashing.ejercicios_propuestos.Problema2;

import java.util.HashMap;

public class DistanciaMaxima {

    // Método para encontrar la distancia máxima entre dos ocurrencias del mismo elemento
    public static int distanciaMaxima(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int maxDistancia = -1;

        // Recorrer el arreglo
        for (int i = 0; i < arr.length; i++) {
            // Si el elemento ya está en el mapa, calcular la distancia
            if (map.containsKey(arr[i])) {
                // Calcular la distancia entre la posición actual y la primera ocurrencia
                int distancia = i - map.get(arr[i]);
                // Actualizar la máxima distancia
                maxDistancia = Math.max(maxDistancia, distancia);
            } else {
                // Si no está en el mapa, agregar la posición
                map.put(arr[i], i);
            }
        }

        return maxDistancia;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 2, 1, 3, 4, 1};

        int resultado = distanciaMaxima(arr);
        if (resultado != -1) {
            System.out.println("La distancia máxima entre dos ocurrencias es: " + resultado);
        } else {
            System.out.println("No hay ocurrencias repetidas en el arreglo.");
        }
    }
}

