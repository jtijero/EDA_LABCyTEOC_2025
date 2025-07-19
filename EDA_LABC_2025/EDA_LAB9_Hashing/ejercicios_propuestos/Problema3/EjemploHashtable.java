package EDA_LAB9_Hashing.ejercicios_propuestos.Problema3;

import java.util.Hashtable;
import java.util.Enumeration;

public class EjemploHashtable {

    public static void main(String[] args) {
        // Crear una Hashtable
        Hashtable<String, Integer> tabla = new Hashtable<>();

        // Agregar elementos a la Hashtable
        tabla.put("Uno", 1);
        tabla.put("Dos", 2);
        tabla.put("Tres", 3);
        tabla.put("Cuatro", 4);
        tabla.put("Cinco", 5);

        // Recorrer los elementos utilizando Enumeration
        System.out.println("Recorriendo elementos de la Hashtable usando Enumeration:");
        Enumeration<String> claves = tabla.keys();
        int contador = 0;

        while (claves.hasMoreElements() && contador < 4) {
            String clave = claves.nextElement();
            Integer valor = tabla.get(clave);
            System.out.println("Clave: " + clave + ", Valor: " + valor);
            contador++;
        }

        // Alternativamente, recorrer utilizando entrySet()
        System.out.println("\nRecorriendo elementos de la Hashtable usando entrySet:");
        contador = 0; // Reiniciar el contador para el segundo recorrido
        for (var entrada : tabla.entrySet()) {
            System.out.println("Clave: " + entrada.getKey() + ", Valor: " + entrada.getValue());
            // Salir después de imprimir 4 elementos
            if (++contador >= 4) {
                break;
            }
        }
    }
}
